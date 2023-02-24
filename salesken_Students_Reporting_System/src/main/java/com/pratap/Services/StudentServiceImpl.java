package com.pratap.Services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pratap.Models.Semesters;
import com.pratap.Models.Student;
import com.pratap.Repository.StudentRepo;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepo studentRepo;
	

	//Add new Students bases on random id
	@Override
	public String addStudents(String name) {
		
		Integer id  = Integer.valueOf((int)studentRepo.count()+1);
		
		Semesters first = new Semesters(1);
		Semesters secand = new Semesters(2);
		
		List<Semesters> semeList = new ArrayList<>();
		semeList.add(first);
		semeList.add(secand);
		
		
		Student student = new Student(id, name, semeList);
		
		try {
			studentRepo.save(student);
		} catch (Exception e) {
		return e.getMessage();
		}
		
		
		return "Student added Successfully";
	}
	
	
//find student by id or get the student by id
	@Override
	public Student getStudent(Integer id) throws Exception {
		
		Optional<Student> stOptional=studentRepo.findById(id);
		
		if(stOptional.isEmpty()) {
			throw new Exception("Student Not found On this id");
		}
		Student student=stOptional.get();
						
		return student;	
	}

	
	//get all students from of a list 
	
	@Override
	public List<Student> getAllStudents() {
		Iterable<Student> stIterable = studentRepo.findAll();
		
		List<Student> studentsList = new ArrayList<>();
		
		for(Student a:stIterable)
		{
			studentsList.add(a);
		}
		
		return studentsList;
	}
	
	
	
	@Override
	public String addMark(Integer stId, Integer semId, String subject, Integer mark) {
		Optional<Student> stOptional = studentRepo.findById(stId);
		List<Semesters> stList = new ArrayList<>();
		if(stOptional.isEmpty())
		{
			return "There is no Student exist on this id"+stId;
		}
		else
		{
			
			Student student = stOptional.get();
			
			stList = student.getSemester();
			for(Semesters s : stList) {
				if(s.getId()==semId) {
					if(subject.equals("English")) {
						s.setEnglish(mark);
					}
					else if(subject.equals("Math")) {
						s.setMath(mark);
					}
					else if(subject.equals("Science")) {
						s.setScience(mark);
					}
					else
					{
						return "No subject avalabul in this Name:"+subject;
					}
					break;
				}
				
			}
			
			student.setSemester(stList);
			studentRepo.save(student);
			
		}
		return "Mark added Successfully !!!!!!!";
	}

	@Override
	public String averagePersentage(Integer id) {
		Double avg = 0.0;
		DecimalFormat d = new DecimalFormat("####0.00");
		
		List<Double> persentages = new ArrayList<>();
		
		Iterable<Student> stIterable = studentRepo.findAll();
		
		for(Student s:stIterable) {
			Semesters semesters = s.getSemester().get(id-1);
			
			Double sum = Double.valueOf((semesters.getEnglish()+semesters.getMath()+semesters.getScience()));
			
			Double per = sum/3;
			
			persentages.add(per);
		}
		
		for(Double a:persentages)
		{
			avg+=a;
		}
		avg=avg/persentages.size();
		
		
		
		return d.format(avg);
	}

	@Override
	public HashMap<Integer, Double> topTwoStudents() {
		HashMap<Integer, Double> map = new HashMap<>();
		
				Iterable<Student> stIterable =studentRepo.findAll();
				
				for(Student s : stIterable) {
					
					Semesters semesters_1 =s.getSemester().get(0); 
					Semesters semesters_2 =s.getSemester().get(1);
					
					Double sum  = Double.valueOf((semesters_1.getEnglish()+semesters_1.getMath()+semesters_1.getScience()+semesters_2.getEnglish()+semesters_2.getMath()+semesters_2.getScience()));
					
					Double max = sum/2;
					
					map.put(s.getId(), max);
				
				}
		
		return map;
	}
	

}
