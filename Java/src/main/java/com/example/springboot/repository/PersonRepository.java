package com.example.springboot.repository;

import com.example.springboot.model.Person;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Lazy
public interface PersonRepository extends ElasticsearchRepository<Person, String> {

    List<Person> findAllByName(String name);
}