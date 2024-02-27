package com.sec_project.vacation;


import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sec_project.vacation.auth.AuthenticationService;
import com.sec_project.vacation.auth.RegisterRequest;
import com.sec_project.vacation.department.Department;
import com.sec_project.vacation.department.DepatmentService;
import com.sec_project.vacation.employee.Employee;
import com.sec_project.vacation.employee.EmployeeService;
import com.sec_project.vacation.user.Role;
import com.sec_project.vacation.vacation.Vacation;
import com.sec_project.vacation.vacation.VacationService;


@SpringBootApplication
public class VacationRequestApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacationRequestApplication.class, args);
	}
	
	@Bean
	CommandLineRunner commandLineRunner(DepatmentService depatmentService ,EmployeeService employeeService, VacationService vacationService , AuthenticationService authenticationService) {
		return args -> {
			
			var admin = RegisterRequest.builder()
					.firstname("admin")
					.lastname("admin")
					.email("admin@gmail.com")
					.password("password")
					.role(Role.ADMIN)
					.build();
			System.out.println("Admin Token : " + authenticationService.register(admin).getAccessToken());
			
			var manager = RegisterRequest.builder()
					.firstname("manager")
					.lastname("manager")
					.email("manager@gmail.com")
					.password("password")
					.role(Role.MANAGER)
					.build();
			System.out.println("Manager Token : " + authenticationService.register(manager).getAccessToken());
			
			var user = RegisterRequest.builder()
					.firstname("user")
					.lastname("user")
					.email("user@gmail.com")
					.password("password")
					.role(Role.USER)
					.build();
			System.out.println("User Token : " + authenticationService.register(user).getAccessToken());
			
			var dept = Department.builder()
					.name("it")
					.build();
			depatmentService.createDept(dept);
			
			var emp = Employee.builder()
					.firstName("mohamed")
					.lastName("ail")
					.email("mohamedail@gmail.com")
					.password("123")
					.department(dept)
					.build();
			employeeService.createEmp(emp);

			var emp2 = Employee.builder()
			        .firstName("Jane")
			        .lastName("Smith")
			        .email("jane.smith@example.com")
			        .password("456")
			        .department(dept)
			        .build();
			employeeService.createEmp(emp2);
	
			
			  dept.setEmployees(Arrays.asList(emp, emp2));
			  depatmentService.updateDept(dept);
			 
			
			LocalDate startDate = LocalDate.of(2024, 1, 8);
			LocalDate endDate = LocalDate.of(2024, 2, 8);
			
			var vac = Vacation.builder()
					.startDate(startDate)
					.endDate(endDate)
					.employee(emp)
					.build();
			vacationService.vacRequest(vac);
			
			var vac1 = Vacation.builder()
					.startDate(startDate)
					.endDate(endDate)
					.employee(emp)
					.build();
			vacationService.vacRequest(vac1);
			
			
			var vac2 = Vacation.builder()
					.startDate(startDate)
					.endDate(endDate)
					.employee(emp)
					.build();
			vacationService.vacRequest(vac2);
		}; 
	}
}
