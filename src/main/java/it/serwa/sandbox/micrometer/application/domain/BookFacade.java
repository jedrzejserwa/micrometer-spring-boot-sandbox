package it.serwa.sandbox.micrometer.application.domain;

import io.micrometer.core.annotation.Timed;
import it.serwa.sandbox.micrometer.application.dto.BookDTO;
import it.serwa.sandbox.micrometer.application.dto.exception.BookNotFoundException;
import it.serwa.sandbox.micrometer.application.dto.exception.SavingBookException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static it.serwa.sandbox.micrometer.application.domain.Book.*;

public class BookFacade {
    private final BookRepository bookRepository;

    BookFacade(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Timed(value = "add-book-flow")
    public BookDTO addBook(BookDTO bookDTO) {
        validate(bookDTO);
        Book book = fromDTO(bookDTO);
        return saveBook(book);
    }

    public BookDTO findByISBN(String isbn) {
        requireNonNullNonEmptyISBN(isbn);
        return findBookByISBN(isbn).toDTO();
    }

    private Book findBookByISBN(String isbn) {
        return Optional.ofNullable(bookRepository.findByBookISBN(isbn))
                .orElseThrow(() -> new BookNotFoundException(isbn));
    }

    public BookDTO borrow(String isbn) {
        requireNonNullNonEmptyISBN(isbn);
        Book toBorrow = findBookByISBN(isbn).borrowBook();
        return saveBook(toBorrow);
    }

    public BookDTO returnBook(String isbn) {
        requireNonNullNonEmptyISBN(isbn);
        Book toReturn = findBookByISBN(isbn).returnBook();
        return saveBook(toReturn);
    }

    private BookDTO saveBook(Book toSave) {
        Book book = Optional.ofNullable(bookRepository.save(toSave))
                .orElseThrow(() -> new SavingBookException(toSave.toDTO().getBookISBN()));
        return book.toDTO();
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(Book::toDTO)
                .collect(Collectors.toList());
    }

    public Long getLibrarySize() {
        return bookRepository.countAll();
    }
}
