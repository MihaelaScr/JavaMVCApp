package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miha on 11/11/2014.
 */
@MyController(urlPath = "/department")
public class DepartmentController {
    @MyRequestMethod(urlPath = "/all")
    public List<Department> getAllDepartments() {
        ArrayList<Department> departments = new ArrayList<Department>();
        Department department1 = new Department();
        department1.setId(938l);
        department1.setDepartmentName("HR");
        departments.add(department1);
        Department department2 = new Department();
        department2.setId(939l);
        department2.setDepartmentName("Finance");
        departments.add(department2);
        Department department3 = new Department();
        department3.setId(940l);
        department3.setDepartmentName("Human Resources");
        departments.add(department3);
        return departments;
    }
}
