package com.banik.user_service.basepackage.service;

import org.springframework.web.multipart.MultipartFile;

public interface UtilService {

    String makeSlug(final String input);

    void checkImageFormat(final MultipartFile image);

    void checkDocumentFormat(final MultipartFile image);


}
