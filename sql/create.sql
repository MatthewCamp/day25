DROP DATABASE bookstore;
CREATE DATABASE bookstore CHARACTER SET utf8;
USE bookstore;
/*���������ͱ�*/
CREATE TABLE booktype
(
	id VARCHAR(32),
	NAME VARCHAR(32),
	descc VARCHAR(200),/*�������*/
	CONSTRAINT  pk_booktypeid PRIMARY KEY(id)
);
/*�������*/
CREATE TABLE book
(
	id VARCHAR(32),
	NAME VARCHAR(32),
	price DOUBLE(10,2),
	auth VARCHAR(32),
	img VARCHAR(100),/*����ͼƬ����*/
	rebate DOUBLE(3,2),/*�ۿ�*/
	amount INT,/*���ж��ٱ���*/
	publisher VARCHAR(32),/*������*/
	publishdate VARCHAR(32),/*����ʱ��*/
	pages INT,/*�ж���ҳ*/
	size INT,/*��K*/
	printtimes VARCHAR(32),/*ӡ��*/
	versions INT,/*���*/
	brief TEXT,/*�������*/
	content TEXT,/*��ϸ����*/
	onlinetime VARCHAR(32),/*����ʱ��*/
	CONSTRAINT pk_bookid PRIMARY KEY (id)
);
/*�����������͵��м��*/
CREATE TABLE bt
(
	bookid VARCHAR(32),
	booktypeid VARCHAR(32),
	CONSTRAINT pk_bt PRIMARY KEY (bookid,booktypeid),
	CONSTRAINT fk_bookid FOREIGN KEY(bookid) REFERENCES book(id),
	CONSTRAINT fk_booktypeid FOREIGN KEY(booktypeid) REFERENCES booktype(id)
);
/*�����û���*/
CREATE TABLE USER
(
	id VARCHAR(32),
	NAME VARCHAR(32),
	PASSWORD VARCHAR(32),
	email VARCHAR(50),
	CONSTRAINT pk_user PRIMARY KEY (id),
	CONSTRAINT UNIQUE_USER UNIQUE(NAME)
)
/*��������Ա��*/
CREATE TABLE admin
(
   id VARCHAR(32),
   NAME VARCHAR(32),
   PASSWORD VARCHAR(32),
   sex CHAR(2),
   email VARCHAR(32),
   CONSTRAINT pk_admin PRIMARY KEY(id),
   CONSTRAINT unique_name UNIQUE(NAME),
   CONSTRAINT unique_email UNIQUE(email)
)
/*������ɫ��*/
CREATE TABLE role
(
   id VARCHAR(32),
   NAME VARCHAR(32),
   descc VARCHAR(32),
   CONSTRAINT pk_role PRIMARY KEY(id),
   CONSTRAINT unique_role_name UNIQUE(NAME)
);
/*�����˵���*/
CREATE TABLE menu 
(
   id VARCHAR(32),
   NAME VARCHAR(32),
   url VARCHAR(100),
   CONSTRAINT pk_menu PRIMARY KEY(id),
   CONSTRAINT unique_menu_name UNIQUE(NAME),
   CONSTRAINT unique_menu_url UNIQUE(url)
);
/*��������Ա-��ɫ��*/
CREATE TABLE `adminrole` (
   `adminid` varchar(32) NOT NULL DEFAULT '',
   `roleid` varchar(32) NOT NULL DEFAULT '',
   PRIMARY KEY (`adminid`,`roleid`),
   KEY `fk_adminrole_roleid` (`roleid`),
   CONSTRAINT `fk_adminrole_roleid` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`) ON DELETE CASCADE,
   CONSTRAINT `fk_adminrole_adminid` FOREIGN KEY (`adminid`) REFERENCES `admin` (`id`) ON DELETE CASCADE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*������ɫ-�˵���*/
CREATE TABLE `rolemenu` (
   `roleid` varchar(32) NOT NULL DEFAULT '',
   `menuid` varchar(32) NOT NULL DEFAULT '',
   PRIMARY KEY (`roleid`,`menuid`),
   KEY `fk_rolemenu_menuid` (`menuid`),
   CONSTRAINT `fk_rolemenu_menuid` FOREIGN KEY (`menuid`) REFERENCES `menu` (`id`) ON DELETE CASCADE,
   CONSTRAINT `fk_rolemenu_roleid` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`) ON DELETE CASCADE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
