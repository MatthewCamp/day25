/*初始化user表*/
INSERT INTO USER VALUES('U001','张三','123456','111@163.com');
INSERT INTO USER VALUES('U002','李四','123456','112@163.com');
INSERT INTO USER VALUES('U003','王五','123456','113@163.com');
INSERT INTO USER VALUES('U004','赵六','123456','114@163.com');
INSERT INTO USER VALUES('U005','陈七','123456','115@163.com');
INSERT INTO USER VALUES('U006','旺财','123456','116@163.com');
INSERT INTO USER VALUES('U007','小强','123456','117@163.com');
/*初始化admin表*/
INSERT INTO admin VALUES('001','张三','123','男','001@163.com');
INSERT INTO admin VALUES('002','李四','123','男','002@163.com');
insert into admin values('003','王五','123','男','003@163.com');
/*初始化角色表role*/
INSERT INTO role VALUES('R001','系统管理员','拥有最高权限');
INSERT INTO role VALUES('R002','图书管理员','拥有次高权限');
INSERT INTO role VALUES('R003','发货人员','只能控制发货');
/*初始化菜单表menu*/
INSERT  INTO `menu`(`id`,`NAME`,`url`) VALUES 
('M001','角色管理','/admin/secure/roleManageServlet'),
('M002','用户管理','/admin/secure/userManageServlet'),
('M003','图书分类','/admin/secure/bookTypeManageServlet'),
('M004','图书管理','/admin/secure/bookManageServlet'),
('M005','图书上架','/admin/secure/addNewBookServlet'),
('M006','发货管理','/admin/secure/consignmentServlet');



/*初始化adminrole表，相当于给某个管理员加上了某几个角色拥有的权限*/
	/*给张三加上所有角色权限*/
INSERT INTO adminrole VALUES('001','R001');
INSERT INTO adminrole VALUES('001','R002');
INSERT INTO adminrole VALUES('001','R003');
	/*给李四加上图书管理员的角色*/
INSERT INTO adminrole VALUES('002','R002');
	/*给王五加上发货人员的角色*/
INSERT INTO adminrole VALUES('003','R003');


/*初始化rolemenu表，相当于给某个橘色加上了访问某几个目录的权限*/
	/*给系统管理员加上访问所有目录的权限*/
INSERT INTO rolemenu VALUES('R001','M001');
INSERT INTO rolemenu VALUES('R001','M002');
INSERT INTO rolemenu VALUES('R001','M003');
INSERT INTO rolemenu VALUES('R001','M004');
INSERT INTO rolemenu VALUES('R001','M005');
INSERT INTO rolemenu VALUES('R001','M006');
	/*给图书管理员加上相应的权限*/
INSERT INTO rolemenu VALUES('R002','M003');
INSERT INTO rolemenu VALUES('R002','M004');
INSERT INTO rolemenu VALUES('R002','M005');
	/*给发货人员加上发货的权限*/
INSERT INTO rolemenu VALUES('R003','M006');
