package com.bootlabs.caching.service.impl;


import com.bootlabs.caching.dto.AuthorDTO;
import com.bootlabs.caching.entities.Author;
import com.bootlabs.caching.exception.DataNotFoundException;
import com.bootlabs.caching.mapper.AuthorMapper;
import com.bootlabs.caching.repositories.AuthorRepository;
import com.bootlabs.caching.service.AuthorService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing {@link Author}.
 *
 * @author @bootteam
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    public static final String AUTHOR_CACHE = "authors";
    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    public AuthorServiceImpl(AuthorRepository repo, AuthorMapper mapper) {
        this.repository = repo;
        this.mapper = mapper;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @CachePut(value = AUTHOR_CACHE, key = "#result.id")
    public AuthorDTO create(AuthorDTO authorDTO) {
        var author = repository.save(mapper.toEntity(authorDTO));
        return mapper.toDto(author);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CachePut(value = AUTHOR_CACHE, key = "#result.id")
    public AuthorDTO update(AuthorDTO author) {

        Author existingAuthor = repository.findById(author.id()).orElseThrow(() -> new DataNotFoundException("Author id not found"));

        existingAuthor.setFirstname(author.firstname());
        existingAuthor.setLastname(author.lastname());

        var author1 = repository.save(existingAuthor);
        return mapper.toDto(author1);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(value = AUTHOR_CACHE, key = "#id")
    public AuthorDTO getOne(Long id) {
        var author =  repository.findById(id).orElseThrow(() -> new DataNotFoundException("Author id not found"));
        return mapper.toDto(author);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuthorDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict(value = AUTHOR_CACHE, key = "#id")
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
