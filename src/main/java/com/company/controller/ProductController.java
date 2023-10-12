package com.company.controller;

import com.company.model.Product;
import com.company.service.interfaces.ProductService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public HttpEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/products/{id}")
    public HttpEntity<Product> getProductById(@PathVariable("id") String id) {
        Product product = productService.getById(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/products/save")
    public HttpEntity<?> saveProduct(@RequestBody Product product) {
        if (productService.isExists(product)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            Product savedProduct = productService.save(product);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/products/{id}")
                    .buildAndExpand(savedProduct.getId()).toUri();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(location);

            return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
        }
    }

    @PutMapping(value = "/products/update/{id}")
    public HttpEntity<?> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
        Product productToDb = productService.getById(id);
        if (productToDb == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            productToDb.setCategory(product.getCategory());
            productToDb.setPrice(product.getPrice());

            productService.update(productToDb);
            return new ResponseEntity<>(productToDb, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/products/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") String id) {
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/products/delete-all/")
    public ResponseEntity<?> deleteAll() {
        productService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
