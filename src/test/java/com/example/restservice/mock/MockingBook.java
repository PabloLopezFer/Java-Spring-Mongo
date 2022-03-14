package com.example.restservice.mock;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Optional;

import org.mockito.Mockito;

import com.example.restservice.dto.Book;
import com.example.restservice.service.BookService;

public class MockingBook {
	
	public static void mockingBook(BookService bookService) {
		get(bookService);
		post_put(bookService);
		delete(bookService);
		getByKey(bookService); 
	}
	
	public static void get (BookService bookService) {
		Book theWitcher = new Book("id-1",30,"The witcher",
				"Andrzej Sapkowski","Misterio","Espanol","-");
		Book harryPotter = new Book("id-2",30,"Harry Potter",
				"J. K. Rowling","Misterio","Espanol","-");
		
		ArrayList<Book> list = new ArrayList<>(Arrays.asList(theWitcher, harryPotter));
		
		Mockito.when(bookService.getBook()).thenReturn(list);
	}
	
	public static void post_put (BookService bookService) {
		Book theWitcher2 = new Book("id-3",100,"The witcher 2",
				"Andrzej Sapkowski","Misterio","Espanol","-");
		
		Mockito.when(bookService.post_put(Mockito.any(Book.class))).thenReturn(theWitcher2);
	}
	
	public static void delete (BookService bookService) {
		Mockito.doAnswer(invocation ->{
			return invocation;
		}).when(bookService).delete(Mockito.anyString());
	}
	
	public static void getByKey (BookService bookService) {
		Book theWitcher2 = new Book("id-10",100,"The witcher 2",
				"Andrzej Sapkowski","Misterio","Espanol","-");
		
		Mockito.when(bookService.getByKey(Mockito.anyString())).thenReturn(Optional.of(theWitcher2));
	}
	
	

}
