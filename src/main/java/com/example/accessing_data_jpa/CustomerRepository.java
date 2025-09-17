package com.example.accessing_data_jpa;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastName(String lastName);
    // NO declares findById aquí; ya existe en CrudRepository y devuelve Optional<Customer>
}
