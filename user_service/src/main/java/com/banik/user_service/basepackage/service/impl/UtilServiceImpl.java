package com.banik.user_service.basepackage.service.impl;

import com.banik.user_service.basepackage.exception.ApplicationException;
import com.banik.user_service.basepackage.response.ResponseCode;
import com.banik.user_service.basepackage.service.UtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
@Slf4j
public class UtilServiceImpl implements UtilService {
    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
//    private final AwsSesService awsSesService;
//    private final AwsS3Service awsS3Service;

    @Override
    public String makeSlug(String input) {
        String noWhiteSpace = WHITESPACE.matcher(input).replaceAll("_");
        String normalized = Normalizer.normalize(noWhiteSpace, Normalizer.Form.NFD);
        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    @Override
    public void checkImageFormat(MultipartFile image) {
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        if (!Objects.requireNonNull(image.getContentType()).contains("image/") &&
                !Objects.equals(Objects.requireNonNull(extension).toLowerCase(), "heic") &&
                !Objects.equals(extension.toLowerCase(), "heif")) {
            throw new ApplicationException(ResponseCode.BAD_REQUEST, "Uploaded file (" + image.getOriginalFilename() + ") is not an image.");
        }
    }

    @Override
    public void checkDocumentFormat(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!Objects.requireNonNull(file.getContentType()).contains("image/") &&
                !Objects.requireNonNull(file.getContentType()).contains("application/pdf") &&
                !Objects.equals(Objects.requireNonNull(extension).toLowerCase(), "heic") &&
                !Objects.equals(extension.toLowerCase(), "heif")) {
            throw new ApplicationException(ResponseCode.BAD_REQUEST, "Uploaded file (" + file.getOriginalFilename() + ") is not an image or pdf.");
        }
    }

    @Override
    public void checkDocumentFormatWithDocx(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!Objects.requireNonNull(file.getContentType()).contains("image/") &&
                !Objects.requireNonNull(file.getContentType()).contains("application/pdf") &&
                !Objects.requireNonNull(file.getContentType())
                        .contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document") &&
                !Objects.equals(Objects.requireNonNull(extension).toLowerCase(), "heic") &&
                !Objects.equals(extension.toLowerCase(), "heif")) {
            throw new ApplicationException(ResponseCode.BAD_REQUEST, "Uploaded file (" + file.getOriginalFilename() + ") is not an image or pdf.");
        }
    }

    @Override
    public void checkDocumentFormat(final List<MultipartFile> files) {
        for (MultipartFile file : files) {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (!Objects.requireNonNull(file.getContentType()).contains("image/") &&
                    !Objects.requireNonNull(file.getContentType()).contains("application/pdf") &&
                    !Objects.equals(Objects.requireNonNull(extension).toLowerCase(), "heic") &&
                    !Objects.equals(extension.toLowerCase(), "heif")) {
                throw new ApplicationException(ResponseCode.BAD_REQUEST,
                        "Uploaded file (" + file.getOriginalFilename() + ") is not an image or pdf.");
            }
        }
    }

    @Override
    public void sendEmail(String email, String subject, String htmlContent) {
        /*EmailRequestDTO emailRequest = new EmailRequestDTO();
        emailRequest.setTo(Set.of(email));
        emailRequest.setSubject(subject);
        emailRequest.setBody(htmlContent);

        try {
            awsSesService.sendEmail(emailRequest);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info(ex.toString());
            log.error("Unable to send email");
        }*/
    }

    @Override
    public void sendEmailWithAttachments(String email, String subject, String htmlContent, List<URL> urls, String folderKey) {
        /*EmailRequestDTO emailRequest = new EmailRequestDTO();
        emailRequest.setTo(Set.of(email));
        emailRequest.setSubject(subject);
        emailRequest.setBody(htmlContent);
        emailRequest.setAttachments(urls);
        try {
            awsSesService.sendEmail(emailRequest);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info(ex.toString());
            log.error("Unable to send email");
        }*/
    }

    @Override
    public URL uploadInputStream(InputStream inputStream, String folderKey) {
        String randomString = RandomString.make(4);
        String fileName = System.currentTimeMillis() + "-" + randomString + "-downloaded-file.xlsx";
        String uploadKey = folderKey + "/" + fileName;
        /*try {
            //upload images to S3
            awsS3Service.upload(inputStream, uploadKey);
            return awsS3Service.getURL(uploadKey);
        } catch (IOException e) {
            throw new ApplicationException(ResponseCode.BAD_REQUEST, "Unable to upload excel.");
        }*/

        return  null;
    }

    @Override
    public void sendEmailToMultipleUsers(Set<String> emails, String subject, String htmlContent) {
      /*  EmailRequestDTO emailRequest = new EmailRequestDTO();
        emailRequest.setTo(emails);
        emailRequest.setSubject(subject);
        emailRequest.setBody(htmlContent);*/

        try {
            //awsSesService.sendEmail(emailRequest);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info(ex.toString());
            log.error("Unable to send email");
        }
    }


    public void uploadImagesToS3(Map<String, MultipartFile> uploadImages) {
        try {
            for (Map.Entry<String, MultipartFile> uploadImage : uploadImages.entrySet()) {
                String uploadKey = uploadImage.getKey();
                InputStream imageInputStream = uploadImage.getValue().getInputStream();
               // awsS3Service.upload(imageInputStream, uploadKey);
            }
        } catch (IOException e) {
            throw new ApplicationException(ResponseCode.INTERNAL_ERROR, "Unable to upload images to S3");
        }
    }


    public URL uploadImage(MultipartFile image, String folderKey) {
        if (image != null) {
            String randomString = RandomString.make(4);
            String fileName = System.currentTimeMillis() + "-" + randomString + "-" + image.getOriginalFilename();
            String uploadKey = folderKey + "/" + fileName;
            Map<String, MultipartFile> uploadImage = new HashMap<>();
            uploadImage.put(uploadKey, image);

            //upload images to S3
            uploadImagesToS3(uploadImage);

            return null;// awsS3Service.getURL(uploadKey);
        }
        return null;
    }


}
