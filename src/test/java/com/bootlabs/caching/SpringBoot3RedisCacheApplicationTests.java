package com.bootlabs.caching;

import com.bootlabs.caching.dto.AuthorDTO;
import com.bootlabs.caching.entities.Author;
import com.bootlabs.caching.repositories.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
class SpringBootRedisCacheApplicationTests {

	public static final String AUTHORS_CACHE_KEY = "authors";
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private CacheManager cacheManager;

	private final ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setUp() {
		authorRepository.deleteAll();
	}

	@Test
	void testCreateAuthorAndCacheIt() throws Exception {
		AuthorDTO authorDTO = new AuthorDTO(null, "jhon", "doe");

		MvcResult result = mockMvc.perform(post("/api/authors")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(authorDTO)))
				.andExpect(status().isCreated())
				.andReturn();

		Author author = objectMapper.readValue(result.getResponse().getContentAsString(), Author.class);

		// Step 2: Check Author Exists in DB
		Assertions.assertTrue(authorRepository.findById(author.getId()).isPresent());

		// Step 3: Check Cache
		Cache cache = cacheManager.getCache(AUTHORS_CACHE_KEY);
		assertNotNull(cache);
		assertNotNull(cache.get(author.getId(), AuthorDTO.class));
	}

	@Test
	void testDeleteAuthorAndEvictCache() throws Exception {
		// Step 1: Create and Save Author
		Author author = new Author();
		author.setFirstname("Avri");
		author.setLastname("jose");
		author = authorRepository.save(author);

		// Step 2: Delete Author
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/authors/" + author.getId()))
				.andExpect(status().isNoContent());

		// Step 3: Check that Author is Deleted from DB
		Assertions.assertFalse(authorRepository.findById(author.getId()).isPresent());

		// Step 4: Check Cache Eviction
		Cache cache = cacheManager.getCache(AUTHORS_CACHE_KEY);
		assertNotNull(cache);
		Assertions.assertNull(cache.get(author.getId()));
	}
}