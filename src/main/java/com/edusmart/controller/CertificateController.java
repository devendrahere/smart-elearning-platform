package com.edusmart.controller;


import com.edusmart.dto.CertificateDTO;
import com.edusmart.service.CertificateService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {
    @Autowired
    private CertificateService certificateService;

    @PostMapping("/generate")
    public ResponseEntity<CertificateDTO> generateCertificate(
            @RequestParam @NotNull Long userId,
            @RequestParam @NotNull Long courseId
    ){
        CertificateDTO dto = certificateService.generateCertificate(userId,courseId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @GetMapping("/user/{userID}")
    public ResponseEntity<List<CertificateDTO>> getUserCertificates(
            @PathVariable Long userId
    ){
        List<CertificateDTO> certificates=certificateService.getUsersCertificates(userId);
        return ResponseEntity.ok(certificates);
    }


    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<CertificateDTO>> getCourseCertificates(
            @PathVariable Long courseID
    ){
        List<CertificateDTO> certificates=certificateService.getCourseCertificates(courseID);
        return ResponseEntity.ok(certificates);
    }

}
