package it.serwa.sandbox.micrometer.application.domain

import it.serwa.sandbox.micrometer.application.dto.BookDTO
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class BookFacadeUT extends Specification {

    @Shared
    @Subject
    BookFacade bookFacade

    void setupSpec() {
        bookFacade = new BookFacadeConfiguration().bookFacade()
    }

    def "Should add book without an error"() {
        given:
        BookDTO bookDTO = getBookDTO()

        when:
        def book = bookFacade.addBook(bookDTO)

        then:
        noExceptionThrown()
        book != null
        book.bookISBN != null && !book.bookISBN.empty
        book.author != null && !book.author.empty
        book.bookName != null && !book.bookName.empty
    }

    def "Should find book by ISBN without an error"() {
        given:
        String isbn = "1"

        when:
        def book = bookFacade.findByISBN(isbn)

        then:
        noExceptionThrown()
        book != null
        book.bookISBN == isbn
        book.author != null && !book.author.empty
        book.bookName != null && !book.bookName.empty

    }

    def "Should borrow book without an error"() {
        given:
        String isbn = '1'

        when:
        def book = bookFacade.borrow(isbn)

        then:
        noExceptionThrown()
        book != null
        book.bookISBN == isbn
        book.author != null && !book.author.empty
        book.bookName != null && !book.bookName.empty
    }

    def "Should return book after borrowed without an error"() {
        given:
        String bookIsbnToReturn = bookFacade.addBook(getBookDTO()).bookISBN
        bookFacade.borrow(bookIsbnToReturn)

        when:
        def book = bookFacade.returnBook(bookIsbnToReturn)

        then:
        noExceptionThrown()
        book != null
        book.bookISBN == bookIsbnToReturn
    }

    def "Should get whole library collection"() {
        expect:
        !bookFacade.findAll().empty
    }


    BookDTO getBookDTO() {
        new BookDTO(UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString())
    }
}
