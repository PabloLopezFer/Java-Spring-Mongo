package com.example.restservice.service;

import java.util.List;
import java.util.Optional;

import com.example.restservice.dto.Book;

public interface IBookService {

	public List<Book> getBook();
	public Book post_put(Book b);
	public void delete(String expID);
	public Optional<Book> getByKey (String expId);
	
}
