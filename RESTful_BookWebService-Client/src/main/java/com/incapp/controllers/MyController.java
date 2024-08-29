package com.incapp.controllers;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.incapp.beans.Book;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class MyController {
	
	RestTemplate restTemplate = new RestTemplate();
	String URL="http://localhost:8383";
		
	@ModelAttribute
	public void commonValue(ModelMap m) {
		m.addAttribute("appName", "BOOK WEB APP");
	}
	
	@RequestMapping(value = {"/","home"})
	public String homedbdhfjkjg() {
		return "index";
	}
	
	@PostMapping("addBook")
	public String addBook(@ModelAttribute Book b, @RequestPart("image") MultipartFile image ,ModelMap m){
		if(image.getSize()!=0) {
			String API = "/book_img";
			
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.MULTIPART_FORM_DATA);
			
			LinkedMultiValueMap<String,Object> data = new LinkedMultiValueMap<>(); 
			data.add("image",convert(image));
			data.add("book",b);
			
			HttpEntity<LinkedMultiValueMap<String,Object>> requestEntity = new HttpEntity(data,header);
			
			ResponseEntity<String> result;
			result=restTemplate.postForEntity(URL+API,requestEntity,String.class);
			
//			if(result.getStatusCode() == HttpStatus.OK) {
//				m.addAttribute("result",b.getName()+" Added Succesfully");
//			}else {
//				m.addAttribute("result",b.getName()+" AlreadyExist");
//			}
			m.addAttribute("result",b.getName()+" "+result.getBody());
		}else {
			String API = "/book";
			
			String result = restTemplate.postForObject(URL+API,b,String.class);
			m.addAttribute("result",b.getName()+" "+result);
		}
		return "index";
	}
		
	@RequestMapping("viewBooks")
	public String viewBooks(ModelMap m) {
		String API = "/books";
		List<Book> b=restTemplate.getForObject(URL+API,List.class);
		m.addAttribute("books",b);
		return "ViewBooks";
	}
	
	@GetMapping("searchBook")
	public String searchBook(String name,ModelMap m) {
		String API = "/books/"+name;
		List<Book> b = restTemplate.getForObject(URL+API,List.class);
		m.addAttribute("books",b);
		return "ViewBooks";
	}
	
    @PostMapping("deleteBook")
	public String deleteBook(String name,ModelMap m) {
    	String API = "/book/"+name;
		HttpEntity<String> requestEntity = new HttpEntity<String>(""); 
		ResponseEntity<Boolean> r = restTemplate.exchange(URL+API,HttpMethod.DELETE,requestEntity,Boolean.class);
		
		if(r.getBody()) {
			m.addAttribute("result", "Book Deleted Successfully!");
		}else {
			m.addAttribute("result", "Book Does Not Exist!");
		}
		
		API="/books";
		List<Book> b=restTemplate.getForObject(URL+API,List.class);
		m.addAttribute("books",b);
		return "ViewBooks";
		
	}
	
	@GetMapping("updateBook")
	public String updateBook(String name,ModelMap m) {
		String API = "/book/"+name;
		Book b = restTemplate.getForObject(URL+API,Book.class);
		m.addAttribute("book",b);
		return "UpdateBook";
	}
	
	@PostMapping("doUpdateBook")
	public String doUpdateBook(@ModelAttribute Book b, @RequestPart("image") MultipartFile image, @RequestParam("oldName") String oldName, ModelMap m) {
		ResponseEntity<String> result;
		
		if(image.getSize()!=0) {
			String API = "/book_img";
			
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.MULTIPART_FORM_DATA);
			
			LinkedMultiValueMap<String,Object> data = new LinkedMultiValueMap<>();
			data.add("book",b);
			data.add("image",convert(image));
			data.add("oldName",oldName);
		
			HttpEntity<LinkedMultiValueMap<String,Object>> requestEntity = new HttpEntity(data,header);
						
			result=restTemplate.exchange(URL+API,HttpMethod.PUT,requestEntity,String.class);
			
//			if(result.getStatusCode() == HttpStatus.OK) {
//				m.addAttribute("result",b.getName()+" Added Succesfully");
//			}else {
//				m.addAttribute("result",b.getName()+" AlreadyExist");
//			}
			
			m.addAttribute("result",b.getName()+" "+result.getBody());			
		}else {
			String API = "/book";			
			
			HttpHeaders header = new HttpHeaders();
			header.setContentType(MediaType.MULTIPART_FORM_DATA);
			
			LinkedMultiValueMap<String,Object> data = new LinkedMultiValueMap<>();
			data.add("book",b);
			data.add("oldName",oldName);
		
			HttpEntity<LinkedMultiValueMap<String,Object>> requestEntity = new HttpEntity(data,header);
						
			result=restTemplate.exchange(URL+API,HttpMethod.PUT,requestEntity,String.class);
			
			m.addAttribute("result",b.getName()+" "+result.getBody());
		}
		
		if(result.getBody().contains("Already Exist!")) {
			String API = "/book/"+oldName;
		    b=restTemplate.getForObject(URL+API,Book.class);
			m.addAttribute("book",b);
		}
		return "UpdateBook";
	}

	@GetMapping("getBookImage")
	public void getBookImage(String name,HttpServletResponse response) throws IOException {
		String API = "/bookImage/"+name;
		byte[] image=restTemplate.getForObject(URL+API,byte[].class);
		if(image==null) {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("book.jfif");
			image=is.readAllBytes();
		}
		response.getOutputStream().write(image);
	}
	
	//convert MultipartFile to FileSystemResource
			public static FileSystemResource convert(MultipartFile file) {
				File convFile=new File(file.getOriginalFilename());
				try {
					convFile.createNewFile();
					FileOutputStream fos=new FileOutputStream(convFile);
					fos.write(file.getBytes());
					fos.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
				return new FileSystemResource(convFile);
			}
			//end
	
}
