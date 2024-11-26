package com.project.jobmatch.helpers;

import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.dto.JobAdDtoIn;
import com.project.jobmatch.models.dto.JobAdDtoOut;
import com.project.jobmatch.services.interfaces.JobAdService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelMapper {
    private final ProfessionalService professionalService;
    private final JobAdService jobAdService;

    @Autowired
    public ModelMapper(ProfessionalService profService,
                       JobAdService jobAdService) {
        this.professionalService = profService;
        this.jobAdService = jobAdService;
    }

    public JobAd fromJobAdDtoIn(JobAdDtoIn jobAdDtoIn) {
        JobAd jobAd = new JobAd();
        jobAd.setPositionTitle(jobAdDtoIn.getTitle());
        jobAd.setMinSalaryBoundary(jobAdDtoIn.getMinSalaryBoundary());
        jobAd.setMaxSalaryBoundary(jobAdDtoIn.getMaxSalaryBoundary());
        jobAd.setJobDescription(jobAdDtoIn.getDescription());
    //    jobAd.setLocation(jobAdDtoIn.getLocation());
        return jobAd;
    }

    public JobAdDtoOut fromJobAdToJobAdDtoOut(JobAd jobAd) {
        JobAdDtoOut jobAdDtoOut = new JobAdDtoOut();
        jobAdDtoOut.setTitle(jobAd.getPositionTitle());
        jobAdDtoOut.setDescription(jobAd.getJobDescription());
        jobAdDtoOut.setMinSalaryBoundary(jobAd.getMinSalaryBoundary());
        jobAdDtoOut.setMaxSalaryBoundary(jobAd.getMaxSalaryBoundary());
        jobAdDtoOut.setLocation(jobAd.getLocation().getName());
        jobAdDtoOut.setStatus(jobAd.getStatus().getType());

        return jobAdDtoOut;
    }

    public List<JobAdDtoOut> fromJobAdToJobAdDtoOutList(List<JobAd> jobAds) {
        if(jobAds == null) {
            return new ArrayList<>();
        }

        return jobAds.stream()
                .map(this::fromJobAdToJobAdDtoOut)
                .collect(Collectors.toList());
    }
}
