-- netwalk.user_tb definition

CREATE TABLE `user_tb` (
  `USER_NO` int NOT NULL AUTO_INCREMENT COMMENT '회원번호',
  `NAME` varchar(100) NOT NULL COMMENT '회원명',
  `EMAIL` varchar(200) NOT NULL,
  `IMG_URL` varchar(400) DEFAULT NULL COMMENT '프로필 이미지 URL',
  `REG_DT` datetime NOT NULL COMMENT '등록일자',
  `MOD_DT` datetime NOT NULL COMMENT '수정일자',
  PRIMARY KEY (`USER_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원';


-- netwalk.group_tb definition

CREATE TABLE `group_tb` (
  `GROUP_NO` int NOT NULL AUTO_INCREMENT COMMENT '그룹번호',
  `CREATE_USER_NO` int NOT NULL COMMENT '그룹생성회원번호',
  `NAME` varchar(100) NOT NULL COMMENT '그룹명',
  `CAPACITY` int NOT NULL COMMENT '정원',
  `PARTICIPANT` int NOT NULL COMMENT '참여인원',
  `CATEGORY` varchar(4) NOT NULL COMMENT '그룹구분',
  `DEL_ST` binary(1) NOT NULL COMMENT '그룹삭제여부',
  `REG_DT` datetime NOT NULL COMMENT '등록일자',
  `MOD_DT` datetime NOT NULL COMMENT '수정일자',
  PRIMARY KEY (`GROUP_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='그룹';


-- netwalk.user_group_tb definition

CREATE TABLE `user_group_tb` (
  `USER_NO` int NOT NULL COMMENT '회원번호',
  `GROUP_NO` int NOT NULL COMMENT '그룹번호',
  `ACT_ST` int NOT NULL COMMENT '활성화상태',
  `DEL_ST` binary(1) NOT NULL COMMENT '탈퇴여부',
  `REG_DT` datetime NOT NULL COMMENT '등록일자',
  `MOD_DT` datetime NOT NULL COMMENT '수정일자',
  PRIMARY KEY (`GROUP_NO`,`USER_NO`),
  KEY `USER_GROUP_TB_FK` (`USER_NO`),
  CONSTRAINT `USER_GROUP_TB_FK` FOREIGN KEY (`USER_NO`) REFERENCES `user_tb` (`USER_NO`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USER_GROUP_TB_FK_1` FOREIGN KEY (`GROUP_NO`) REFERENCES `group_tb` (`GROUP_NO`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='그룹 참여자';


-- netwalk.activity_tb definition

CREATE TABLE `activity_tb` (
   `ACT_NO` int NOT NULL AUTO_INCREMENT COMMENT '활동내역번호',
   `USER_NO` int NOT NULL COMMENT '회원번호',
   `ACT_GB` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '활동 구분',
   `TOTAL_ACT_DISTANCE` double DEFAULT NULL COMMENT '총이동거리',
   `TOTAL_ACT_TIME` double DEFAULT NULL COMMENT '총활동시간',
   `SHARE_ST` int DEFAULT NULL COMMENT '공유여부',
   `ACT_ST` varchar(100) NOT NULL COMMENT '활동상태',
   `REG_DT` datetime NOT NULL COMMENT '등록일자',
   `MOD_DT` datetime NOT NULL COMMENT '수정일자',
   PRIMARY KEY (`ACT_NO`),
   KEY `ACTIVITY_TB_FK` (`USER_NO`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='활동내역';

-- netwalk.group_activity_tb definition

CREATE TABLE `group_activity_tb` (
 `GROUP_ACTIVITY_NO` int NOT NULL AUTO_INCREMENT COMMENT '그룹 활동 내역',
 `GROUP_NO` int NOT NULL COMMENT '그룹번호',
 `USER_NO` int NOT NULL COMMENT '회원번호',
 PRIMARY KEY (`GROUP_ACTIVITY_NO`),
 KEY `group_activity_tb_FK` (`GROUP_NO`),
 KEY `group_activity_tb_FK_1` (`USER_NO`),
 CONSTRAINT `group_activity_tb_FK` FOREIGN KEY (`GROUP_NO`) REFERENCES `group_tb` (`GROUP_NO`) ON DELETE CASCADE ON UPDATE CASCADE,
 CONSTRAINT `group_activity_tb_FK_1` FOREIGN KEY (`USER_NO`) REFERENCES `user_tb` (`USER_NO`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='그룹 활동 내역';

-- netwalk.activity_distance_tb definition

CREATE TABLE `activity_distance_tb` (
  `ACT_DIST_NO` int NOT NULL AUTO_INCREMENT COMMENT '활동이동경로번호',
  `ACT_NO` int NOT NULL COMMENT '활동내역번호',
  `LATITUDE` double NOT NULL COMMENT '이동 경로 위도',
  `LONGITUDE` double NOT NULL COMMENT '이동 경로 경도',
  PRIMARY KEY (`ACT_DIST_NO`),
  KEY `ACTIVITY_DISTANCE_TB_FK` (`ACT_NO`),
  CONSTRAINT `ACTIVITY_DISTANCE_TB_FK` FOREIGN KEY (`ACT_NO`) REFERENCES `activity_tb` (`ACT_NO`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='활동 이동 경로';

-- netwalk.activity_trash_tb definition

CREATE TABLE `activity_trash_tb` (
 `TRASH_NO` int NOT NULL AUTO_INCREMENT COMMENT '수집쓰레기번호',
 `ACT_NO` int NOT NULL COMMENT '활동내역번호',
 `IMG_URL` varchar(500) NOT NULL COMMENT '쓰레기 이미지 URL',
 `CATEGORY` varchar(100) NOT NULL COMMENT '카테고리',
 PRIMARY KEY (`TRASH_NO`),
 KEY `ACTIVITY_TRASH_TB_FK` (`ACT_NO`),
 CONSTRAINT `ACTIVITY_TRASH_TB_FK` FOREIGN KEY (`ACT_NO`) REFERENCES `activity_tb` (`ACT_NO`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='활동중 수집 쓰레기';