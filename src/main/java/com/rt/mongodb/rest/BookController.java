package com.rt.mongodb.rest;

import com.rt.mongodb.domain.Book;
import com.rt.mongodb.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    /**
     * Save a book.
     *
     * @param book
     * @return
     */
    @PostMapping
    public ResponseEntity<Book> save(@RequestBody Book book) {
        bookRepository.save(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping
    public ResponseEntity<Book> update(@RequestBody Book book) {
        if (book.getId() == null) {
            bookRepository.save(book);
        } else {
            if (bookRepository.existsById(book.getId())) {
                bookRepository.save(book);
            } else {
                book.setId(null);
                bookRepository.save(book);
            }
        }

        return ResponseEntity.ok(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBook() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable String id) {
        return Optional.ofNullable( bookRepository.findById(id) )
                .map( book -> ResponseEntity.ok(book.get()))
                .orElseGet( () -> ResponseEntity.notFound().build() );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> save(@PathVariable String id) {
        bookRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
