package com.project.jobmatch.helpers;

import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.Requirement;
import com.project.jobmatch.models.dto.JobAdDtoInCreate;
import com.project.jobmatch.models.dto.JobAdDtoOut;
import com.project.jobmatch.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ModelMapper {
    private final ProfessionalService professionalService;
    private final JobAdService jobAdService;
    private final LocationService locationService;
    private final RequirementService requirementService;
    private final StatusService statusService;
    private final JobApplicationService jobApplicationService;

    @Autowired
    public ModelMapper(ProfessionalService profService,
                       JobAdService jobAdService,
                       LocationService locationService,
                       RequirementService requirementService,
                       StatusService statusService,
                       JobApplicationService jobApplicationService) {
        this.professionalService = profService;
        this.jobAdService = jobAdService;
        this.locationService = locationService;
        this.requirementService = requirementService;
        this.statusService = statusService;
        this.jobApplicationService = jobApplicationService;
    }

    public JobAd fromJodAdDtoIn(int id, JobAdDtoInCreate jobAdDtoInCreate) {
        JobAd jobAd = fromJobAdDtoIn(jobAdDtoInCreate);
        jobAd.setId(id);

        JobAd repositoryAd = jobAdService.getJobAdById(id);
        jobAd.setStatus(statusService.getStatusByType("Active"));
        jobAd.setCompany(repositoryAd.getCompany());
        jobAd.setListOfApplicationMatchRequests(repositoryAd.getListOfApplicationMatchRequests());

        return jobAd;
    }
    public JobAd fromJobAdDtoIn(JobAdDtoInCreate jobAdDtoInCreate) {
        JobAd jobAd = new JobAd();
        jobAd.setPositionTitle(jobAdDtoInCreate.getTitle());
        jobAd.setMinSalaryBoundary(jobAdDtoInCreate.getMinSalaryBoundary());
        jobAd.setMaxSalaryBoundary(jobAdDtoInCreate.getMaxSalaryBoundary());
        jobAd.setJobDescription(jobAdDtoInCreate.getDescription());
        jobAd.setLocation(locationService.getLocationByName(jobAdDtoInCreate.getLocation()));
        jobAd.setRequirements(fromStringSetToRequirementSet(jobAdDtoInCreate.getRequirements()));

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

    public Set<Requirement> fromStringSetToRequirementSet(Set<String> requirementSet) {
        if (requirementSet == null) {
            return new HashSet<>();
        }

        return requirementSet.stream()
                .map(requirementService::getRequirementByName)
                .collect(Collectors.toSet());
    }
}
