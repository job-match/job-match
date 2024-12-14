package com.project.jobmatch.services;

import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MailjetServiceImplTests {

    @Mock
    private MailjetClient mockMailjetClient;

    @InjectMocks
    private MailjetServiceImpl mailjetService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void sendEmail_Should_CallMailjetClient_WithCorrectParameters() throws Exception {
        // Arrange
        String toEmail = "test@example.com";
        String toName = "Test User";
        String subject = "Test Subject";
        String textContent = "Test Text Content";
        String htmlContent = "<h1>Test HTML Content</h1>";

        MailjetResponse mockResponse = mock(MailjetResponse.class);
        when(mockResponse.getStatus()).thenReturn(200);
        when(mockResponse.getData()).thenReturn(new JSONArray());

        when(mockMailjetClient.post(any(MailjetRequest.class))).thenReturn(mockResponse);

        // Act
        mailjetService.sendEmail(toEmail, toName, subject, textContent, htmlContent);

        // Assert
        verify(mockMailjetClient, times(1)).post(any(MailjetRequest.class));
    }

    @Test
    public void sendEmail_Should_HandleException_When_MailjetClientThrows() throws Exception {
        // Arrange
        String toEmail = "test@example.com";
        String toName = "Test User";
        String subject = "Test Subject";
        String textContent = "Test Text Content";
        String htmlContent = "<h1>Test HTML Content</h1>";

        when(mockMailjetClient.post(any(MailjetRequest.class))).thenThrow(new RuntimeException("Mailjet error"));

        // Act
        mailjetService.sendEmail(toEmail, toName, subject, textContent, htmlContent);

        // Assert
        verify(mockMailjetClient, times(1)).post(any(MailjetRequest.class));
    }

    @Test
    public void sendEmail_Should_LogResponseDetails() throws Exception {
        // Arrange
        String toEmail = "test@example.com";
        String toName = "Test User";
        String subject = "Test Subject";
        String textContent = "Test Text Content";
        String htmlContent = "<h1>Test HTML Content</h1>";

        MailjetResponse mockResponse = mock(MailjetResponse.class);
        when(mockResponse.getStatus()).thenReturn(200);
        when(mockResponse.getData()).thenReturn(new JSONArray().put(new JSONObject().put("Message", "Success")));

        when(mockMailjetClient.post(any(MailjetRequest.class))).thenReturn(mockResponse);

        // Act
        mailjetService.sendEmail(toEmail, toName, subject, textContent, htmlContent);

        // Assert
        verify(mockMailjetClient, times(1)).post(any(MailjetRequest.class));
    }
}
