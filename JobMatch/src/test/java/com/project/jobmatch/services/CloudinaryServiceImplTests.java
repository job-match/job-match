package com.project.jobmatch.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class CloudinaryServiceImplTests {

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private CloudinaryServiceImpl cloudinaryService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        lenient().when(cloudinary.uploader()).thenReturn(uploader); // Use lenient stubbing
        cloudinaryService = new CloudinaryServiceImpl(cloudinary);
    }

    @Test
    public void testUpload_Success() throws Exception {
        File tempFile = File.createTempFile("temp-file", ".jpg");
        when(multipartFile.getOriginalFilename()).thenReturn("test.jpg");
        doAnswer(invocation -> {
            tempFile.createNewFile();
            return null;
        }).when(multipartFile).transferTo(any(File.class)); // Use any(File.class)

        Map<String, String> uploadResult = new HashMap<>();
        uploadResult.put("url", "http://example.com/image.jpg");
        uploadResult.put("public_id", "sample_public_id");

        doReturn(uploadResult).when(uploader).upload(any(File.class), eq(ObjectUtils.emptyMap())); // Use doReturn

        CloudinaryImage cloudinaryImage = cloudinaryService.upload(multipartFile);

        assertNotNull(cloudinaryImage);
        assertEquals("http://example.com/image.jpg", cloudinaryImage.getUrl());
        assertEquals("sample_public_id", cloudinaryImage.getPublicId());

        verify(multipartFile, times(1)).transferTo(any(File.class));
        verify(uploader, times(1)).upload(any(File.class), eq(ObjectUtils.emptyMap()));

        assertTrue(tempFile.delete());
    }

    @Test
    public void testUpload_ThrowsIOException_WhenTransferFails() throws Exception {
        // Arrange
        when(multipartFile.getOriginalFilename()).thenReturn("test.jpg");
        doThrow(new IOException("File transfer failed")).when(multipartFile).transferTo(any(File.class));

        // Act & Assert
        IOException thrown = assertThrows(IOException.class, () -> cloudinaryService.upload(multipartFile));
        assertEquals("File transfer failed", thrown.getMessage());

        // Verify
        verify(multipartFile, times(1)).transferTo(any(File.class));
        verify(uploader, never()).upload(any(File.class), anyMap());
    }


}
