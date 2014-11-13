package ro.z2h.service;

import ro.z2h.dao.JobDao;
import ro.z2h.domain.Job;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Miha on 11/13/2014.
 */
public class JobServiceImpl implements JobService {
    @Override
    public List<Job> findAllJobs() {
        Connection con = null;
        JobDao jobDao = new JobDao();
        try {
            con = DatabaseManager.getConnection("ZTH_21", "passw0rd");
            return jobDao.getAllJobs(con);
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
