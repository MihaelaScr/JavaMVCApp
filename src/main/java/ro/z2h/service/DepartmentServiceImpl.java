package ro.z2h.service;

import ro.z2h.dao.DepartmentDao;
import ro.z2h.dao.EmployeeDao;
import ro.z2h.domain.Department;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Miha on 11/13/2014.
 */
public class DepartmentServiceImpl implements DepartmentService {
    @Override
    public List<Department> findAllDepartments() {
        Connection con = null;
        DepartmentDao departmentDao = new DepartmentDao();
        try {
            con = DatabaseManager.getConnection("ZTH_21", "passw0rd");
            return departmentDao.getAllDepartments(con);
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
