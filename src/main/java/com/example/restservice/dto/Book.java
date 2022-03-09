package com.example.restservice.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Book")
public class Book {

	@Id
	private String id;
	private double price;
	private String title;
	private String author;
	private String category;
	private String language;
	private String editorial;

	public Book() {
		
	}
	
	public Book(String id, double price, String title, String author, 
			String category, String language, String editorial) {
		this.id = id;
		this.price = price;
		this.title = title;
		this.author = author;
		this.category = category;
		this.language = language;
		this.editorial = editorial;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

}