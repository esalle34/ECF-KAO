package com.example.springboot.controller;

import com.example.springboot.model.Person;
import com.example.springboot.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Lazy
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person")
public class PersonController {

    private final PersonRepository PersonRepository;

    @PostMapping
    public void save(@RequestBody Person person) {
        PersonRepository.save(person);
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable String id) {
        return PersonRepository.findById(id).orElse(null);
    }

    @GetMapping
    public Iterable<Person> findAll() {
        return PersonRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        PersonRepository.deleteById(id);
    }

    @PutMapping
    public void update(@RequestBody Person person) {
        PersonRepository.save(person);
    }

    @GetMapping("/find")
    public List<Person> findByBrand(@RequestParam String name) {
        return PersonRepository.findAllByName(name);
    }
}