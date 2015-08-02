package com.kdyzm.domain;

/*
 * 书的种类JavaBean
 * */
public class BookType {
	private String id;
	private String name;
	private String descc;
	private int amount;
	public BookType() {
	}
	public BookType(String id, String name, String descc, int amount) {
		super();
		this.id = id;
		this.name = name;
		this.descc = descc;
		this.amount = amount;
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "BookType [id=" + id + ", name=" + name + ", descc=" + descc
				+ ", amount=" + amount + "]";
	}
}
