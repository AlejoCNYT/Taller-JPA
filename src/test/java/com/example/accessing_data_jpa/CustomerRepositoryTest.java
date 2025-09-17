package com.example.accessing_data_jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")      // <-- clave
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @BeforeEach
    void clean() {
        repository.deleteAll();  // opcional si usas create-drop (ver paso 3)
    }

    @Test
    void saveAndQuery() {
        repository.save(new Customer("Jack", "Bauer"));
        repository.save(new Customer("Kim", "Bauer"));

        List<Customer> bauers = repository.findByLastName("Bauer");
        assertThat(bauers).hasSize(2);

        Optional<Customer> one = repository.findById(bauers.get(0).getId());
        assertThat(one).isPresent();
        assertThat(one.get().getLastName()).isEqualTo("Bauer");
    }
}

