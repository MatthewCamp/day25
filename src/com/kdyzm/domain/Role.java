package com.kdyzm.domain;
/*
 * 对应着数据库中的role表。角色表
 */
public class Role {
	private String id;
	private String name;
	private String descc;
	public Role() {
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescc() {
		return descc;
	}
	public void setDescc(String descc) {
		this.descc = descc;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", descc=" + descc + "]";
	}
}
