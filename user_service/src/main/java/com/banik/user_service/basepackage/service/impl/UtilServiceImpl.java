package com.banik.user_service.basepackage.service.impl;

import com.banik.user_service.basepackage.exception.ApplicationException;
import com.banik.user_service.basepackage.service.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.Normalizer;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
@Slf4j
public class UtilServiceImpl implements UtilService {
    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public String makeSlug(String input) {
        String noWhiteSpace = WHITESPACE.matcher(input).replaceAll("_");
        String normalized = Normalizer.normalize(noWhiteSpace, Normalizer.Form.NFD);
        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }


    public void checkImageFormat(MultipartFile image) {
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        if (!Objects.requireNonNull(image.getContentType()).contains("image/") &&
                !Objects.equals(Objects.requireNonNull(extension).toLowerCase(), "heic") &&
                !Objects.equals(extension.toLowerCase(), "heif")) {
            throw new ApplicationException("Uploaded file (" + image.getOriginalFilename() + ") is not an image.");
        }
    }

    @Override
    public void checkDocumentFormat(MultipartFile image) {
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        if (!Objects.requireNonNull(image.getContentType()).contains("image/") &&
                !Objects.requireNonNull(image.getContentType()).contains("application/pdf") &&
                !Objects.equals(Objects.requireNonNull(extension).toLowerCase(), "heic") &&
                !Objects.equals(extension.toLowerCase(), "heif")) {
            throw new ApplicationException("Uploaded file (" + image.getOriginalFilename() + ") is not an image or pdf.");
        }
    }


}
