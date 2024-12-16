package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Professional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface JobApplicationService {

    void createJobApplication(JobApplication jobApplication, Professional professionalAuthenticated);

    void updateJobApplication(Professional professionalAuthenticated, JobApplication jobApplicationMapped);

    void deleteJobApplication(JobApplication jobApplicationToDelete, Professional professionalAuthenticated);

    void addJobAdToListOfAdMatchRequests(JobApplication jobApplication, JobAd jobAd);

    JobApplication getJobApplicationByIdFromCompany(int id);

    JobApplication getJobApplicationById(int jobApplicationId);

    List<JobApplication> getAllJobApplicationsOfProfessional
            (Professional professionalToRetrieveJobAppsFrom, Professional professionalAuthenticated);

    List<JobApplication> getAllJobApplications();

    List<JobApplication> getAllActiveJobApplications();

    List<JobApplication> getAllActiveJobApplicationsOfProfessional(Professional professional);

    List<JobApplication> searchJobApplications(String location, Double minSalary, Double maxSalary, String skill, String keyword);

    Page<JobApplication> searchJobApplicationsPaginated(String location, Double minSalary, Double maxSalary, String skill, String keyword, PageRequest pageRequest);

    List<JobApplication> getJobApplicationsByProfessionalId(int id);

    boolean checkIfOwnerOfJobApplication(Professional professional, JobApplication jobApplication);
}
