package com.project.jobmatch;

import com.project.jobmatch.models.*;
import java.util.HashSet;

public class Helpers {
    public static JobApplication createMockApplication() {
        JobApplication mockJobApplication = new JobApplication();
        mockJobApplication.setId(1);
        mockJobApplication.setLocation(createMockLocation());
        mockJobApplication.setSkills(new HashSet<>());
        mockJobApplication.setStatus(createMockStatus());
        mockJobApplication.setListOfAdMatchRequests(new HashSet<>());
        mockJobApplication.setMaxDesiredSalary(2500);
        mockJobApplication.setMinDesiredSalary(2000);
        mockJobApplication.setMotivationLetter("MockMotivationLetter");
        mockJobApplication.setProfessional(createMockProfessional());
        return mockJobApplication;
    }

    public static JobAd createMockJobAd() {
        JobAd mockJobAd = new JobAd();
        mockJobAd.setId(1);
        mockJobAd.setPositionTitle("MockPositionTitle");
        mockJobAd.setMinSalaryBoundary(2000);
        mockJobAd.setMaxSalaryBoundary(2500);
        mockJobAd.setJobDescription("MockJobDescription");
        mockJobAd.setStatus(createMockStatus());
        mockJobAd.setLocation(createMockLocation());
        mockJobAd.setCompany(createMockCompany());
        mockJobAd.setRequirements(new HashSet<>());
        mockJobAd.setListOfApplicationMatchRequests(new HashSet<>());

        return mockJobAd;
    }

    public static Professional createMockProfessional(){
        Professional mockProfessional = new Professional();
        mockProfessional.setId(1);
        mockProfessional.setUsername("MockUsername");
        mockProfessional.setPassword("MockPassword");
        mockProfessional.setFirstName("MockFirstName");
        mockProfessional.setLastName("MockLastName");
        mockProfessional.setEmail("MockEmail");
        mockProfessional.setSummary("MockSummary");
        mockProfessional.setLocation(createMockLocation());
        mockProfessional.setStatus(createMockStatus());
        mockProfessional.setPicture(createMockPicture());
        mockProfessional.setJobApplications(new HashSet<>());

        return mockProfessional;
    }

    public static Company createMockCompany(){
        Company mockCompany = new Company();
        mockCompany.setId(1);
        mockCompany.setUsername("MockUsername");
        mockCompany.setPassword("MockPassword");
        mockCompany.setName("MockCompanyName");
        mockCompany.setEmail("MockEmail");
        mockCompany.setDescription("MockDescription");
        mockCompany.setLocation(createMockLocation());
        mockCompany.setContacts("MockContacts");
        mockCompany.setPicture(createMockPicture());
        mockCompany.setJobAds(new HashSet<>());

        return mockCompany;
    }

    public static Location createMockLocation(){
        Location mockLocation = new Location();
        mockLocation.setId(1);
        mockLocation.setName("MockLocationName");

        return mockLocation;
    }

    public static Match createMockMatch(){
        Match mockMatch = new Match();
        mockMatch.setId(1);
        mockMatch.setJobAd(createMockJobAd());
        mockMatch.setJobApplication(createMockApplication());

        return mockMatch;
    }

    public static Picture createMockPicture() {
        Picture mockPicture = new Picture();
        mockPicture.setPublicId("MockPublicId");
        mockPicture.setUrl("MockUrl");
        mockPicture.setPublicId("MockPublicId");

        return mockPicture;
    }

    public static Requirement createMockRequirement() {
        Requirement mockRequirement = new Requirement();
        mockRequirement.setId(1);
        mockRequirement.setType("MockRequirementType");

        return mockRequirement;
    }

    public static Skill createMockSkill() {
        Skill mockSkill = new Skill();
        mockSkill.setId(1);
        mockSkill.setType("MockSkillType");

        return mockSkill;
    }

    public static Status createMockStatus() {
        Status mockStatus = new Status();
        mockStatus.setId(1);
        mockStatus.setType("MockStatusType");

        return mockStatus;
    }
}
