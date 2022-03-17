package com.example.restservice.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.restservice.dto.Book;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Book SpringBootTest.
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@RunWith(SpringRunner.class)
public class BookControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  private static String serverPort = "8080";
  private static final String BASE_URL = "http://localhost:" + serverPort;
  private static final String ABSOLUTE_URI = BASE_URL + "/books";


  @Test
  @Order(1)
  public void test01_post() {
    Book harryPotter = new Book("harryP", 15, "Harry Potter");

    ResponseEntity<Book> response = 
        this.testRestTemplate.postForEntity(ABSOLUTE_URI, harryPotter, Book.class);

    assertNotNull(response, "Response must not be null");
    assertEquals(response.getStatusCode(), 
        HttpStatus.CREATED, "HTTP status code must be 201 CREATED");

    assertNotNull(response.getBody(), "Response body must not be null");
    assertTrue(response.getBody() instanceof Book, "Response body must be a Book Object");
    assertEquals(response.getBody().getId(), "harryP", "Book IDs must be equals");
    assertEquals(response.getBody().getPrice(), 15, "Book price must be equals");
    assertEquals(response.getBody().getTitle(), "Harry Potter", "Book title must be equals");

    Book theWitcher = new Book("theWitcher", 20, "The witcher");

    response = this.testRestTemplate.postForEntity(ABSOLUTE_URI, theWitcher, Book.class);

    assertNotNull(response, "Response must not be null");
    assertEquals(response.getStatusCode(), 
        HttpStatus.CREATED, "HTTP status code must be 201 CREATED");

    assertNotNull(response.getBody(), "Response body must not be null");
    assertTrue(response.getBody() instanceof Book, "Response body must be a Book Object");
    assertEquals(response.getBody().getId(), "theWitcher", "Book IDs must be equals");
    assertEquals(response.getBody().getPrice(), 20, "Book price must be equals");
    assertEquals(response.getBody().getTitle(), "The witcher", "Book title must be equals");
  }

  @Test
  @Order(2)
  public void test02_put() {
    Book harryPotter = new Book("harryP", 20, "Harry Potter 3");

    HttpEntity<Book> requestEntity = new HttpEntity<>(harryPotter, new HttpHeaders());
    ResponseEntity<Book> response = new RestTemplate()
        .exchange(ABSOLUTE_URI + "/harryP", HttpMethod.PUT, requestEntity, Book.class);

    assertNotNull(response, "Response must not be null");
    assertEquals(response.getStatusCode(), HttpStatus.OK, "HTTP status code must be 200 OK");

    assertNotNull(response.getBody(), "Response body must not be null");
    assertTrue(response.getBody() instanceof Book, "Response body must be an API object");
    assertEquals(response.getBody().getId(), "harryP", "Book ids must be equals");
    assertNotEquals(response.getBody().getPrice(), 15, "Book price must not be 15 anymore");
    assertEquals(response.getBody().getPrice(), 20, "Book price must be 20 now");
    assertEquals(response.getBody().getTitle(), 
        "Harry Potter 3", "Book name must be Harry Potter 3");
  }

  @Test
  @Order(3)
  public void test03_get() {
    ResponseEntity<Book[]> response = this.testRestTemplate
        .getForEntity(ABSOLUTE_URI, Book[].class);

    assertNotNull(response, "Response must not be null");
    assertEquals(response.getStatusCode(), HttpStatus.OK, "HTTP status code must be 200 OK");

    assertNotNull(response.getBody(), "Response body must not be null");
    assertTrue(response.getBody().length >= 1, "Response body must have at least 1 object");
    for (Book book : response.getBody()) {
      assertTrue(book instanceof Book, "Response body must be a lis of Book objects");
      if (book.getId().equals("harryP")) {
        assertEquals(book.getPrice(), 20, "Book price must be 20 now");
        assertEquals(book.getTitle(), "Harry Potter 3", "Book name must be Harry Potter 3");
      } else if (book.getId().equals("theWitcher")) {
        assertEquals(book.getPrice(), 20, "Book price must be equals");
        assertEquals(book.getTitle(), "The witcher", "Book title must be equals");
      }
    }
  }

  @Test
  @Order(4)
  public void test04_getByKey() {
    ResponseEntity<Book> response = this.testRestTemplate
        .getForEntity(ABSOLUTE_URI + "/theWitcher", Book.class);

    assertNotNull(response, "Response entity must be not null");
    assertEquals(response.getStatusCode(), HttpStatus.OK, "Status code must be 200 OK");

    assertNotNull(response.getBody(), "Response body must not be null");
    assertTrue(response.getBody() instanceof Book, "Response body must be an API object");
    assertEquals(response.getBody().getId(), "theWitcher", "Book IDs must be equals");
    assertEquals(response.getBody().getPrice(), 20, "Book price must be equals");
    assertEquals(response.getBody().getTitle(), "The witcher", "Book title must be equals");
  }

  @Test
  @Order(5)
  public void test05_delete() {
    ResponseEntity<Void> response = new RestTemplate()
        .exchange(ABSOLUTE_URI + "/harryP", HttpMethod.DELETE, null, Void.class);

    assertNotNull(response, "Response must not be null");
    assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT, 
        "HTTP status code must be 204 NO CONTENT");

    assertNull(response.getBody(), "Response body must be null");

    response = new RestTemplate().exchange(ABSOLUTE_URI + "/theWitcher", 
        HttpMethod.DELETE, null, Void.class);

    assertNotNull(response, "Response must not be null");
    assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT, 
        "HTTP status code must be 204 NO CONTENT");

    assertNull(response.getBody(), "Response body must be null");
  }

}
