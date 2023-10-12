package com.company.service.interfaces;

import com.company.model.Customer;

public interface CustomerService extends EntityService<Customer> {
    Customer getCustomerByEmail(String email);
}
