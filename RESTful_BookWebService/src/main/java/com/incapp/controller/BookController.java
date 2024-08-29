package com.incapp.controller;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Book;
import com.incapp.repo.BookRepo;
import com.incapp.service.BookService;

import jakarta.servlet.http.Part;

@RestController
public class BookController {
	
	@Autowired
	BookService service;
	
	@RequestMapping("/")
	public String homePage() {
		return "Welcome to Book RESTful web service";
	}
	
	@GetMapping("/book/{name}")
	public Book getBook(@PathVariable("name") String name) {
		return service.getBook(name);
	}
	
	@GetMapping("/books/{name}")
	public List<Book> getBooks(@PathVariable("name") String name) {
		return service.getBooks(name);
	}
	
	//@GetMapping(value = "/books",produces = {"application/xml"})
	@GetMapping("/books")
	public List<Book> getBooks() {
		return service.getBooks();
	}
	
	@PostMapping(value="/book")
	public String addBook(@RequestBody Book b){
		return service.addBook(b);	
	}
	@PostMapping(value="/book_img")
	public String addBook(@RequestPart("book") Book b,@RequestPart("image") MultipartFile image){
		return service.addBook(b,image);	
	}
	
	@DeleteMapping("/book/{name}")
	public boolean deleteBook(@PathVariable("name") String name) {
		return service.deleteBook(name);	
	}
	
	@PutMapping("/book")
    public String doupdateBook(@RequestPart("book") Book b,@RequestPart("oldName") String oldName){
		return service.doUpdateBook(b,oldName);
	}
	@PutMapping("/book_img")
	public String doupdateBook(@RequestPart("book") Book b,@RequestPart("image") MultipartFile image,@RequestPart("oldName") String oldName){
		return service.doUpdateBook(b,image,oldName);
	}
	
	@GetMapping("/bookImage/{name}")
	public byte[] getBookImage(@PathVariable("name") String name){
		return service.getBookImage(name);
	}
}
