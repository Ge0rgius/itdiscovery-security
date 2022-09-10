package it.discovery.security.web;

import it.discovery.security.dto.BookDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    public List<BookDTO> findAll() {
        return List.of(new BookDTO(1, "Java Security", "Unknown", 500));
    }
}
