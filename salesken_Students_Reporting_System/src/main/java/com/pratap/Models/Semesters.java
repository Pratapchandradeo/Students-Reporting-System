package com.pratap.Models;


import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class Semesters {
	
	
	
	private Integer id;
	
	
	private Integer english;
	private Integer math;
	private Integer science;

	public Semesters(Integer i) {
		this.id=i;
	}
	
}
