/*��ʼ��user��*/
INSERT INTO USER VALUES('U001','����','123456','111@163.com');
INSERT INTO USER VALUES('U002','����','123456','112@163.com');
INSERT INTO USER VALUES('U003','����','123456','113@163.com');
INSERT INTO USER VALUES('U004','����','123456','114@163.com');
INSERT INTO USER VALUES('U005','����','123456','115@163.com');
INSERT INTO USER VALUES('U006','����','123456','116@163.com');
INSERT INTO USER VALUES('U007','Сǿ','123456','117@163.com');
/*��ʼ��admin��*/
INSERT INTO admin VALUES('001','����','123','��','001@163.com');
INSERT INTO admin VALUES('002','����','123','��','002@163.com');
insert into admin values('003','����','123','��','003@163.com');
/*��ʼ����ɫ��role*/
INSERT INTO role VALUES('R001','ϵͳ����Ա','ӵ�����Ȩ��');
INSERT INTO role VALUES('R002','ͼ�����Ա','ӵ�дθ�Ȩ��');
INSERT INTO role VALUES('R003','������Ա','ֻ�ܿ��Ʒ���');
/*��ʼ���˵���menu*/
INSERT  INTO `menu`(`id`,`NAME`,`url`) VALUES 
('M001','��ɫ����','/admin/secure/roleManageServlet'),
('M002','�û�����','/admin/secure/userManageServlet'),
('M003','ͼ�����','/admin/secure/bookTypeManageServlet'),
('M004','ͼ�����','/admin/secure/bookManageServlet'),
('M005','ͼ���ϼ�','/admin/secure/addNewBookServlet'),
('M006','��������','/admin/secure/consignmentServlet');



/*��ʼ��adminrole���൱�ڸ�ĳ������Ա������ĳ������ɫӵ�е�Ȩ��*/
	/*�������������н�ɫȨ��*/
INSERT INTO adminrole VALUES('001','R001');
INSERT INTO adminrole VALUES('001','R002');
INSERT INTO adminrole VALUES('001','R003');
	/*�����ļ���ͼ�����Ա�Ľ�ɫ*/
INSERT INTO adminrole VALUES('002','R002');
	/*��������Ϸ�����Ա�Ľ�ɫ*/
INSERT INTO adminrole VALUES('003','R003');


/*��ʼ��rolemenu���൱�ڸ�ĳ����ɫ�����˷���ĳ����Ŀ¼��Ȩ��*/
	/*��ϵͳ����Ա���Ϸ�������Ŀ¼��Ȩ��*/
INSERT INTO rolemenu VALUES('R001','M001');
INSERT INTO rolemenu VALUES('R001','M002');
INSERT INTO rolemenu VALUES('R001','M003');
INSERT INTO rolemenu VALUES('R001','M004');
INSERT INTO rolemenu VALUES('R001','M005');
INSERT INTO rolemenu VALUES('R001','M006');
	/*��ͼ�����Ա������Ӧ��Ȩ��*/
INSERT INTO rolemenu VALUES('R002','M003');
INSERT INTO rolemenu VALUES('R002','M004');
INSERT INTO rolemenu VALUES('R002','M005');
	/*��������Ա���Ϸ�����Ȩ��*/
INSERT INTO rolemenu VALUES('R003','M006');
