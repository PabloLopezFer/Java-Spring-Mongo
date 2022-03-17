package com.example.restservice.repository;

import com.example.restservice.dto.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Book repository.
 */
@Repository
public interface Ibookrepository extends MongoRepository<Book, String> {

}