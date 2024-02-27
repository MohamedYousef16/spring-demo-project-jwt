package com.sec_project.vacation.vacation;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Integer> {

	@Query("""
			select v from Vacation v inner join Employee e on v.employee.id = e.id 
			where e.email = :empEmail
			""")
	List<Vacation> findByEmployeeEmail(String empEmail);
}
