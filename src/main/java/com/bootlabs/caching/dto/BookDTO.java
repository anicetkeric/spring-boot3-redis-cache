package com.bootlabs.caching.dto;

import java.math.BigDecimal;

public record BookDTO (

     Long id,

     String title,

     String isbn,

     String description,

     Integer page,

     BigDecimal price,

     AuthorDTO author
){}
