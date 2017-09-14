/*Drop Databse */

show tables;
drop table religion; 
create database app;
show tables;
SHOW PROCEDURE STATUS;
SHOW FUNCTION STATUS;
select * from images;
truncate profile;

create table gender(
		genderid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
		genderName varchar(50),
		gender_desc varchar(50),
		active char,
		c_date timestamp,
		m_date timestamp
  );
CREATE TRIGGER gender_insert BEFORE INSERT ON gender FOR EACH ROW SET @c_date=CURRENT_TIMESTAMP,@m_date=CURRENT_TIMESTAMP;
CREATE TRIGGER gender_update BEFORE UPDATE ON gender FOR EACH ROW SET @m_date=CURRENT_TIMESTAMP;

  create table religion(
		religionid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
		religionname varchar(50),
		religion_desc varchar(50),
		active char,
		c_date TIMESTAMP,
		m_date TIMESTAMP
  );

CREATE TRIGGER religion_insert BEFORE INSERT ON religion FOR EACH ROW SET @c_date=CURRENT_TIMESTAMP,@m_date=CURRENT_TIMESTAMP;
CREATE TRIGGER religion_update BEFORE UPDATE ON religion FOR EACH ROW SET @m_date=CURRENT_TIMESTAMP;

INSERT INTO religion(religionname,religion_desc,active) values('Hindu','Hindu','Y');
INSERT INTO religion(religionname,religion_desc,active) values('Christian','Christian','Y');
INSERT INTO religion(religionname,religion_desc,active) values('Muslim','Muslim','Y');
INSERT INTO religion(religionname,religion_desc,active) values('Sikh','Sikh','Y');

create table marital(
		maritalnbr INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
		maritalname varchar(50),
		marital_desc varchar(50),
		active char,
		c_date TIMESTAMP,
		m_date TIMESTAMP
);

CREATE TRIGGER marital_insert BEFORE INSERT ON marital FOR EACH ROW SET @c_date=CURRENT_TIMESTAMP,@m_date=CURRENT_TIMESTAMP;
CREATE TRIGGER marital_update BEFORE UPDATE ON marital FOR EACH ROW SET @m_date=CURRENT_TIMESTAMP;

insert into marital(maritalname,marital_desc,active) values('Unmarried','Unmarried','Y');
insert into marital(maritalname,marital_desc,active) values('Married','Married','Y');



CREATE TABLE profile(
		profileid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
		homeurl varchar(50),
		pass  varchar(50), 
		f_name  varchar(50), 
		l_name  varchar(50),
		email  varchar(50),
		img VARCHAR(50),
		no_msgs int DEFAULT 0,
		followers int DEFAULT 0, 
		following int DEFAULT 0,
		c_date TIMESTAMP,
		m_date TIMESTAMP
	);
    
	select * from profile;

delete from profile where profileid=2;

truncate profile;
drop table profile;
CREATE TRIGGER profile_insert BEFORE INSERT ON profile FOR EACH ROW SET @c_date=CURRENT_TIMESTAMP,@m_date=CURRENT_TIMESTAMP;
CREATE TRIGGER profile_update BEFORE UPDATE ON profile FOR EACH ROW SET @m_date=CURRENT_TIMESTAMP;
   

CREATE TABLE profile_detail(
		profileid INTEGER NOT NULL PRIMARY KEY,FOREIGN KEY(profileid) REFERENCES profile(profileid), 
		phone BIGINT, 
		address varchar(50),	
		dob DATE, 
		active char, 
		c_date TIMESTAMP, 
		m_date TIMESTAMP, 
		text_file text,
		image BLOB, 
		gender_id INTEGER, FOREIGN KEY(gender_id) REFERENCES gender(genderid), 
		religion_id INTEGER, FOREIGN KEY(religion_id) REFERENCES religion(religionid),
		marital_id INTEGER, FOREIGN KEY(marital_id) REFERENCES marital(maritalnbr)
    ) ;
    
   select * from profile_detail; 
  truncate profile_detail;  
  drop table profile_detail;
    
CREATE TRIGGER profile_detail_insert BEFORE INSERT ON profile_detail FOR EACH ROW SET @c_date=CURRENT_TIMESTAMP,@m_date=CURRENT_TIMESTAMP;
CREATE TRIGGER profile_detail_update BEFORE UPDATE ON profile_detail FOR EACH ROW SET @m_date=CURRENT_TIMESTAMP;

    
create table messages(
		msgnbr INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
		msg varchar(200),
		c_date TIMESTAMP,
		m_date TIMESTAMP,
		shared_usr INTEGER NOT NULL,FOREIGN KEY(shared_usr) REFERENCES profile(profileid)
     );
    
create table images(
		imgid INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
		header varchar(20),
		des varchar(20),
		path varchar(50),
		userid INTEGER, FOREIGN KEY(userid) REFERENCES profile(profileid)
  );
  
    
  create table relation(
		releation_id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
		c_date TIMESTAMP,
		m_date TIMESTAMP,
		activeind char DEFAULT 'Y',
		des varchar(100),
		friend INTEGER, FOREIGN KEY(friend) REFERENCES profile(profileid),
		userid INTEGER , FOREIGN KEY(userid) REFERENCES profile(profileid),
		INDEX (userid) 
   );
    
    drop table RELATION;
    
CREATE TRIGGER Messages_INSERT BEFORE INSERT ON messages FOR EACH ROW SET @c_date=CURRENT_TIMESTAMP,@m_date=CURRENT_TIMESTAMP;
CREATE TRIGGER Messages_UPDATE BEFORE UPDATE ON messages FOR EACH ROW SET @m_date=CURRENT_TIMESTAMP;

CREATE TRIGGER IMAGES_INSERT BEFORE INSERT ON IMAGES FOR EACH ROW SET @c_date=CURRENT_TIMESTAMP,@m_date=CURRENT_TIMESTAMP;
CREATE TRIGGER IMAGES_UPDATE BEFORE UPDATE ON IMAGES FOR EACH ROW SET @m_date=CURRENT_TIMESTAMP;


CREATE TRIGGER RELATION_INSERT BEFORE INSERT ON relation FOR EACH ROW SET @c_date=CURRENT_TIMESTAMP,@m_date=CURRENT_TIMESTAMP;
CREATE TRIGGER RELATION_UPDATE BEFORE UPDATE ON relation FOR EACH ROW SET @m_date=CURRENT_TIMESTAMP;
    

select * from gender;

create procedure emailCount(IN emai varchar(20), OUT count int)
     BEGIN
     select count(*) into count from profile where profile.email =email;
END
    
    