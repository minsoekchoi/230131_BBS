1) 2개의 시퀀스 생성
    - bbs_t_seq
    - comment_t_seq
    
    create sequence bbs_t_seq
    increment by 1
    start with 1;

    create sequence comment_t_seq
    increment by 1
    start with 1;
	
2) 2개의 테이블 생성

    CREATE TABLE bbs_t(
		b_idx 	NUMBER(6,0) CONSTRAINT bbs_t_pk PRIMARY KEY,
		subject VARCHAR2(50),
		writer	VARCHAR2(20),
		content CLOB,
		f_name VARCHAR2(50),
		pwd	  VARCHAR2(20),
		write_date DATE,
		hit	   NUMBER(4)
     );

    CREATE TABLE comment_t(
	c_idx 	NUMBER(6,0) CONSTRAINT comm_t_pk PRIMARY KEY,
	writer	 VARCHAR2(20),
	content  CLOB,
	pwd	  VARCHAR2(20),
	write_date DATE,
	b_idx	   NUMBER(6,0),
	CONSTRAINT comm_t_fk FOREIGN KEY (b_idx) REFERENCES bbs_t(b_idx)
     );

     