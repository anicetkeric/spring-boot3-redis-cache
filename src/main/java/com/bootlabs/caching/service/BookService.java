package com.bootlabs.caching.service;

import com.bootlabs.caching.dto.BookDTO;
import com.bootlabs.caching.entities.Book;

import java.util.List;

/**
 * Service Interface for managing {@link Book}.
*  @author @bootteam
 */
public interface BookService {


    BookDTO create(BookDTO entity);

    BookDTO update(BookDTO entity);

    BookDTO getOne(Long id) ;

    List<BookDTO> getAll();
    /**
     * Delete record by id
     *  
     */
    void delete(Long id);
}
