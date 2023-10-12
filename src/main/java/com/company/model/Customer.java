package com.company.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "customers")
public class Customer {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
    @Indexed(unique = true)
    private String email;
    private String pass;
    @DBRef
    private List<Order> orders;
}
//GET	/api/tutorials/:id/comments	retrieve all Comments of a Tutorial