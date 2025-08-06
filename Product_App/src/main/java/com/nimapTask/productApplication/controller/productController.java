package com.nimapTask.productApplication.controller;


import java.util.List;
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
import com.nimapTask.productApplication.entity.Product;
import com.nimapTask.productApplication.repository.productRepository;

@RestController
@RequestMapping("/api")

public class productController {
	@Autowired
	productRepository pr;
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>> getAllProducts(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size) {
		Page<Product> prodList=pr.findAll(PageRequest.of(page, size));
        return new ResponseEntity<Page<Product>>(prodList,HttpStatus.OK);
    }
	
	@PostMapping("/products/add")
	public ResponseEntity<String> addProduct(@RequestBody Product p) {
		pr.save(p);
		return new ResponseEntity<String>("Product Added Successfully",HttpStatus.CREATED);
	}
	
	@GetMapping("/products/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable int id){
		Optional<Product> product=pr.findById(id);
		if(product.isPresent()) {
			return new ResponseEntity<>(product,HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Product Not Found !!",HttpStatus. NOT_FOUND);
		}
	}
	
	@PutMapping("/products/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product updatedPro) {
		Optional<Product> optionalPro=pr.findById(id);
		if(optionalPro.isPresent()) {
			Product p=optionalPro.get();
			p.setName(updatedPro.getName());
			p.setPrice(updatedPro.getPrice());
			p.setCategory(updatedPro.getCategory());
			pr.save(p);
			return new ResponseEntity<>(p,HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Product Not Found !",HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id) {
		Optional<Product> optionalPro=pr.findById(id);
		if(optionalPro.isPresent()) {
			pr.delete(optionalPro.get());
			return new ResponseEntity<>("Product Deleted SuccessFully",HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Product Not Found!",HttpStatus.NOT_FOUND);
		}
	}
}

