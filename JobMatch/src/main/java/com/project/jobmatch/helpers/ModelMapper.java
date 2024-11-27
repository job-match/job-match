package com.project.jobmatch.helpers;

import com.project.jobmatch.models.Professional;
import com.project.jobmatch.services.interfaces.LocationService;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.dto.CompanyDtoInCreate;
import com.project.jobmatch.models.dto.CompanyDtoInUpdate;
import com.project.jobmatch.models.dto.CompanyDtoOut;
import com.project.jobmatch.services.interfaces.CompanyService;
import com.project.jobmatch.services.interfaces.LocationService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import com.project.jobmatch.services.interfaces.StatusService;
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
    private final CompanyService companyService;
    private final JobApplicationService jobApplicationService;

    @Autowired
    public ModelMapper(ProfessionalService profService,
                       JobAdService jobAdService,
                       LocationService locationService,
                       StatusService statusService,
                       CompanyService companyService) {
        this.professionalService = professionalService;
                       RequirementService requirementService,
                       StatusService statusService,
                       JobApplicationService jobApplicationService) {
        this.professionalService = profService;
        this.jobAdService = jobAdService;
        this.locationService = locationService;
        this.requirementService = requirementService;
        this.statusService = statusService;
        this.companyService = companyService;
    }

    public CompanyDtoOut fromCompanyToCompanyDtoOut (Company company) {
        CompanyDtoOut companyDtoOut = new CompanyDtoOut();

        companyDtoOut.setUsername(company.getUsername());
        companyDtoOut.setName(company.getName());
        companyDtoOut.setEmail(company.getEmail());
        companyDtoOut.setDescription(company.getDescription());
        companyDtoOut.setLocation(company.getLocation().getName());
        companyDtoOut.setContacts(company.getContacts());

        return companyDtoOut;
    }

    public Company fromCompanyDtoInToCompany(CompanyDtoInCreate companyDtoInCreate) {
        Company company = new Company();

        company.setUsername(companyDtoInCreate.getUsername());
        company.setPassword(companyDtoInCreate.getPassword());
        company.setName(companyDtoInCreate.getName());
        company.setEmail(companyDtoInCreate.getEmail());
        company.setDescription(companyDtoInCreate.getDescription());
        company.setLocation(locationService.getLocationByName(companyDtoInCreate.getLocation()));
        company.setContacts(companyDtoInCreate.getContacts());

        return company;
    }

    public List<CompanyDtoOut> fromListCompaniesToListCompaniesDtoOut (List<Company> companyList) {
        if (companyList == null) {
            return new ArrayList<>();
        }

        return companyList.stream()
                .map(company -> {
                    CompanyDtoOut companyDtoOut = new CompanyDtoOut();

                    companyDtoOut.setUsername(company.getUsername());
                    companyDtoOut.setName(company.getName());
                    companyDtoOut.setEmail(company.getEmail());
                    companyDtoOut.setDescription(company.getDescription());
                    companyDtoOut.setLocation(company.getLocation().getName());
                    companyDtoOut.setContacts(company.getContacts());

                    return companyDtoOut;
                }).collect(Collectors.toList());
    }

    public Company fromCompanyDtoInUpdateToCompany(int id, CompanyDtoInUpdate companyDtoInUpdate) {
        Company company = new Company();

        company.setId(id);
        company.setUsername(companyService.getCompanyById(id).getUsername());
        company.setPassword(companyDtoInUpdate.getPassword());
        company.setName(companyDtoInUpdate.getName());
        company.setEmail(companyDtoInUpdate.getEmail());
        company.setDescription(companyDtoInUpdate.getDescription());
        company.setLocation(locationService.getLocationByName(companyDtoInUpdate.getLocation()));
        company.setContacts(companyDtoInUpdate.getContacts());
        company.setJobAds(companyService.getCompanyById(id).getJobAds());

        return company;
        this.jobApplicationService = jobApplicationService;
    }

    public JobAd fromJodAdDtoIn(int id, JobAdDtoInUpdate jobAdDtoInUpdate) {
        JobAd jobAd = jobAdService.getJobAdById(id);

        jobAd.setPositionTitle(jobAdDtoInUpdate.getTitle());
        jobAd.setMinSalaryBoundary(jobAdDtoInUpdate.getMinSalaryBoundary());
        jobAd.setMaxSalaryBoundary(jobAdDtoInUpdate.getMaxSalaryBoundary());
        jobAd.setJobDescription(jobAdDtoInUpdate.getDescription());
        jobAd.setLocation(locationService.getLocationByName(jobAdDtoInUpdate.getLocation()));
        jobAd.setStatus(statusService.getStatusByType(jobAdDtoInUpdate.getStatus()));

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
        jobAd.setStatus(statusService.getStatusByType("Active"));

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

    public ProfessionalDtoOut fromProfessionalToProfessionalDtoOut(Professional professional) {
        ProfessionalDtoOut professionalDtoOut = new ProfessionalDtoOut();

        professionalDtoOut.setUsername(professional.getUsername());
        professionalDtoOut.setFirstName(professional.getFirstName());
        professionalDtoOut.setLastName(professional.getLastName());
        professionalDtoOut.setEmail(professional.getEmail());
        professionalDtoOut.setSummary(professional.getSummary());
        professionalDtoOut.setLocation(professional.getLocation().getName());
        professionalDtoOut.setStatus(professional.getStatus().getType());

        return professionalDtoOut;
    }

    public Professional fromProfessionalDtoInToProfessional(ProfessionalDtoInCreate professionalDtoInCreateIn) {
        Professional professional = new Professional();

        professional.setUsername(professionalDtoInCreateIn.getUsername());
        professional.setPassword(professionalDtoInCreateIn.getPassword());
        professional.setFirstName(professionalDtoInCreateIn.getFirstName());
        professional.setLastName(professionalDtoInCreateIn.getLastName());
        professional.setEmail(professionalDtoInCreateIn.getEmail());
        professional.setSummary(professionalDtoInCreateIn.getSummary());
        professional.setLocation(locationService.getLocationByName(professionalDtoInCreateIn.getLocation()));
        professional.setStatus(statusService.getStatusByType("Active"));

        return professional;
    }

    public Professional fromProfessionalDtoInToProfessional(int id, ProfessionalDtoInUpdate professionalDtoInUpdate) {
        Professional professional = new Professional();
        professional.setId(id);

        professional.setUsername(professionalService.getProfessionalById(id).getUsername());
        professional.setPassword(professionalDtoInUpdate.getPassword());
        professional.setFirstName(professionalDtoInUpdate.getFirstName());
        professional.setLastName(professionalDtoInUpdate.getLastName());
        professional.setEmail(professionalDtoInUpdate.getEmail());
        professional.setSummary(professionalDtoInUpdate.getSummary());
        professional.setLocation(locationService.getLocationByName(professionalDtoInUpdate.getLocation()));
        professional.setStatus(statusService.getStatusByType(professionalDtoInUpdate.getStatus()));

        return professional;
    }

    public List<ProfessionalDtoOut> fromListProfessionalsToListProfessionalDtoOut(List<Professional> professionals) {
        if (professionals == null) {
            return new ArrayList<>();
        }

        return professionals.stream()
                .map(this::fromProfessionalToProfessionalDtoOut)
                .collect(Collectors.toList());
    }
}
