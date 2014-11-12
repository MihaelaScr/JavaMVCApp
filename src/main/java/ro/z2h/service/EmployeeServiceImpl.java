package ro.z2h.service;

import ro.z2h.dao.EmployeeDao;
import ro.z2h.domain.Employee;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Miha on 11/12/2014.
 */
public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public List<Employee> findAllEmployees() {
        Connection con = null;
        EmployeeDao employeeDao = new EmployeeDao();
        try {
            con = DatabaseManager.getConnection("ZTH_21","passw0rd");
            return employeeDao.getAllEmployees(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Employee findOneEmployee(Long id) {
        Connection con = null;
        EmployeeDao employeeDao = new EmployeeDao();
        try {
            con = DatabaseManager.getConnection("ZTH_21","passw0rd");
            return employeeDao.getEmployeeById(con, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
