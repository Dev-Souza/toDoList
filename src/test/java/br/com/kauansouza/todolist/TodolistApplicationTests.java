package br.com.kauansouza.todolist;

import br.com.kauansouza.todolist.entity.ToDo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TodolistApplicationTests {
	@Autowired
	public WebTestClient webTestClient;

	@Test
	void testCreateToDoSuccess() {
		var toDo = new ToDo("ToDo 3", "ToDo 3", false, 1 );

		webTestClient
				.post()
				.uri("/toDos")
				.bodyValue(toDo)
				.exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(1)
				.jsonPath("$[0].nome").isEqualTo(toDo.getNome())
				.jsonPath("$[0].descricao").isEqualTo(toDo.getDescricao())
				.jsonPath("$[0].realizado").isEqualTo(toDo.isRealizado())
				.jsonPath("$[0].prioridade").isEqualTo(toDo.getPrioridade());
	}
	@Test
	void testCreateToDoFailure() {
		webTestClient
				.post()
				.uri("/toDos")
				.bodyValue(
						new ToDo("", "", false, 0)
				).exchange()
				.expectStatus().isBadRequest();
	}

}
