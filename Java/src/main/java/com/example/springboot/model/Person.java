package com.example.springboot.model;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.annotation.Id;

@Data
@Document(indexName = "person")
public class Person {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "name")
    private String name;

}
