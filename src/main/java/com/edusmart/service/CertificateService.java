package com.edusmart.service;

import com.edusmart.dto.CertificateDTO;

import java.util.List;

public interface CertificateService {
    CertificateDTO generateCertificate(Long userId,Long courseId);
    List<CertificateDTO> getUsersCertificates(Long userId);
    CertificateDTO verifyCertificate(String certificateCode);
    List<CertificateDTO> getCourseCertificates(Long courseId);
}
