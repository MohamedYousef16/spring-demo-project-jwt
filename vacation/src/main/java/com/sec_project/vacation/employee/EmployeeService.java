package com.sec_project.vacation.employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeRepository empRepo;
	
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(empRepo.findAll());
	}
	
	public ResponseEntity<?> findById(Long id){
		return ResponseEntity.ok(empRepo.findById(id));
	}
	
	public ResponseEntity<?> findEmpByDeptId( Long  id){
		return ResponseEntity.ok(empRepo.findByDepartmentId(id));
	}
	
	public ResponseEntity<?> createEmp(Employee emp){
		return ResponseEntity.ok(empRepo.save(emp));
	}
	
	public ResponseEntity<?> updateEmp(Employee emp){
		return ResponseEntity.ok(empRepo.save(emp));
	}
	
	public void deleteEmp(Long id) {
		empRepo.deleteById(id);
	}
}
