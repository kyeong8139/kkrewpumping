package com.kkrew.model.service;

import java.util.List;

import com.kkrew.model.dto.Krew;
import com.kkrew.model.dto.KrewUser;

public interface KrewService {

	List<Krew> getChallengeKrew(int challengeId);

	int createKrew(Krew krew);

	int addKrewUser(KrewUser krewUser);

	Krew getKrewInfo(int krewId);

	List<KrewUser> getKrewUsers(int krewId);

}
