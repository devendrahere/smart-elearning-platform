package com.edusmart.service.implemeted;

import com.edusmart.dto.CertificateDTO;
import com.edusmart.entity.Certificate;
import com.edusmart.entity.Courses;
import com.edusmart.entity.Users;
import com.edusmart.exception.ResourcesNotFound;
import com.edusmart.repository.CertificateRepository;
import com.edusmart.repository.CourseRepository;
import com.edusmart.repository.UserRepository;
import com.edusmart.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImple implements CertificateService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    @Override
    public CertificateDTO generateCertificate(Long userId, Long courseId) {
        Users user=userRepository.findById(userId)
                .orElseThrow(()->new ResourcesNotFound("user not found with id: "+userId));
        Courses course=courseRepository.findById(courseId)
                .orElseThrow(()->new ResourcesNotFound("Course not found with id: "+courseId));

        boolean exists=certificateRepository.existsByUserUserIdAndCourseCourseId(userId,courseId);
        if(exists){
            throw new IllegalStateException("Certificate with user and course already exists");
        }
        Certificate certificate=new Certificate();
        certificate.setCourse(course);
        certificate.setUser(user);
        certificate.setCertificateCode(UUID.randomUUID().toString());
        certificate.setIssueDate(LocalDateTime.now());
        certificate.setPdfURL("N/A");

        Certificate saved=certificateRepository.save(certificate);
        return mapToDTO(saved);
    }

    @Override
    public List<CertificateDTO> getUsersCertificates(Long userId) {
        return certificateRepository.findByUserUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CertificateDTO verifyCertificate(String certificateCode) {
        Certificate certificate=certificateRepository.findByCertificateCode(certificateCode)
                .orElseThrow(()-> new ResourcesNotFound("Invalid certificate code: "+certificateCode));
        return mapToDTO(certificate);
    }

    @Override
    public List<CertificateDTO> getCourseCertificates(Long courseId) {
        return certificateRepository.findByCourse_CourseId(courseId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //mapper helper
    private CertificateDTO mapToDTO(Certificate entity){
        CertificateDTO dto=new CertificateDTO();
        dto.setCertificateId(entity.getCertificateId());
        dto.setCourseId(entity.getCourse().getCourseId());
        dto.setUserId(entity.getUser().getUserId());
        dto.setCourseTitle(entity.getCourse().getCourseTitle());
        dto.setCertificateCode(entity.getCertificateCode());
        dto.setIssueDate(entity.getIssueDate());
        dto.setPdfURl(entity.getPdfURL());
        return dto;
    }
}
