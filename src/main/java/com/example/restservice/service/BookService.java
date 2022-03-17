package com.example.restservice.service;

import com.example.restservice.dto.Book;
import com.example.restservice.repository.Ibookrepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Book service.
 */
@Service
public class BookService implements Ibookservice {

  @Autowired
  private Ibookrepository data;

  @Override
  public List<Book> getBook() {
    return data.findAll();
  }

  @Override
  public Book post_put(Book b) {
    return data.save(b);
  }

  @Override
  public void delete(String expId) {
    data.deleteById(expId);
  }

  @Override
  public Optional<Book> getByKey(String expId) {
    return data.findById(expId);
  }

}
