package com.sec_project.vacation.vacation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/vac")
@RequiredArgsConstructor
public class VacationController {
	
	private final VacationService service;

	@GetMapping()
	public ResponseEntity<?> findAllVac(){
		return ResponseEntity.ok(service.findAll());
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<?> findByEmpEmail(@PathVariable String email){
		return ResponseEntity.ok(service.findByEmpEmail(email));
	}
	
	@PostMapping()
	public ResponseEntity<?> vacRequest(@RequestBody Vacation vac){
		return ResponseEntity.ok(service.vacRequest(vac));
	}
	@PutMapping()
	public ResponseEntity<?> vacResponse(@RequestBody Vacation vac){
		return ResponseEntity.ok(service.vacResponse(vac));
	}
	
}
