package com.bootlabs.caching.mapper;

import com.bootlabs.caching.dto.AuthorDTO;
import com.bootlabs.caching.entities.Author;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Author} and its DTO {@link AuthorDTO}.
 */
@Mapper
public interface AuthorMapper extends EntityMapper<AuthorDTO, Author> {
}
