package org.example.web.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.example.app.services.books.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdRemove;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/books")
public class BookShelfController {

    private final Logger logger = Logger.getLogger(BookShelfController.class);
    private final BookService bookService;

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        getBooksForView(model);
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            getBooksForView(model, false, true);
            logger.warn("Book has errors in fields");
            return "book_shelf";
        }
        bookService.saveBook(book);
        logger.info("current repository size: " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookIdRemove bookIdRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            getBooksForView(model, true, false);
            logger.warn("Id book for delete is empty");
            return "book_shelf";
        }
        if (bookService.removeBookById(bookIdRemove.getId())) {
            return "redirect:/books/shelf";
        } else {
            model.addAttribute("notBookWithId", true);
            getBooksForView(model);
            logger.warn("Book with this id not exists");
            return "book_shelf";
        }
    }

    @PostMapping("/removeByRegex")
    public String removeByRegex(@RequestParam(value = "queryRegex") String queryRegexRemove, Model model) {
        if (bookService.removeByRegex(queryRegexRemove)) {
            return "redirect:/books/shelf";
        } else {
            model.addAttribute("invalidId", true);
            getBooksForView(model);
            logger.warn("Regex for delete book is un correct, or empty");
            return "book_shelf";
        }
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        if (file.isEmpty()) {
            model.addAttribute("invalidFile", true);
            getBooksForView(model);
            logger.warn("File not exists, or is bad");
            return "book_shelf";
        }
        String fileName = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "my_data");

        dir.mkdirs();

        File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(serverFile));
        bos.write(bytes);
        bos.flush();
        bos.close();

        return "redirect:/books/shelf";
    }

    private void getBooksForView(Model model) {
        getBooksForView(model, true, true);
    }

    private void getBooksForView(Model model, boolean addBook, boolean addBookIdRemove) {
        if (addBook) {
            model.addAttribute("book", new Book());
        }
        if (addBookIdRemove) {
            model.addAttribute("bookIdRemove", new BookIdRemove());
        }
        model.addAttribute("bookList", bookService.getAllBooks());
    }
}