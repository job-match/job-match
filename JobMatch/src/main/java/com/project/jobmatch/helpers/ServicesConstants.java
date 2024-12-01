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

    // Mailjet API

    // LETTING BOTH PARTIES KNOW OF A SUCCESSFUL MATCH
    public static final String SUCCESSFUL_MATCH_SUBJECT_MESSAGE = "You have got a match!";
    public static final String SUCCESSFUL_MATCH_TEXT_CONTENT = "Congratulations! You've just got a match!";
    public static final String SUCCESSFUL_MATCH_HTML_CONTENT =
            "<h4 style='font-family: Arial, sans-serif; color: #333;'>Hello %s!</h4>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 14px; color: #555;'>This is an automatic response from <strong>Job-Match</strong> to let you know you have got a match!.</p>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 14px; color: #555;'>We hope that you both find what you are looking for!</p>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 14px; color: #555;'>Best regards,</p>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 14px; color: #555;'><strong>Job-Match Team</strong></p>";

    // NOTIFYING THE PROFESSIONAL FOR A MATCH REQUEST BY A COMPANY
    public static final String JOB_APP_MATCH_REQUEST_SUBJECT_MESSAGE = "A company has liked you job application!";
    public static final String JOB_APP_MATCH_REQUEST_TEXT_CONTENT = "Hey %s, you have got a match request to your job app by a %s.";
    public static final String JOB_APP_MATCH_REQUEST_HTML_CONTENT =
            "<h4 style='font-family: Arial, sans-serif; color: #333;'>Hello %s!</h4>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 14px; color: #555;'>This is an automatic response from <strong>Job-Match</strong> to let you know that you have received a match request!</p>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 14px; color: #555;'>Feel free to explore the job ad and the company behind it.</p>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 14px; color: #555;'>Best regards,</p>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 14px; color: #555;'><strong>Job-Match Team</strong></p>";

    // NOTIFYING THE COMPANY FOR A MATCH REQUEST BY A PROFESSIONAL
    public static final String JOB_AD_MATCH_REQUEST_SUBJECT_MESSAGE = "A professional has liked your job ad!";
    public static final String JOB_AD_MATCH_REQUEST_TEXT_CONTENT = "Hey %s, you have got a match request to your job ad by a %s.";
    public static final String JOB_AD_MATCH_REQUEST_HTML_CONTENT =
            "<h4 style='font-family: Arial, sans-serif; color: #333;'>Hello %s!</h4>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 14px; color: #555;'>This is an automatic response from <strong>Job-Match</strong> to let you know that you have received a match request!</p>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 14px; color: #555;'>Feel free to explore the job application and the professional who stands behind it.</p>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 14px; color: #555;'>Best regards,</p>" +
                    "<p style='font-family: Arial, sans-serif; font-size: 14px; color: #555;'><strong>Job-Match Team</strong></p>";

}
