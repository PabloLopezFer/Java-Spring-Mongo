package com.example.restservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.restservice.dto.Book;

@Repository
public interface IBookRepository extends MongoRepository<Book, String> {

}