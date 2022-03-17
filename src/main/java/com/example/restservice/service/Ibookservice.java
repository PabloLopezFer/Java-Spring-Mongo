package com.example.restservice.service;

import com.example.restservice.dto.Book;
import java.util.List;
import java.util.Optional;

/**
 * Interface book service.
 */
public interface Ibookservice {

  public List<Book> getBook();
  
  public Book post_put(Book b);
  
  public void delete(String bookId);
  
  public Optional<Book> getByKey(String bookId);

}
