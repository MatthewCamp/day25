package com.kdyzm.domain;
/*
 * ��Ӧ�����ݿ���е�menu��
 */
public class Menu {
	private String id;
	private String name;
	private String url;
	public Menu() {
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", url=" + url + "]";
	}
}
