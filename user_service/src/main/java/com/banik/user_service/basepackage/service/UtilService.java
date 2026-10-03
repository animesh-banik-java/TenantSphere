package com.banik.user_service.basepackage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UtilService {

	void sendEmail(String email, String subject, String htmlContent);
	void sendEmailWithAttachments(String email, String subject, String htmlContent, List<URL> url, String folderKey);

	void sendEmailToMultipleUsers(Set<String> emails, String subject, String htmlContent);

	URL uploadInputStream(InputStream inputStream, String folderKey);

	URL uploadImage(MultipartFile image, String folderKey);

	String makeSlug(final String input);

	void checkImageFormat(final MultipartFile image);

	void checkDocumentFormat(final MultipartFile file);
	void checkDocumentFormatWithDocx(MultipartFile file);

	void checkDocumentFormat(final List<MultipartFile> files);

	void uploadImagesToS3(Map<String, MultipartFile> uploadKeys);
}
