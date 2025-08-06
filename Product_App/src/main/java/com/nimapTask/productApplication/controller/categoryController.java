package com.nimapTask.productApplication.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.nimapTask.productApplication.entity.Category;
import com.nimapTask.productApplication.repository.categoryRepository;

@RestController
@RequestMapping("/api")
public class categoryController {
	@Autowired
	categoryRepository cr;
	
	@GetMapping("/categories")
	public ResponseEntity<Page<Category>> getAllCategories(@RequestParam(defaultValue = "0") int page, @ RequestParam(defaultValue = "5") int size) {
        Page<Category> categories = cr.findAll(PageRequest.of(page, size));
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
	
	@GetMapping("/category/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable int id){
		Optional<Category> category=cr.findById(id);
		if(category.isPresent()) {
			return new ResponseEntity<>(category,HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Category Not Found !!",HttpStatus. NOT_FOUND);
		}
	}
	
	@PostMapping("/category/add")
	public ResponseEntity<String> addCategory(@RequestBody Category c) {
		cr.save(c);
		 return new ResponseEntity<>("Category Added Successfully",HttpStatus.CREATED);
	}
	
	@PutMapping("/category/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable int id, @RequestBody Category updatedCat) {
		Optional<Category> optionalCat=cr.findById(id);
		if(optionalCat.isPresent()) {
			Category c=optionalCat.get();
			c.setName(updatedCat.getName());
			cr.save(c);
			return new ResponseEntity<>(c,HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Category Not Found !",HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/category/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable int id) {
		Optional<Category> optionalCat=cr.findById(id);
		if(optionalCat.isPresent()) {
			cr.delete(optionalCat.get());
			return new ResponseEntity<>("Category Deleted SuccessFully",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Category Not Found!",HttpStatus.NOT_FOUND);
		}
	}
	
}

