package it.serwa.sandbox.micrometer.application.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BookFacadeConfiguration {

    @Bean
    BookFacade bookFacade() {
        InMemoryBookRepository inMemoryBookRepository = new InMemoryBookRepository();
        return new BookFacade(inMemoryBookRepository);
    }
}
