package com.sec_project.vacation.employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/emp")
@RequiredArgsConstructor
public class EmpolyeeController {

	private final EmployeeService service;
	
	@GetMapping()
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(service.findAll());
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> findEmpById(@PathVariable  Long id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping("/dept/{id}")
	public ResponseEntity<?> findEmpByDeptId(@PathVariable Long  id){
		return ResponseEntity.ok(service.findEmpByDeptId(id));
	}
	
	@PostMapping()
	public ResponseEntity<?> createEmp(@RequestBody Employee emp){
		return ResponseEntity.ok(service.createEmp(emp));
	}
	
	@DeleteMapping()
	public void deleteEmp(@RequestParam Long id) {
		service.deleteEmp(id);
	}
}
