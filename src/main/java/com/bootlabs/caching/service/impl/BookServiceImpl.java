package com.bootlabs.caching.service.impl;

import com.bootlabs.caching.dto.BookDTO;
import com.bootlabs.caching.entities.Book;
import com.bootlabs.caching.exception.DataNotFoundException;
import com.bootlabs.caching.mapper.BookMapper;
import com.bootlabs.caching.repositories.BookRepository;
import com.bootlabs.caching.service.BookService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing {@link Book}.
 *
 * @author @bootteam
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository repository;

    private final BookMapper mapper;
    public static final String BOOK_CACHE = "books";

    public BookServiceImpl(BookRepository repo, BookMapper mapper) {
        this.repository = repo;
        this.mapper = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CachePut(value = BOOK_CACHE, key = "#result.id")
    public BookDTO create(BookDTO bookDTO) {
        var author = repository.save(mapper.toEntity(bookDTO));
        return mapper.toDto(author);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CachePut(value = BOOK_CACHE, key = "#result.id")
    public BookDTO update(BookDTO book) {
        var existingBook = repository.findById(book.id()).orElseThrow(() -> new DataNotFoundException("book id not found"));

        existingBook.setPage(book.page());
        existingBook.setIsbn(book.isbn());
        existingBook.setDescription(book.description());
        existingBook.setPrice(book.price());
        existingBook.setTitle(book.title());

        var book1 = repository.save(existingBook);
        return mapper.toDto(book1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(value = BOOK_CACHE, key = "#id")
    public BookDTO getOne(Long id) {
        var author =  repository.findById(id).orElseThrow(() -> new DataNotFoundException("Book id not found"));
        return mapper.toDto(author);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict(value = BOOK_CACHE, key = "#id")
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
