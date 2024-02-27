package com.sec_project.vacation.vacation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VacationService {

	private final VacationRepository vacRepo;
	
	
	
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(vacRepo.findAll());
	}
	
	public ResponseEntity<?> findByEmpEmail(String email){
		return ResponseEntity.ok(vacRepo.findByEmployeeEmail(email));
	}
	
	public ResponseEntity<?> vacRequest(Vacation vac){
		vac.setStatus(Status.WAITINGFORREPLY);
		return ResponseEntity.ok(vacRepo.save(vac));
	}
	
	public ResponseEntity<?> vacResponse(Vacation updatedVacation){
	    Vacation existingVacation = vacRepo.findById(updatedVacation.getId()).orElse(null);
	    if (existingVacation == null) {
	        return ResponseEntity.notFound().build();
	    }
	    existingVacation.setStatus(updatedVacation.getStatus());
	    vacRepo.save(existingVacation);
	    return ResponseEntity.ok(existingVacation);
	}
	  
}
