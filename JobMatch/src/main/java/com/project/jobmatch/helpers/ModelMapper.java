package com.project.jobmatch.helpers;

import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.dto.CompanyDtoInCreate;
import com.project.jobmatch.models.dto.CompanyDtoInUpdate;
import com.project.jobmatch.models.dto.CompanyDtoOut;
import com.project.jobmatch.services.interfaces.CompanyService;
import com.project.jobmatch.services.interfaces.LocationService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelMapper {

    private final ProfessionalService professionalService;
    private final LocationService locationService;
    private final CompanyService companyService;

    @Autowired
    public ModelMapper(ProfessionalService profService, LocationService locationService, CompanyService companyService) {
        this.professionalService = profService;
        this.locationService = locationService;
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
}
