package com.kdyzm.buy.car;

import java.io.Serializable;

import com.kdyzm.domain.Book;
/*
 * 购物车实体类
 */
public class Car implements Serializable{
	private static final long serialVersionUID = 2732712154580848259L;
	private Book book;
	private int amount;
	
	public Car() {
	}
	public Car(Book book, int amount) {
		this.book = book;
		this.amount = amount;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Car [book=" + book + ", amount=" + amount + "]";
	}
	
}
