package com.kkrew.model.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kkrew.model.dao.KrewDao;
import com.kkrew.model.dto.Krew;
import com.kkrew.model.dto.KrewUser;
import com.kkrew.model.dto.Message;

@Service
public class KrewServiceImpl implements KrewService{

	KrewDao kDao;
	
	public KrewServiceImpl(KrewDao kDao) {
		this.kDao = kDao;
	}

	@Override
	public List<Krew> getChallengeKrew(int challengeId) {
		return kDao.selectKrewByChallengeId(challengeId);
	}

	@Override
	public int createKrew(Krew krew) {
		return kDao.insertKrew(krew);
	}

	@Override
	public int addKrewUser(KrewUser krewUser) {
		return kDao.insertKrewUser(krewUser);
	}

	@Override
	public Krew getKrewInfo(int krewId) {
		return kDao.selectKrewById(krewId);
	}

	@Override
	public List<KrewUser> getKrewUsers(int krewId) {
		
		List<KrewUser> krewUsers = kDao.selectKrewUserById(krewId);
		for (KrewUser user : krewUsers) {
			KrewUser info = kDao.selectUserChallengeInfo(user.getUserChallengeId());
			user.setCurrentCompletion(info.getCurrentCompletion());
			user.setUserChallengeId(info.getUserChallengeId());
			user.setUpdateTimeStamp(info.getUpdateTimeStamp());
		}
		
		krewUsers.sort(new Comparator<KrewUser>() {
			@Override
			public int compare(KrewUser o1, KrewUser o2) {
				if (Integer.bitCount(o2.getCurrentCompletion()) == Integer.bitCount(o1.getCurrentCompletion())) {
					return o1.getUpdateTimeStamp().compareTo(o2.getUpdateTimeStamp());
				}
				
				return Integer.bitCount(o2.getCurrentCompletion()) - Integer.bitCount(o1.getCurrentCompletion());
			}
		});
		
		return krewUsers;
	}

}
