package com.sec_project.vacation.department;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepatmentService {

	private final DepartmentRepository deptRepo;
	
	
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(deptRepo.findAll());
	}
	
	public ResponseEntity<?> findById(Long id){
		return ResponseEntity.ok(deptRepo.findById(id));
	}
	
	public ResponseEntity<?> createDept(Department dept){
		return ResponseEntity.ok(deptRepo.save(dept));
	}
	
	public ResponseEntity<?> updateDept(Department dept){
		return ResponseEntity.ok(deptRepo.save(dept));
	}


}
