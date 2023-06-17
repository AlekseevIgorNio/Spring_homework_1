package org.example.web.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.example.app.services.books.BookService;
import org.example.app.services.books.impl.BookServiceImpl;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "bookIdToRemove") Integer bookIdToRemove, Model model) {
        if (bookService.removeBookById(bookIdToRemove)) {
            return "redirect:/books/shelf";
        } else {
            model.addAttribute("invalidId", "Error, nonexistent book id entered, enter another id");
            return books(model);
        }
    }

    @PostMapping("/removeByRegex")
    public String removeByRegex(@RequestParam(value = "queryRegex") String queryRegexRemove, Model model) {
        if (bookService.removeByRegex(queryRegexRemove)) {
            return "redirect:/books/shelf";
        } else {
            model.addAttribute("invalidId", "Error, your request does not match any books, enter another request");
            return books(model);
        }
    }
}