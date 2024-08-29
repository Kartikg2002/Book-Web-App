package com.incapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Book;
import com.incapp.repo.BookRepo;

import jakarta.servlet.http.Part;

@Service
public class BookService {
	
	@Autowired
	BookRepo repo;
	
	public Book getBook(String name) {
		return repo.getBook(name);
	}
	
	public List<Book> getBooks(String name) {
		return repo.getBooks(name);
	}
	
	public List<Book> getBooks() {
		return repo.getBooks();
	}
	
	public String addBook(Book b) {
		return repo.addBook(b);
	}	
	public String addBook(Book b,MultipartFile image) {
		return repo.addBook(b,image);
	}
	
	public boolean deleteBook(String name) {
		return repo.deleteBook(name);
	}
	
	public String doUpdateBook(Book b,String oldName) {
		return repo.doUpdateBook(b,oldName);
	}
	public String doUpdateBook(Book b,MultipartFile image,String oldName) {
		return repo.doUpdateBook(b,image,oldName);
	}
	
	
	public byte[] getBookImage(String name){
		return repo.getBookImage(name);
	}
}
