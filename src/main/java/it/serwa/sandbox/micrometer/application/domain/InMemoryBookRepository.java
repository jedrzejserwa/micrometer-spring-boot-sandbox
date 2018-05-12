package it.serwa.sandbox.micrometer.application.domain;

import it.serwa.sandbox.micrometer.application.dto.exception.BookNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static it.serwa.sandbox.micrometer.application.domain.Book.requireNonNullNonEmptyISBN;
import static java.util.Objects.requireNonNull;

@Slf4j
class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> inMemoryData = new ConcurrentHashMap<>();

    public InMemoryBookRepository() {
        initializeInMemoryData();
    }

    @Override
    public Book findByBookISBN(String isbn) throws BookNotFoundException {
        requireNonNullNonEmptyISBN(isbn);
        return inMemoryData.get(isbn);
    }

    @Override
    public Book save(Book book) {
        requireNonNull(book);
        inMemoryData.put(book.toDTO().getBookISBN(), book);
        return book;
    }

    @Override
    public Long countAll() {
        return (long) inMemoryData.size();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(inMemoryData.values());
    }

    private void initializeInMemoryData() {
        inMemoryData.put("1", new Book("1", "Harper Lee", "To Kill a Mockingbird", null));
        inMemoryData.put("2", new Book("2", "Harper Lee", "To Kill a Mockingbird 2", null));
        inMemoryData.put("3", new Book("3", "Harper Lee", "To Kill a Mockingbird 3", null));
        inMemoryData.put("4", new Book("4", "Anne Frank  ", "The Diary of a Young Girl", null));
    }
}
