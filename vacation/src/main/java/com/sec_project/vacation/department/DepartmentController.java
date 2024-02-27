package com.sec_project.vacation.department;

import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/dept")
@RequiredArgsConstructor
//@PreAuthorize("hasRole('ADMIN')")
public class DepartmentController {

	private final DepatmentService service;
	
	@GetMapping()
	//@PreAuthorize("hasAnyAuthority('admin:read')")
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/{id}")
	//@PreAuthorize("hasAuthority('admin:read')")
	public ResponseEntity<?> findDeptById(@PathVariable Long id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping()
	//@PreAuthorize("hasAuthority('admin:create')")
	public ResponseEntity<?> createDept(@RequestBody Department dept){
		return ResponseEntity.ok(service.createDept(dept));
	}
}
