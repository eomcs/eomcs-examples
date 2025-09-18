-- 수강신청
DROP TABLE IF EXISTS lms_application RESTRICT;

-- 과정
DROP TABLE IF EXISTS lms_lectures RESTRICT;

-- 매니저
DROP TABLE IF EXISTS lms_managers RESTRICT;

-- 강의실
DROP TABLE IF EXISTS lms_rooms RESTRICT;

-- 강사
DROP TABLE IF EXISTS lms_teachers RESTRICT;

-- 강의실사진
DROP TABLE IF EXISTS lms_photos RESTRICT;

-- 교육센터
DROP TABLE IF EXISTS lms_centers RESTRICT;

-- 주소
DROP TABLE IF EXISTS lms_address RESTRICT;

-- 학생
DROP TABLE IF EXISTS lms_students RESTRICT;

-- 학력
DROP TABLE IF EXISTS lms_level RESTRICT;

-- 강의배정
DROP TABLE IF EXISTS lms_teacher_lecture RESTRICT;

-- 부서
DROP TABLE IF EXISTS lms_departments RESTRICT;

-- 회원
DROP TABLE IF EXISTS lms_members RESTRICT;

-- 수강신청
CREATE TABLE lms_application (
  application_no INTEGER  NOT NULL COMMENT '수강신청번호', -- 수강신청번호
  lecture_no     INTEGER  NOT NULL COMMENT '과정번호', -- 과정번호
  student_no     INTEGER  NOT NULL COMMENT '학생번호', -- 학생번호
  register_dt    DATETIME NOT NULL DEFAULT (now()) COMMENT '신청일', -- 신청일
  state          CHAR(1)  NULL     COMMENT '상태' -- 상태
)
COMMENT '수강신청';

-- 수강신청
ALTER TABLE lms_application
  ADD CONSTRAINT PK_lms_application -- 수강신청 기본키
  PRIMARY KEY (
  application_no -- 수강신청번호
  );

ALTER TABLE lms_application
  MODIFY COLUMN application_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '수강신청번호';

-- 과정
CREATE TABLE lms_lectures (
  lecture_no INTEGER      NOT NULL COMMENT '과정번호', -- 과정번호
  title      VARCHAR(255) NOT NULL COMMENT '과정명', -- 과정명
  content    MEDIUMTEXT   NOT NULL COMMENT '내용', -- 내용
  qty        INTEGER      NOT NULL COMMENT '모집인원', -- 모집인원
  start_dt   DATE         NOT NULL COMMENT '시작일', -- 시작일
  end_dt     DATE         NOT NULL COMMENT '종료일', -- 종료일
  day_hr     INTEGER      NOT NULL COMMENT '일교육시간', -- 일교육시간
  total_hr   INTEGER      NOT NULL COMMENT '총교육시간', -- 총교육시간
  support    BOOLEAN      NOT NULL COMMENT '국비지원여부', -- 국비지원여부
  room_no    INTEGER      NULL     COMMENT '강의실번호', -- 강의실번호
  manager_no INTEGER      NULL     COMMENT '매니저번호' -- 매니저번호
)
COMMENT '과정';

-- 과정
ALTER TABLE lms_lectures
  ADD CONSTRAINT PK_lms_lectures -- 과정 기본키
  PRIMARY KEY (
  lecture_no -- 과정번호
  );

ALTER TABLE lms_lectures
  MODIFY COLUMN lecture_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '과정번호';

-- 매니저
CREATE TABLE lms_managers (
  manager_no    INTEGER     NOT NULL COMMENT '매니저번호', -- 매니저번호
  position      VARCHAR(60) NULL     COMMENT '직급', -- 직급
  fax           VARCHAR(30) NULL     COMMENT '팩스', -- 팩스
  department_no INTEGER     NOT NULL COMMENT '부서번호' -- 부서번호
)
COMMENT '매니저';

-- 매니저
ALTER TABLE lms_managers
  ADD CONSTRAINT PK_lms_managers -- 매니저 기본키
  PRIMARY KEY (
  manager_no -- 매니저번호
  );

