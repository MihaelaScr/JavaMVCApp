package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Job;
import ro.z2h.service.JobServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miha on 11/13/2014.
 */
@MyController(urlPath = "/job")
public class JobController {
    @MyRequestMethod(urlPath = "/all")
    public List<Job> getAllJobs() {
        JobServiceImpl jsi = new JobServiceImpl();
        ArrayList<Job> jobs = (ArrayList<Job>) jsi.findAllJobs();
        return jobs;
    }
}
