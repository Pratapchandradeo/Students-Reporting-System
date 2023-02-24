package com.pratap.Repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.pratap.Models.Student;

@Repository
public interface StudentRepo extends ElasticsearchRepository<Student, Integer> {

}