-- 강의실
CREATE TABLE lms_rooms (
  room_no   INTEGER     NOT NULL COMMENT '강의실번호', -- 강의실번호
  center_no INTEGER     NOT NULL COMMENT '교육센터번호', -- 교육센터번호
  title     VARCHAR(60) NOT NULL COMMENT '강의실명', -- 강의실명
  qty       INTEGER     NOT NULL COMMENT '수용가능인원' -- 수용가능인원
)
COMMENT '강의실';

-- 강의실
ALTER TABLE lms_rooms
  ADD CONSTRAINT PK_lms_rooms -- 강의실 기본키
  PRIMARY KEY (
  room_no -- 강의실번호
  );

ALTER TABLE lms_rooms
  MODIFY COLUMN room_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '강의실번호';

-- 강사
CREATE TABLE lms_teachers (
  teacher_no INTEGER      NOT NULL COMMENT '강사번호', -- 강사번호
  hour_pay   INTEGER      NULL     COMMENT '시강료', -- 시강료
  photo      VARCHAR(255) NOT NULL COMMENT '사진', -- 사진
  career     TEXT         NULL     COMMENT '경력' -- 경력
)
COMMENT '강사';

-- 강사
ALTER TABLE lms_teachers
  ADD CONSTRAINT PK_lms_teachers -- 강사 기본키
  PRIMARY KEY (
  teacher_no -- 강사번호
  );

-- 강의실사진
CREATE TABLE lms_photos (
  photo_no INTEGER      NOT NULL COMMENT '강의실사진번호', -- 강의실사진번호
  filepath VARCHAR(255) NOT NULL COMMENT '사진경로', -- 사진경로
  room_no  INTEGER      NOT NULL COMMENT '강의실번호' -- 강의실번호
)
COMMENT '강의실사진';

-- 강의실사진
ALTER TABLE lms_photos
  ADD CONSTRAINT PK_lms_photos -- 강의실사진 기본키
  PRIMARY KEY (
  photo_no -- 강의실사진번호
  );

ALTER TABLE lms_photos
  MODIFY COLUMN photo_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '강의실사진번호';

-- 교육센터
CREATE TABLE lms_centers (
  center_no INTEGER     NOT NULL COMMENT '교육센터번호', -- 교육센터번호
  title     VARCHAR(60) NOT NULL COMMENT '지점명' -- 지점명
)
COMMENT '교육센터';

-- 교육센터
ALTER TABLE lms_centers
  ADD CONSTRAINT PK_lms_centers -- 교육센터 기본키
  PRIMARY KEY (
  center_no -- 교육센터번호
  );

-- 교육센터 유니크 인덱스
CREATE UNIQUE INDEX UIX_lms_centers
  ON lms_centers ( -- 교육센터
    title ASC -- 지점명
  );

ALTER TABLE lms_centers
  MODIFY COLUMN center_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '교육센터번호';

-- 주소
CREATE TABLE lms_address (
  address_no INTEGER      NOT NULL COMMENT '주소번호', -- 주소번호
  postno     VARCHAR(10)  NOT NULL COMMENT '우편번호', -- 우편번호
  basic_addr VARCHAR(255) NOT NULL COMMENT '기본주소' -- 기본주소
)
COMMENT '주소';

-- 주소
ALTER TABLE lms_address
  ADD CONSTRAINT PK_lms_address -- 주소 기본키
  PRIMARY KEY (
  address_no -- 주소번호
  );

-- 주소 인덱스
CREATE INDEX IX_lms_address
  ON lms_address( -- 주소
    postno ASC -- 우편번호
  );

ALTER TABLE lms_address
  MODIFY COLUMN address_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '주소번호';

-- 학생
CREATE TABLE lms_students (
  student_no  INTEGER      NOT NULL COMMENT '학생번호', -- 학생번호
  jumin       VARCHAR(13)  NULL     COMMENT '주민번호', -- 주민번호
  level_no    INTEGER      NULL     COMMENT '학력번호', -- 학력번호
  school      VARCHAR(60)  NULL     COMMENT '학교', -- 학교
  major       VARCHAR(60)  NULL     COMMENT '전공', -- 전공
  detail_addr VARCHAR(255) NULL     COMMENT '상세주소', -- 상세주소
  working     BOOLEAN      NOT NULL COMMENT '재직여부', -- 재직여부
  address_no  INTEGER      NOT NULL COMMENT '주소번호' -- 주소번호
)
COMMENT '학생';

