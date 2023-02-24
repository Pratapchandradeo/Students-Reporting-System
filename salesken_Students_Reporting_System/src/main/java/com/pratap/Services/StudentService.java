package com.pratap.Services;

import java.util.HashMap;
import java.util.List;

import com.pratap.Models.Student;

public interface StudentService {
	
	
	public String addStudents(String name);
	
	public Student getStudent(Integer id) throws Exception;
	
	public List<Student> getAllStudents();
	
	public String addMark(Integer stId,Integer semId,String subject, Integer mark);
	
	public String averagePersentage(Integer id);
	
	public HashMap<Integer, Double> topTwoStudents();
	
	

}
