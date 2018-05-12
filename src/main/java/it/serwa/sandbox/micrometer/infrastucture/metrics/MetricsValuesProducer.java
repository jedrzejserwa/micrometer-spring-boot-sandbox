package it.serwa.sandbox.micrometer.infrastucture.metrics;

import it.serwa.sandbox.micrometer.application.dto.BookDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
@EnableScheduling
class MetricsValuesProducer {
    private final static String addBookEndpoint = "/book/add";
    private final static String findBookEndpoint = "/book/find";
    private final static String borrowBookEndpoint = "/book/borrow";
    private final static String returnBookEndpoint = "/book/return";

    private final Long validRequestsInvokes;
    private final Long invalidRequestsInvokes;

    private final RestTemplate restTemplate;
    private final ParameterizedTypeReference<BookDTO> bookDTOTypeRef = new ParameterizedTypeReference<BookDTO>() {
    };


    public MetricsValuesProducer(@Value("${mvp.invokes.valid-requests:100}") Long validRequestsInvokes,
                                 @Value("${mvp.invokes.invalid-requests:100}") Long invalidRequestsInvokes,
                                 @Value("${server.port:8080}") Integer serverPort) {
        this.validRequestsInvokes = validRequestsInvokes;
        this.invalidRequestsInvokes = invalidRequestsInvokes;

        this.restTemplate = new RestTemplate();
        this.restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:" + serverPort));
    }

    @Scheduled(cron = "*/20 * * * * *") //every 20 sec
    public void createMetricsValues() {
        doValidRequests(validRequestsInvokes);
        doInvalidRequests(invalidRequestsInvokes);
    }

    private void doValidRequests(Long counter) {
        for (int i = 0; i < counter; i++) {
            try {
                BookDTO bookDTO = new BookDTO(UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString());

                restTemplate.exchange(addBookEndpoint, HttpMethod.POST, new HttpEntity<>(bookDTO), bookDTOTypeRef, Collections.emptyList());
                restTemplate.exchange(borrowBookEndpoint, HttpMethod.POST, new HttpEntity<>(String.valueOf(bookDTO.getBookISBN())), bookDTOTypeRef, Collections.emptyList());
                restTemplate.exchange(returnBookEndpoint, HttpMethod.POST, new HttpEntity<>(String.valueOf(bookDTO.getBookISBN())), bookDTOTypeRef, Collections.emptyList());

                UriComponentsBuilder builder = UriComponentsBuilder
                        .fromPath(findBookEndpoint)
                        .queryParam("isbn", String.valueOf(bookDTO.getBookISBN()));
                restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<BookDTO>(new HttpHeaders()), bookDTOTypeRef);

                log.info("Valid requests made");
            } catch (Exception e) {
                log.error("Exception while creating valid request!");
            }
        }
    }

    private void doInvalidRequests(Long invokes) {
        addBooks(invokes);
        findBooks(invokes);
        borrowBooks(invokes);
        returnBooks(invokes);
    }

    private void returnBooks(Long counter) {
        for (int i = 10; i < counter + 10; i++) {
            try {
                restTemplate.exchange(returnBookEndpoint, HttpMethod.POST, new HttpEntity<>(String.valueOf(i)), bookDTOTypeRef, Collections.emptyList());
            } catch (Exception e) {
                log.warn("Invalid returnBook request made.");
            }
        }
    }

    private void borrowBooks(Long counter) {
        for (int i = 10; i < counter + 10; i++) {
            try {
                restTemplate.exchange(borrowBookEndpoint, HttpMethod.POST, new HttpEntity<>(String.valueOf(i)), bookDTOTypeRef, Collections.emptyList());
            } catch (Exception e) {
                log.warn("Invalid borrowBook request made.");
            }
        }
    }

    private void findBooks(Long counter) {
        for (int i = 10; i < counter + 10; i++) {
            try {
                UriComponentsBuilder builder = UriComponentsBuilder
                        .fromHttpUrl(findBookEndpoint)
                        .queryParam("isbn", String.valueOf(counter));
                restTemplate.exchange(builder.toUriString(), HttpMethod.GET, new HttpEntity<BookDTO>(new HttpHeaders()), BookDTO.class);
            } catch (Exception e) {
                log.warn("Invalid findBook request made.");
            }
        }
    }

    private void addBooks(Long counter) {
        for (int i = 10; i < counter + 10; i++) {
            try {
                BookDTO bookDTO = new BookDTO("", "", UUID.randomUUID().toString());
                restTemplate.exchange(addBookEndpoint, HttpMethod.POST, new HttpEntity<>(bookDTO), bookDTOTypeRef, Collections.emptyList());
            } catch (Exception e) {
                log.warn("Invalid addBook request made.");
            }
        }
    }
}

