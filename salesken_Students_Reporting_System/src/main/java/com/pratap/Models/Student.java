package com.pratap.Models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(indexName ="student")
@Component
public class Student {
	
	@Id
	private Integer id;
	
	@Field(type = FieldType.Text, name = "Name")
	private String name;
	
	@Field(type = FieldType.Auto,name = "Semester")
	private List<Semesters> semester;

	
}
