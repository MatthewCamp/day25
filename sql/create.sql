DROP DATABASE bookstore;
CREATE DATABASE bookstore CHARACTER SET utf8;
USE bookstore;
/*创建书类型表*/
CREATE TABLE booktype
(
	id VARCHAR(32),
	NAME VARCHAR(32),
	descc VARCHAR(200),/*简短描述*/
	CONSTRAINT  pk_booktypeid PRIMARY KEY(id)
);
/*创建书表*/
CREATE TABLE book
(
	id VARCHAR(32),
	NAME VARCHAR(32),
	price DOUBLE(10,2),
	auth VARCHAR(32),
	img VARCHAR(100),/*封面图片名称*/
	rebate DOUBLE(3,2),/*折扣*/
	amount INT,/*还有多少本书*/
	publisher VARCHAR(32),/*出版社*/
	publishdate VARCHAR(32),/*出版时间*/
	pages INT,/*有多少页*/
	size INT,/*几K*/
	printtimes VARCHAR(32),/*印次*/
	versions INT,/*版次*/
	brief TEXT,/*简短描述*/
	content TEXT,/*详细描述*/
	onlinetime VARCHAR(32),/*上线时间*/
	CONSTRAINT pk_bookid PRIMARY KEY (id)
);
/*创建书与类型的中间表*/
CREATE TABLE bt
(
	bookid VARCHAR(32),
	booktypeid VARCHAR(32),
	CONSTRAINT pk_bt PRIMARY KEY (bookid,booktypeid),
	CONSTRAINT fk_bookid FOREIGN KEY(bookid) REFERENCES book(id),
	CONSTRAINT fk_booktypeid FOREIGN KEY(booktypeid) REFERENCES booktype(id)
);
/*创建用户表*/
CREATE TABLE USER
(
	id VARCHAR(32),
	NAME VARCHAR(32),
	PASSWORD VARCHAR(32),
	email VARCHAR(50),
	CONSTRAINT pk_user PRIMARY KEY (id),
	CONSTRAINT UNIQUE_USER UNIQUE(NAME)
)
/*创建管理员表*/
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
/*创建角色表*/
CREATE TABLE role
(
   id VARCHAR(32),
   NAME VARCHAR(32),
   descc VARCHAR(32),
   CONSTRAINT pk_role PRIMARY KEY(id),
   CONSTRAINT unique_role_name UNIQUE(NAME)
);
/*创建菜单表*/
CREATE TABLE menu 
(
   id VARCHAR(32),
   NAME VARCHAR(32),
   url VARCHAR(100),
   CONSTRAINT pk_menu PRIMARY KEY(id),
   CONSTRAINT unique_menu_name UNIQUE(NAME),
   CONSTRAINT unique_menu_url UNIQUE(url)
);
/*创建管理员-角色表*/
CREATE TABLE `adminrole` (
   `adminid` varchar(32) NOT NULL DEFAULT '',
   `roleid` varchar(32) NOT NULL DEFAULT '',
   PRIMARY KEY (`adminid`,`roleid`),
   KEY `fk_adminrole_roleid` (`roleid`),
   CONSTRAINT `fk_adminrole_roleid` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`) ON DELETE CASCADE,
   CONSTRAINT `fk_adminrole_adminid` FOREIGN KEY (`adminid`) REFERENCES `admin` (`id`) ON DELETE CASCADE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
/*创建角色-菜单表*/
CREATE TABLE `rolemenu` (
   `roleid` varchar(32) NOT NULL DEFAULT '',
   `menuid` varchar(32) NOT NULL DEFAULT '',
   PRIMARY KEY (`roleid`,`menuid`),
   KEY `fk_rolemenu_menuid` (`menuid`),
   CONSTRAINT `fk_rolemenu_menuid` FOREIGN KEY (`menuid`) REFERENCES `menu` (`id`) ON DELETE CASCADE,
   CONSTRAINT `fk_rolemenu_roleid` FOREIGN KEY (`roleid`) REFERENCES `role` (`id`) ON DELETE CASCADE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8
