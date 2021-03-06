package com.example.restservice.controller;

import com.example.restservice.dto.Book;
import com.example.restservice.service.Ibookservice;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Book controller.
 */
@RestController
@RequestMapping("/books")
public class BookController {

  @Autowired
  private Ibookservice service;
  
  /**
   * List all books.
   *
   * @return ResponseEntity
   */
  @GetMapping
  public ResponseEntity<List<Book>> getBook() {
    return ResponseEntity.status(HttpStatus.OK).body(service.getBook());
  }

  /**
   * New book.
   *
   * @param b -
   * @return ResponseEntity
   */
  @PostMapping
  public ResponseEntity<Book> post(@RequestBody Book b) {
    if (b.getId() == null) {
      b.setId(UUID.randomUUID().toString());
    }

    if (service.getByKey(b.getId()).isPresent()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
    } else {
      this.service.post_put(b);
      return ResponseEntity.status(HttpStatus.CREATED).body(b);
    }
  }

  /**
   * Update book.
   *
   * @param b - specific book
   * @return ResponseEntity
   */
  @PutMapping(value = "{id}")
  public ResponseEntity<Book> put(@RequestBody Book b) {
    if (!service.getByKey(b.getId()).isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } else {
      this.service.post_put(b);
      return ResponseEntity.status(HttpStatus.OK).body(b);
    }
  }

  /**
   * Delete book by its id.
   *
   * @param id - identifier of a specific book
   * @return ResponseEntity
   */
  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteExperiment(@PathVariable("id") String id) {
    if (!service.getByKey(id).isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } else {
      this.service.delete(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
  }

  /**
   * View a specific book by its id.
   *
   * @param id - identifier of a specific book
   * @return ResponseEntity
   */
  @GetMapping("{id}")
  public ResponseEntity<Book> getByKey(@PathVariable("id") String id) {
    if (!service.getByKey(id).isPresent()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } else {
      return ResponseEntity.status(HttpStatus.OK).body(service.getByKey(id).get());
    }
  }

}