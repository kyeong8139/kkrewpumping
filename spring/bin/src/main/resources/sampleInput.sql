INSERT INTO kkrew.user (id, nickname, password)
VALUES ("ssafy1234", "관리자", "6aba834f44ee35f70a5faa0e2c22b67477b5ac6dcdcd83fc2ce906ba72a8e1d"),
	("(알 수 없음)", "(알 수 없음)", "(알 수 없음)");

INSERT INTO kkrew.supplies()
VALUES("아령"), 
	("폼롤러"),
    ("요가매트");
    
INSERT INTO kkrew.video(title, purpose, image_url, video_url, writer, description)
VALUES
    ("하루 15분! 전신 칼로리 불태우는 다이어트 운동 입니다.", "DIET", "https://img.youtube.com/vi/swRNeYw1JkY/0.jpg", "http://www.youtube.com/watch?v=swRNeYw1JkY", "ssafy1234", "하루 15분 동안 전신의 칼로리를 불태울 수 있는 다이어트 운동입니다."),
    ("전신 다이어트 최고의 운동 [칼소폭 찐 핵핵 매운맛]", "DIET", "https://img.youtube.com/vi/gMaB-fG4u4g/0.jpg", "http://www.youtube.com/watch?v=gMaB-fG4u4g", "ssafy1234", "전신 다이어트에 효과적인 칼로리 소모 폭발 운동, 찐 핵핵 매운맛 버전입니다."),
    ("전신 다이어트 최고의 운동 [칼소폭 마라맛]", "DIET", "https://img.youtube.com/vi/F-Jd4kI6rdM/0.jpg", "http://www.youtube.com/watch?v=F-Jd4kI6rdM", "ssafy1234", "전신 다이어트에 최고의 효과를 주는 칼로리 소모 폭발 운동, 마라맛 버전입니다."),
    ("자세교정 운동", "POSTURE_CORRECTION", "https://img.youtube.com/vi/kgCj8UUEWjU/0.jpg", "http://www.youtube.com/watch?v=kgCj8UUEWjU", "ssafy1234", "자세 교정을 위한 다양한 운동 방법을 소개합니다."),
    ("척추 측만증 교정 운동", "POSTURE_CORRECTION", "https://img.youtube.com/vi/Csq14WHG6q8/0.jpg", "http://www.youtube.com/watch?v=Csq14WHG6q8", "ssafy1234", "척추 측만증을 교정하는 데 도움이 되는 운동입니다."),
    ("무릎 통증 완화 운동", "POSTURE_CORRECTION", "https://img.youtube.com/vi/DwTqs4IHwbs/0.jpg", "http://www.youtube.com/watch?v=DwTqs4IHwbs", "ssafy1234", "무릎 통증을 완화하는 데 도움이 되는 운동입니다."),
    ("목 통증 완화 운동", "POSTURE_CORRECTION", "https://img.youtube.com/vi/fFIL0rlRH78/0.jpg", "http://www.youtube.com/watch?v=fFIL0rlRH78", "ssafy1234", "목 통증을 완화시키기 위한 다양한 운동입니다."),
    ("누구나 하는 근력 강화 운동", "MUSCULAR_STRENGTH", "https://img.youtube.com/vi/NTetHa7N1Mc/0.jpg", "http://www.youtube.com/watch?v=NTetHa7N1Mc", "ssafy1234", "초보자도 쉽게 따라할 수 있는 근력 강화 운동입니다."),
    ("복근 강화 운동", "MUSCULAR_STRENGTH", "https://img.youtube.com/vi/2G_P1Y9UG0Q/0.jpg", "https://www.youtube.com/watch?v=2G_P1Y9UG0Q", "ssafy1234", "복근을 강화하는 데 효과적인 운동입니다."),
    ("쉬운 팔다리 강화 운동", "MUSCULAR_STRENGTH", "https://img.youtube.com/vi/2pPubifPzHo/0.jpg", "http://www.youtube.com/watch?v=2pPubifPzHo", "ssafy1234", "팔과 다리를 쉽게 강화할 수 있는 운동입니다.")
;

