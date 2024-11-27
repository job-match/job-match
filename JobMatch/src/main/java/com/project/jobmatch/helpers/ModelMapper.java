package com.project.jobmatch.helpers;

import com.project.jobmatch.models.Professional;
import com.project.jobmatch.models.dto.ProfessionalDtoInCreate;
import com.project.jobmatch.models.dto.ProfessionalDtoInUpdate;
import com.project.jobmatch.models.dto.ProfessionalDtoOut;
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
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelMapper {

    private final ProfessionalService professionalService;
    private final LocationService locationService;
    private final StatusService statusService;
    private final CompanyService companyService;

    @Autowired
    public ModelMapper(ProfessionalService professionalService,
                       LocationService locationService,
                       StatusService statusService,
                       CompanyService companyService) {
        this.professionalService = professionalService;
        this.locationService = locationService;
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
