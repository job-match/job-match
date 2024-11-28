package com.project.jobmatch.services;

public class ServicesConstants {
    public static final String TEMP_FILE = "temp-file";
    public static final String URL = "url";
    public static final String PUBLIC_ID = "public_id";
    public static final String COMPANY = "Company";
    public static final String MODIFY_PROFILE_ERROR_MESSAGE = "Only company's account can make changes to the company.";
    public static final String MODIFY_JOB_AD_ERROR_MESSAGE = "Only job ad owner can modify/delete the job ad!";
    public static final String YOU_ALREADY_APPLIED_ERROR_MESSAGE = "You already applied for this job ad!";
    public static final String APPLICATION_DENIED_ERROR_MESSAGE = "You do not meet ad's requirements!";
    public static final double REQUIREMENTS_THRESHOLD_PERCENTAGE = 50.0;
    public static final double MIN_SALARY_THRESHOLD_COEFFICIENT = 0.8;
    public static final double MAX_SALARY_THRESHOLD_COEFFICIENT = 1.2;
    public static final String REMOTE_LOCATION = "Remote";
    public static final String HYBRID_LOCATION = "Hybrid";
    public static final String JOB_AD_OWNER_ERROR_MESSAGE = "Only company which is owner of this Job Ad can confirm match with Job Application!";
    public static final String MODIFY_PROFESSIONAL_ERROR_MESSAGE = "Only owner can make changes to the professional info.";
    public static final String DELETE_PROFILE_ERROR_MESSAGE =
            "Only professional account's owner can delete their job applications!";
    public static final String YOU_ALREADY_SHOWED_INTEREST_ERROR_MESSAGE = "You already showed interest for this job application!";
    public static final String AD_REQUEST_DENIED_ERROR_MESSAGE = "This job ad's requirements do not meet the applicant's preferences!";
}