-- 학생
ALTER TABLE lms_students
  ADD CONSTRAINT PK_lms_students -- 학생 기본키
  PRIMARY KEY (
  student_no -- 학생번호
  );

-- 학생 유니크 인덱스2
CREATE UNIQUE INDEX UIX_lms_students2
  ON lms_students ( -- 학생
    jumin ASC -- 주민번호
  );

-- 학력
CREATE TABLE lms_level (
  level_no INTEGER     NOT NULL COMMENT '학력번호', -- 학력번호
  title    VARCHAR(60) NOT NULL COMMENT '학력명' -- 학력명
)
COMMENT '학력';

-- 학력
ALTER TABLE lms_level
  ADD CONSTRAINT PK_lms_level -- 학력 기본키
  PRIMARY KEY (
  level_no -- 학력번호
  );

-- 학력 유니크 인덱스
CREATE UNIQUE INDEX UIX_lms_level
  ON lms_level ( -- 학력
    title ASC -- 학력명
  );

ALTER TABLE lms_level
  MODIFY COLUMN level_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '학력번호';

-- 강의배정
CREATE TABLE lms_teacher_lecture (
  lecture_no INTEGER NOT NULL COMMENT '과정번호', -- 과정번호
  teacher_no INTEGER NOT NULL COMMENT '강사번호' -- 강사번호
)
COMMENT '강의배정';

-- 강의배정
ALTER TABLE lms_teacher_lecture
  ADD CONSTRAINT PK_lms_teacher_lecture -- 강의배정 기본키
  PRIMARY KEY (
  lecture_no, -- 과정번호
  teacher_no  -- 강사번호
  );

-- 부서
CREATE TABLE lms_departments (
  department_no INTEGER     NOT NULL COMMENT '부서번호', -- 부서번호
  title         VARCHAR(60) NOT NULL COMMENT '부서명' -- 부서명
)
COMMENT '부서';

-- 부서
ALTER TABLE lms_departments
  ADD CONSTRAINT PK_lms_departments -- 부서 기본키
  PRIMARY KEY (
  department_no -- 부서번호
  );

-- 부서 유니크 인덱스
CREATE UNIQUE INDEX UIX_lms_departments
  ON lms_departments ( -- 부서
    title ASC -- 부서명
  );

ALTER TABLE lms_departments
  MODIFY COLUMN department_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '부서번호';

-- 회원
CREATE TABLE lms_members (
  member_no   INTEGER     NOT NULL COMMENT '회원번호', -- 회원번호
  name        VARCHAR(60) NOT NULL COMMENT '이름', -- 이름
  email       VARCHAR(40) NOT NULL COMMENT '이메일', -- 이메일
  tel         VARCHAR(30) NOT NULL COMMENT '전화', -- 전화
  register_dt DATE        NULL     DEFAULT (curdate()) COMMENT '가입일' -- 가입일
)
COMMENT '회원';

-- 회원
ALTER TABLE lms_members
  ADD CONSTRAINT PK_lms_members -- 회원 기본키
  PRIMARY KEY (
  member_no -- 회원번호
  );

-- 회원 유니크 인덱스
CREATE UNIQUE INDEX UIX_lms_members
  ON lms_members ( -- 회원
    email ASC -- 이메일
  );

-- 회원 인덱스
CREATE INDEX IX_lms_members
  ON lms_members( -- 회원
    name ASC -- 이름
  );

ALTER TABLE lms_members
  MODIFY COLUMN member_no INTEGER NOT NULL AUTO_INCREMENT COMMENT '회원번호';

-- 수강신청
ALTER TABLE lms_application
  ADD CONSTRAINT FK_lms_lectures_TO_lms_application -- 과정 -> 수강신청
  FOREIGN KEY (
  lecture_no -- 과정번호
  )
  REFERENCES lms_lectures ( -- 과정
  lecture_no -- 과정번호
  );

