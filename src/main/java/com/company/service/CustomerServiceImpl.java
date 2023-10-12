package com.company.service;

import com.company.model.Customer;
import com.company.repository.CustomerRepository;
import com.company.service.interfaces.CustomerService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public boolean isExists(Customer entity) {
        return customerRepository.exists(Example.of(entity));
    }

    @Override
    public Customer save(Customer entity) {
        return customerRepository.save(entity);
    }

    @Override
    public void update(Customer entity) {
        customerRepository.save(entity);
    }

    @Override
    public void deleteById(String id) {
        customerRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        customerRepository.deleteAll();
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email).orElse(null);
    }
}
