package com.pratap.Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pratap.Models.Student;
import com.pratap.Repository.StudentRepo;
import com.pratap.Services.StudentServiceImpl;

import co.elastic.clients.elasticsearch.core.IndexResponse;

@Controller
public class StudentUiController {
	
	@Autowired
	private StudentServiceImpl serviceImpl;
	
	@Autowired
	private StudentRepo studentRepo;
	
	private final String indexName ="student";
	
	
	
	 
	@RequestMapping("/")
	public ModelAndView Index() {
		ModelAndView modelAndView = new ModelAndView("Home.jsp");
		
		List<Student> students = serviceImpl.getAllStudents();
		
		modelAndView.addObject("students", students);
		
		String avgClass1 = serviceImpl.averagePersentage(1);
		String avgClass2 = serviceImpl.averagePersentage(2);
		
		HashMap<Integer, Double> hash = serviceImpl.topTwoStudents();
		
		Integer student1=0,student2=0;
		Double mark1=0.0,mark2=0.0;
		
		
		for(Map.Entry<Integer, Double> a: hash.entrySet()) {
			
			if(a.getValue()>mark1)
			{
				student1=a.getKey();
				mark1=a.getValue().doubleValue();
				
				student2=student1;
				mark2=mark1.doubleValue();
			}
			else if(a.getValue()>mark2)
			{
				mark2=a.getValue().doubleValue();
				student2=a.getKey();
			}	
		}
		
		Optional<Student> stOptional = studentRepo.findById(student1);
		String name1 = stOptional.get().getName();
		
		Optional<Student> stOptional1 = studentRepo.findById(student2);
		String name2 = stOptional1.get().getName();
		
		modelAndView.addObject("avgClass1", avgClass1);
		modelAndView.addObject("avgClass2", avgClass2);
		
		modelAndView.addObject("top1", student1);
		modelAndView.addObject("top1_score", mark1);
		modelAndView.addObject("top1_name", name1);
		
		modelAndView.addObject("top2", student2);
		modelAndView.addObject("top2_score", mark2);
		modelAndView.addObject("top2_name", name2);
		
		
		return modelAndView;
	}

	@RequestMapping(value = "/addSemeMarks", method = RequestMethod.POST)
    public String addSemesterMarks(@RequestParam("sId") Integer sId, @RequestParam("seId") Integer seId,  @RequestParam("English") Integer English,
                                   @RequestParam("Maths") Integer Maths, @RequestParam("Science") Integer Science)
    {
            if(English != 0)
            {
                serviceImpl.addMark(sId, seId, "English", English);
            }
            if(Science != 0)
            {
                serviceImpl.addMark(sId, seId, "Science", Science);
            }
            if(Maths != 0)
            {
                serviceImpl.addMark(sId, seId, "Maths",Maths);
            }


        return "redirect:/";
    }
	
	
	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public String addStudentByName(@RequestParam("name") String name)
    {
        
            String response = serviceImpl.addStudents(name);

        return "redirect:/";

    }
	
	
	
	@RequestMapping(value = "/addStudentMarks", method = RequestMethod.POST)
    public String addMarks(@RequestParam("Id") Integer Id, @RequestParam("seId") Integer seId, @RequestParam("subject") String subject, @RequestParam("marks") Integer marks)
    {
        try {
            String response = serviceImpl.addMark(Id, seId, subject, marks);
            return "redirect:/";
        }catch (Exception e)
        {
            return e.toString();
        }

    }
	
	@GetMapping("/home")
	public String homeLunch() {
		return "home";
	}
	
	

}
