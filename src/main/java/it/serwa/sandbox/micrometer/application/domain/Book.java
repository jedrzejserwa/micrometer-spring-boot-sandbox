package it.serwa.sandbox.micrometer.application.domain;

import it.serwa.sandbox.micrometer.application.dto.BookDTO;
import it.serwa.sandbox.micrometer.application.dto.exception.BookAlreadyBorrowedException;
import it.serwa.sandbox.micrometer.application.dto.exception.BookNotRentedException;
import it.serwa.sandbox.micrometer.application.dto.exception.BookValidationException;
import it.serwa.sandbox.micrometer.application.dto.exception.ISBNValidationException;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@Entity
@EqualsAndHashCode
class Book implements Serializable {
    private static final long serialVersionUID = -4433796124836530046L;

    @Id
    private final String bookISBN;
    private final String author;
    private final String bookName;
    private final LocalDateTime bookRentalDate;

    static Book fromDTO(BookDTO bookDTO) {
        return Book.builder()
                .author(bookDTO.getAuthor())
                .bookName(bookDTO.getBookName())
                .bookISBN(bookDTO.getBookISBN())
                .bookRentalDate(null)
                .build();
    }

    static void validate(BookDTO bookDTO) {
        if (bookDTO.getAuthor() != null && !bookDTO.getAuthor().isEmpty() &&
                bookDTO.getBookName() != null && !bookDTO.getBookName().isEmpty() &&
                bookDTO.getBookISBN() != null && !bookDTO.getBookISBN().isEmpty()) {
            return;
        }
        throw new BookValidationException();
    }

    static String requireNonNullNonEmptyISBN(String isbn) {
        if (isbn == null || isbn.isEmpty())
            throw new ISBNValidationException();

        return isbn;
    }

    BookDTO toDTO() {
        return new BookDTO(this.author, this.bookName, this.bookISBN);
    }

    Book borrowBook() {
        if (Objects.isNull(bookRentalDate)) {
            return new Book(bookISBN, author, bookName, LocalDateTime.now());
        }
        throw new BookAlreadyBorrowedException(bookISBN);
    }

    Book returnBook() {
        if (!Objects.isNull(bookRentalDate)) {
            return new Book(bookISBN, author, bookName, null);
        }
        throw new BookNotRentedException(bookISBN);
    }
}
