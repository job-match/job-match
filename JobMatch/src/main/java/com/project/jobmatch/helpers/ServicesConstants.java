package com.project.jobmatch.helpers;

public class ServicesConstants {
    public static final String TEMP_FILE = "temp-file";
    public static final String URL = "url";
    public static final String PUBLIC_ID = "public_id";
    public static final String COMPANY = "Company";
    public static final String PROFESSIONAL_STATUS_BUSY = "Busy";
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
    public static final String DELETE_PROFILE_ERROR_MESSAGE = "Only professional account's owner can delete their job applications!";
    public static final String YOU_ALREADY_SHOWED_INTEREST_ERROR_MESSAGE = "You already showed interest for this job application!";
    public static final String AD_REQUEST_DENIED_ERROR_MESSAGE = "This job ad's requirements do not meet the applicant's preferences!";
    public static final String JOB_APPLICATION_OWNER_ERROR_MESSAGE = "Only professional which is owner of this Job Application can confirm match with Job Ad!";
    public static final String ALREADY_MATCHED_ERROR_MESSAGE = "Already Matched!";
    public static final String CREATE_JOB_APPLICATION_ERROR_MESSAGE = "You cannot create Job Application because your status is Busy!";

    //MAILJET
    // Job application creating messages
    public static final String JOB_APP_CREATION_SUBJECT_MESSAGE = "This is an automatic response from Job-Match!";
    public static final String JOB_APP_CREATION_TEXT_CONTENT = "Job application creation";
    public static final String JOB_APP_CREATION_HTML_CONTENT = "<h2>Hello %s!<h2>" +
            "<p><h3>This is an automatic response from Job-Match to let you know about a job application you have just created.<h3><p>" +
            "<p><h3>We wish you good luck!<h3><p>" +
            "<p><h4>Best,<h4><p>" +
            "<p><h5>Job-Match team<h5><p>";

    // Job application job add messages
    public static final String JOB_APP_JOB_AD_TWO_SIDE_MATCH_SUBJECT_MESSAGE = "You have got a match!";
    public static final String JOB_APP_JOB_AD_TWO_SIDE_MATCH_TEXT_CONTENT = "Congratulations! You've just got a match for your job add with id:%s";
    public static final String JOB_APP_JOB_AD_TWO_SIDE_MATCH_HTML_CONTENT =
            "<h2>Hello %s!<h2>" +
                    "<p><h3>This is an automatic response from Job-Match to let you know that %s responded to your match request.<h3><p>" +
                    "<p><h3>We hope that you both find what you are looking for!<h3><p>" +
                    "<p><h4>Best,<h4><p>" +
                    "<p><h5>Job-Match team<h5><p>";

}
