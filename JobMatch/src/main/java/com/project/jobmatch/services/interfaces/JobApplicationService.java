package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.JobApplication;

public interface JobApplicationService {

    JobApplication getJobApplicationById(int id);
    //    JobApplication getJobApplicationByTitle(String title);
}
