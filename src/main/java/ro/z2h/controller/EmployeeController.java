package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Employee;
import ro.z2h.service.EmployeeServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miha on 11/11/2014.
 */
@MyController(urlPath = "/employee")//clasa ii spune dispatcherului ca este controller si raspunde pt requesturile /employee
public class EmployeeController {
    @MyRequestMethod(urlPath = "/all")
    public List<Employee> getAllEmployees() {
//        ArrayList<Employee> employees = new ArrayList<Employee>();
//        Employee employee1 = new Employee();
//        employee1.setId(122l);
//        employee1.setLastName("Raluca");
//        employee1.setFirstName("Russ");
//        employees.add(employee1);
//        Employee employee2 = new Employee();
//        employee2.setId(123l);
//        employee2.setLastName("Jasmina");
//        employee2.setFirstName("Said");
//        employees.add(employee2);

        EmployeeServiceImpl esi = new EmployeeServiceImpl();
        ArrayList<Employee> employees = (ArrayList<Employee>) esi.findAllEmployees();
        return employees;
    }

    @MyRequestMethod(urlPath = "/one")
    public Employee getOneEmployee() {
//        Employee employee = new Employee();
//        employee.setId(111l);
//        employee.setLastName("Mihaela");
//        employee.setFirstName("Scripcaru");

        EmployeeServiceImpl esi = new EmployeeServiceImpl();
        return esi.findOneEmployee(110l);
    }
}
