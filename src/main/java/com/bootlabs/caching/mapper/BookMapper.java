package com.bootlabs.caching.mapper;

import com.bootlabs.caching.dto.BookDTO;
import com.bootlabs.caching.entities.Book;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(uses = AuthorMapper.class)
public interface BookMapper extends EntityMapper<BookDTO, Book> {
}
