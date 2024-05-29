package com.kkrew.model.dao;

import java.util.List;

import com.kkrew.model.dto.Krew;
import com.kkrew.model.dto.KrewUser;

public interface KrewDao {

	Krew selectKrewById(int krewId);

	int insertKrewUser(KrewUser krewUser);

	int insertKrew(Krew krew);

	List<Krew> selectKrewByChallengeId(int challengeId);

	List<KrewUser> selectKrewUserById(int krewId);

	KrewUser selectUserChallengeInfo(int userChallengeId);

}
