package com.company.controller;

import com.company.model.Customer;
import com.company.service.interfaces.CustomerService;
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
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public HttpEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAll();
        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/customers/{id}")
    public HttpEntity<Customer> getCustomerById(@PathVariable("id") String id) {
        Customer customer = customerService.getById(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/customers/save")
    public HttpEntity<?> saveCustomer(@RequestBody Customer customer) {
        if (customerService.isExists(customer)) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            Customer savedCustomer = customerService.save(customer);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/customers/{id}")
                    .buildAndExpand(savedCustomer.getId()).toUri();

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(location);

            return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
        }
    }

    @PutMapping(value = "/customers/update/{id}")
    public HttpEntity<?> updateCustomer(@PathVariable("id") String id, @RequestBody Customer customer) {
        Customer customerToDb = customerService.getById(id);
        if (customerToDb == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            customerToDb.setFirstName(customer.getFirstName());
            customerToDb.setLastName(customer.getLastName());
            customerToDb.setBirthDate(customer.getBirthDate());
            customerToDb.setEmail(customer.getEmail());
            customerToDb.setPass(customer.getPass());
            customerToDb.setOrders(customer.getOrders());

            customerService.update(customerToDb);
            return new ResponseEntity<>(customerToDb, HttpStatus.OK);
        }
    }

    @DeleteMapping(value = "/customers/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") String id) {
        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/customers/delete-all/")
    public ResponseEntity<?> deleteAll() {
        customerService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
