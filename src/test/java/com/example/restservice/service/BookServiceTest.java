package com.example.restservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.restservice.dto.Book;
import com.example.restservice.mock.MockingBook;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Book SpringBootTest.
 */
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

  @MockBean
  private BookService bookService;

  @BeforeEach
  public void init() {
    MockingBook.mockingBook(bookService);
  }

  @Test
  @Order(1)
  public void test01_get() {
    List<Book> response = this.bookService.getBook();

    assertEquals(response.size(), 2, "Response size must be 2");

    assertEquals(response.get(0).getId(), "id-1", "Book id must be equals");
    assertEquals(response.get(0).getPrice(), 30, "Book price must be equals");
    assertEquals(response.get(0).getTitle(), "The witcher", "Book title must be equals");
    assertEquals(response.get(0).getAuthor(), "Andrzej Sapkowski", "Book author must be equals");
    assertEquals(response.get(0).getCategory(), "Misterio", "Book cathegory must be equals");
    assertEquals(response.get(0).getLanguage(), "Espanol", "Book language must be equals");
    assertEquals(response.get(0).getEditorial(), "-", "Book ids editorial be equals");

    assertEquals(response.get(1).getId(), "id-2", "Book id must be equals");
    assertEquals(response.get(1).getPrice(), 30, "Book price must be equals");
    assertEquals(response.get(1).getTitle(), "Harry Potter", "Book title must be equals");
    assertEquals(response.get(1).getAuthor(), "J. K. Rowling", "Book author must be equals");
    assertEquals(response.get(1).getCategory(), "Misterio", "Book cathegory must be equals");
    assertEquals(response.get(1).getLanguage(), "Espanol", "Book language must be equals");
    assertEquals(response.get(1).getEditorial(), "-", "Book ids editorial be equals");

    Mockito.verify(bookService).getBook();
  }

  @Test
  @Order(1)
  public void test02_post() {
    Book b = new Book();
    Book response = this.bookService.post_put(b);

    assertEquals(response.getId(), "id-3", "Book id must be equals");
    assertEquals(response.getPrice(), 100, "Book price must be equals");
    assertEquals(response.getTitle(), "The witcher 2", "Book title must be equals");
    assertEquals(response.getAuthor(), "Andrzej Sapkowski", "Book author must be equals");
    assertEquals(response.getCategory(), "Misterio", "Book cathegory must be equals");
    assertEquals(response.getLanguage(), "Espanol", "Book language must be equals");
    assertEquals(response.getEditorial(), "-", "Book ids editorial be equals");

    Mockito.verify(bookService).post_put(b);
  }

  @Test
  @Order(1)
  public void test03_delete() {
    this.bookService.delete(new String());

    Mockito.verify(bookService).delete(new String());
  }

  @Test
  @Order(1)
  public void test04_getByKey() {
    Optional<Book> response = this.bookService.getByKey(new String());

    assertTrue(response.isPresent() && !response.isEmpty(), "Response entity must be present");
    assertEquals(response.get().getId(), "id-10", "Book id must be equals");
    assertEquals(response.get().getPrice(), 100, "Book price must be equals");
    assertEquals(response.get().getTitle(), "The witcher 2", "Book title must be equals");
    assertEquals(response.get().getAuthor(), "Andrzej Sapkowski", "Book author must be equals");
    assertEquals(response.get().getCategory(), "Misterio", "Book cathegory must be equals");
    assertEquals(response.get().getLanguage(), "Espanol", "Book language must be equals");
    assertEquals(response.get().getEditorial(), "-", "Book ids editorial be equals");

    Mockito.verify(bookService).getByKey(new String());
  }

}
