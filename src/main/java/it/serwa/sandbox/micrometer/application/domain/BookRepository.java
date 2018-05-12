package it.serwa.sandbox.micrometer.application.domain;


import org.springframework.data.repository.Repository;

import java.util.List;

interface BookRepository extends Repository<Book, String> {
    Book findByBookISBN(String isbn);

    Book save(Book book);

    Long countAll();

    List<Book> findAll();
}