-- 수강신청
ALTER TABLE lms_application
  ADD CONSTRAINT FK_lms_students_TO_lms_application -- 학생 -> 수강신청
  FOREIGN KEY (
  student_no -- 학생번호
  )
  REFERENCES lms_students ( -- 학생
  student_no -- 학생번호
  );

-- 과정
ALTER TABLE lms_lectures
  ADD CONSTRAINT FK_lms_managers_TO_lms_lectures -- 매니저 -> 과정
  FOREIGN KEY (
  manager_no -- 매니저번호
  )
  REFERENCES lms_managers ( -- 매니저
  manager_no -- 매니저번호
  );

-- 과정
ALTER TABLE lms_lectures
  ADD CONSTRAINT FK_lms_rooms_TO_lms_lectures -- 강의실 -> 과정
  FOREIGN KEY (
  room_no -- 강의실번호
  )
  REFERENCES lms_rooms ( -- 강의실
  room_no -- 강의실번호
  );

-- 매니저
ALTER TABLE lms_managers
  ADD CONSTRAINT FK_lms_departments_TO_lms_managers -- 부서 -> 매니저
  FOREIGN KEY (
  department_no -- 부서번호
  )
  REFERENCES lms_departments ( -- 부서
  department_no -- 부서번호
  );

-- 매니저
ALTER TABLE lms_managers
  ADD CONSTRAINT FK_lms_members_TO_lms_managers -- 회원 -> 매니저
  FOREIGN KEY (
  manager_no -- 매니저번호
  )
  REFERENCES lms_members ( -- 회원
  member_no -- 회원번호
  );

-- 강의실
ALTER TABLE lms_rooms
  ADD CONSTRAINT FK_lms_centers_TO_lms_rooms -- 교육센터 -> 강의실
  FOREIGN KEY (
  center_no -- 교육센터번호
  )
  REFERENCES lms_centers ( -- 교육센터
  center_no -- 교육센터번호
  );

-- 강사
ALTER TABLE lms_teachers
  ADD CONSTRAINT FK_lms_members_TO_lms_teachers -- 회원 -> 강사
  FOREIGN KEY (
  teacher_no -- 강사번호
  )
  REFERENCES lms_members ( -- 회원
  member_no -- 회원번호
  );

-- 강의실사진
ALTER TABLE lms_photos
  ADD CONSTRAINT FK_lms_rooms_TO_lms_photos -- 강의실 -> 강의실사진
  FOREIGN KEY (
  room_no -- 강의실번호
  )
  REFERENCES lms_rooms ( -- 강의실
  room_no -- 강의실번호
  );

-- 학생
ALTER TABLE lms_students
  ADD CONSTRAINT FK_lms_address_TO_lms_students -- 주소 -> 학생
  FOREIGN KEY (
  address_no -- 주소번호
  )
  REFERENCES lms_address ( -- 주소
  address_no -- 주소번호
  );

-- 학생
ALTER TABLE lms_students
  ADD CONSTRAINT FK_lms_level_TO_lms_students -- 학력 -> 학생
  FOREIGN KEY (
  level_no -- 학력번호
  )
  REFERENCES lms_level ( -- 학력
  level_no -- 학력번호
  );

-- 학생
ALTER TABLE lms_students
  ADD CONSTRAINT FK_lms_members_TO_lms_students -- 회원 -> 학생
  FOREIGN KEY (
  student_no -- 학생번호
  )
  REFERENCES lms_members ( -- 회원
  member_no -- 회원번호
  );

-- 강의배정
ALTER TABLE lms_teacher_lecture
  ADD CONSTRAINT FK_lms_teachers_TO_lms_teacher_lecture -- 강사 -> 강의배정
  FOREIGN KEY (
  teacher_no -- 강사번호
  )
  REFERENCES lms_teachers ( -- 강사
  teacher_no -- 강사번호
  );

-- 강의배정
ALTER TABLE lms_teacher_lecture
  ADD CONSTRAINT FK_lms_lectures_TO_lms_teacher_lecture -- 과정 -> 강의배정
  FOREIGN KEY (
  lecture_no -- 과정번호
  )
  REFERENCES lms_lectures ( -- 과정
  lecture_no -- 과정번호
  );