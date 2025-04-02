package com.filipecode.libraryApi.model.dtos;

import com.filipecode.libraryApi.model.entities.Author;

import java.time.LocalDate;

public record AuthorDTO(String name,
                        LocalDate dateOfBirth,
                        String nationality){

    public Author mapping() {
        Author author = new Author();
        author.setName(this.name);
        author.setDateOfBirth(this.dateOfBirth);
        author.setNationality(this.nationality);

        return author;
    }
}
