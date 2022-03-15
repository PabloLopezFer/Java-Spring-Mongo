package com.example.restservice.webTestClient;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import com.example.restservice.dto.Book;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebClientDemoApplicationTests {
	
	@Autowired
	private WebTestClient webTestClient;
	
	@Value("${spring.data.mongodb.uri}")
    private static String uri;

    @Value("${spring.data.mongodb.database}")
    private static String database;
    
    @Container
  	public static MongoDBContainer container = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @DynamicPropertySource
    public static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
    }
    
    @BeforeAll
    public static void init() {
        container.start();
    }
	
	@Test
	@Order(1)
	public void test01_post() {
		Book book = new Book("harryP", 20, "Harry Potter");
		
		webTestClient.post().uri("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(book), Book.class)
				.exchange()
				.expectStatus().isCreated()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.id").isEqualTo("harryP");
	}
	
	@Test
	@Order(2)
	public void test02_get() {
		webTestClient.get().uri("/books")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBodyList(Book.class);
	}
	
	@Test
	@Order(3)
	public void test03_getByKey() {
		webTestClient.get()
				.uri("/books/{id}", "harryP")
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.consumeWith(response ->
						Assertions.assertThat(response.getResponseBody()).isNotNull());
	}
	
	@Test
	@Order(4)
	public void test04_put() {
		Book newBook = new Book("harryP", 50, "Harry potter 4");
		webTestClient.put()
				.uri("/books/{id}", "harryP")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(newBook), Book.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.id").isEqualTo("harryP");
	}
	
	
	@Test
	@Order(5)
	public void test05_delete() {
		webTestClient.delete()
				.uri("/books/{id}", "harryP")
				.exchange()
				.expectStatus().isNoContent();
	}
	
}
