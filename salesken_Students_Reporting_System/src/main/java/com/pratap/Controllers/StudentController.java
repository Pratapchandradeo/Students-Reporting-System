package com.pratap.Controllers;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratap.Models.Student;
import com.pratap.Repository.StudentRepo;
import com.pratap.Services.StudentServiceImpl;

@RestController
public class StudentController {
	
	@Autowired
	private StudentServiceImpl serviceImpl;
	
	@Autowired
	private StudentRepo studentRepo;
	
	private final String indexName ="student";
	
	
	@PostMapping("/addStudent")
	public ResponseEntity<String> addStudents(@RequestBody String name) throws IOException{
		
		String res = serviceImpl.addStudents(name);
		
		return new ResponseEntity<>(res,HttpStatus.OK);
		
	}
	
	
	@GetMapping("/findStudentById")
    public ResponseEntity<Student> getStudentById(@RequestParam("Id") Integer Id){
        Student student=null;
		try {
			student = serviceImpl.getStudent(Id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new ResponseEntity<>(student, HttpStatus.OK);
    }
	
	
	
	
	@GetMapping("/getAllStudent")
	public ResponseEntity<List<Student>> getAllStudents(){
		List<Student> students = serviceImpl.getAllStudents();
		
		return new ResponseEntity<List<Student>>(students,HttpStatus.OK);
	}
	
	@PostMapping(path = "/addMarks")
	public ResponseEntity<String> addMarks(@RequestParam("Id") Integer sId,
											@RequestParam("seId") Integer seId,
											@RequestParam("subject")String subject,
											@RequestParam("mark")Integer marks){
		String res = serviceImpl.addMark(sId, seId, subject, marks);
		
		return new ResponseEntity<String>(res,HttpStatus.OK);
	}
	
	

}
