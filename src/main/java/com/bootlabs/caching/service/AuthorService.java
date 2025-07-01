package com.bootlabs.caching.service;

import com.bootlabs.caching.dto.AuthorDTO;
import com.bootlabs.caching.entities.Author;

import java.util.List;

/**
 * Service Interface for managing {@link Author}.
*  @author @bootteam
 */
public interface AuthorService  {

    AuthorDTO create(AuthorDTO dto);

    AuthorDTO update(AuthorDTO entity);

    AuthorDTO getOne(Long id) ;

    List<AuthorDTO> getAll();

    /**
     * Delete record by id
     *  
     */
    void delete(Long id);

}
