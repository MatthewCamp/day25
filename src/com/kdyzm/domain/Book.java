package com.kdyzm.domain;
import java.io.Serializable;
/*
 *  ÈºÆJavaBean
 */
import java.util.Date;

public class Book implements Serializable{
	private static final long serialVersionUID = 6174585861756723743L;
	private String id;
	private String name;
	private double price;
	private String auth;
	private String img;
	private double rebate;
	private int amount;
	private String publisher;
	private String publishdate;
	private int pages;
	private int size;
	private int printtimes;
	private int versions;
	private String brief;
	private String content;
	private String onlinetime;
	
	public Book() {
	}
	public Book(String id, String name, double price, String auth, String img,
			double rebate, int amount, String publisher, String publishdate,
			int pages, int size, int printtimes, int version, String brief,
			String content, String onlinetime) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.auth = auth;
		this.img = img;
		this.rebate = rebate;
		this.amount = amount;
		this.publisher = publisher;
		this.publishdate = publishdate;
		this.pages = pages;
		this.size = size;
		this.printtimes = printtimes;
		this.versions = version;
		this.brief = brief;
		this.content = content;
		this.onlinetime = onlinetime;
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public double getRebate() {
		return rebate;
	}
	public void setRebate(double rebate) {
		this.rebate = rebate;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPublishdate() {
		return publishdate;
	}
	public void setPublishdate(String publishdate) {
		this.publishdate = publishdate;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getPrinttimes() {
		return printtimes;
	}
	public void setPrinttimes(int printtimes) {
		this.printtimes = printtimes;
	}
	public int getVersions() {
		return versions;
	}
	public void setVersions(int versions) {
		this.versions = versions;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOnlinetime() {
		return onlinetime;
	}
	public void setOnlinetime(String onlinetime) {
		this.onlinetime = onlinetime;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", price=" + price
				+ ", auth=" + auth + ", img=" + img + ", rebate=" + rebate
				+ ", amount=" + amount + ", publisher=" + publisher
				+ ", publishdate=" + publishdate + ", pages=" + pages
				+ ", size=" + size + ", printtimes=" + printtimes
				+ ", version=" + versions + ", brief=" + brief + ", content="
				+ content + ", onlinetime=" + onlinetime + "]";
	}
}
