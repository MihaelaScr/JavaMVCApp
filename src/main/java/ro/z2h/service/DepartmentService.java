package ro.z2h.service;

import ro.z2h.domain.Department;
import ro.z2h.domain.Employee;

import java.util.List;

/**
 * Created by Miha on 11/13/2014.
 */
public interface DepartmentService {
    List<Department> findAllDepartments();
}
