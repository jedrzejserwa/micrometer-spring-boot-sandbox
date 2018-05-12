package it.serwa.sandbox.micrometer.application;

import it.serwa.sandbox.micrometer.application.domain.BookFacade;
import it.serwa.sandbox.micrometer.application.dto.BookDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("book")
class BookController {

    private final BookFacade bookFacade;

    public BookController(BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }

    @PostMapping("add")
    @ResponseStatus(HttpStatus.CREATED)
    BookDTO addNewBook(@RequestBody BookDTO bookDTO) {
        return bookFacade.addBook(bookDTO);
    }

    @GetMapping("find")
    @ResponseStatus(HttpStatus.OK)
    BookDTO findBookByIsbn(@RequestParam String isbn) {
        return bookFacade.findByISBN(isbn);
    }

    @PostMapping("borrow")
    @ResponseStatus(HttpStatus.OK)
    BookDTO borrowBook(@RequestBody String isbn) {
        return bookFacade.borrow(isbn);
    }

    @PostMapping("return")
    @ResponseStatus(HttpStatus.OK)
    BookDTO returnBook(@RequestBody String isbn) {
        return bookFacade.returnBook(isbn);
    }
}