-- Challenge 테이블에 데이터 삽입
INSERT INTO kkrew.challenge (title, purpose, level, period, time, image_url, video_count, writer)
VALUES 
    ("다이어트를 위한 챌린지", "DIET", 3, 7, 15, "https://img.youtube.com/vi/swRNeYw1JkY/0.jpg", 3, "ssafy1234"),
    ("자세교정을 위한 챌린지", "POSTURE_CORRECTION", 1, 7, 15, "https://img.youtube.com/vi/kgCj8UUEWjU/0.jpg", 4, "ssafy1234"),
    ("근력 강화를 위한 챌린지", "MUSCULAR_STRENGTH", 1, 7, 15, "https://img.youtube.com/vi/NTetHa7N1Mc/0.jpg", 3, "ssafy1234");

-- Challenge_video 테이블에 데이터 삽입
INSERT INTO kkrew.challenge_video(challenge_id, video_id, `order`)
VALUES 
    -- 다이어트 챌린지 비디오들
    (1, 1, 0),
    (1, 2, 1),
    (1, 3, 2),
    
    -- 자세교정 챌린지 비디오들
    (2, 4, 0),
    (2, 5, 1),
    (2, 6, 2),
    (2, 7, 3),

    -- 근력 강화 챌린지 비디오들
    (3, 8, 0),
    (3, 9, 1),
    (3, 10, 2);

-- Review 테이블에 데이터 삽입
INSERT INTO kkrew.review(challenge_id, writer, content) 
VALUES 
    (1, "ssafy1234", "다이어트 챌린지를 통해 체중 감량에 큰 도움이 되었어요. 매일 15분 투자로 큰 변화를 느꼈습니다."),
    (1, "ssafy1234", "쉽고 따라하기 좋은 다이어트 운동들로 구성되어 있어요. 체력에 맞게 도전해볼 수 있습니다."),
    (1, "ssafy1234", "다이어트 챌린지를 통해 몸이 가벼워지고 건강해졌어요. 추천합니다!"),

    (2, "ssafy1234", "자세교정 챌린지 덕분에 허리 통증이 많이 완화되었어요. 자세가 좋아지니 생활이 편해졌습니다."),
    (2, "ssafy1234", "매일 꾸준히 하니 자세가 바르게 교정되는 느낌이에요. 추천할 만한 챌린지입니다."),
    (2, "ssafy1234", "자세교정 챌린지는 누구에게나 필요한 운동 같아요. 효과가 좋아요."),

    (3, "ssafy1234", "근력 강화 챌린지로 근력이 눈에 띄게 향상되었어요. 매일 하는 것이 힘들지만 결과가 좋아요."),
    (3, "ssafy1234", "팔과 복근 강화에 큰 도움이 되는 운동들로 구성되어 있어요. 운동 후 뿌듯함이 큽니다."),
    (3, "ssafy1234", "근력 강화를 통해 몸이 탄탄해지고 체력이 좋아졌어요. 꾸준히 하니 변화가 느껴집니다.");
    
-- Crew 테이블에 데이터 삽입
INSERT INTO kkrew.crew (name, end_date, challenge_id)
VALUES 
    ("다이어트 챌린지 크루", DATE_ADD(CURDATE(), INTERVAL 7 DAY), 1),
    ("자세교정 챌린지 크루", DATE_ADD(CURDATE(), INTERVAL 7 DAY), 2),
    ("근력 강화 챌린지 크루", DATE_ADD(CURDATE(), INTERVAL 7 DAY), 3);

-- Crew_user 테이블에 데이터 삽입
INSERT INTO kkrew.crew_user (crew_id, user_id, user_challenge_id)
VALUES 
    (1, "ssafy1234", 1),
    (2, "ssafy1234", 2),
    (3, "ssafy1234", 3);

-- User_challenge 테이블에 데이터 삽입
INSERT INTO kkrew.user_challenge (user_id, challenge_id, crew_id, start_date, end_date, video_count)
VALUES 
    ("ssafy1234", 1, 1, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 3),
    ("ssafy1234", 2, 2, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 4),
    ("ssafy1234", 3, 3, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), 3);
