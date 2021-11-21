package iu.cse.lannis.serviceretention.service;

import iu.cse.lannis.serviceretention.dto.StudentRetentionDto;
import iu.cse.lannis.serviceretention.entity.Retention;
import iu.cse.lannis.serviceretention.exception.ServiceRetentionException;
import iu.cse.lannis.serviceretention.repository.RetentionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RetentionService {
    private final RetentionRepository retentionRepository;
    @Autowired
    public RetentionService(RetentionRepository retentionRepository) {
        this.retentionRepository = retentionRepository;
    }

    public List<Retention> getAllRetentions() {
        return this.retentionRepository.findAll();
    }

    public Retention getRetentionById(Long retentionId) {
        return this.retentionRepository.findById(retentionId)
                .orElseThrow(() -> new ServiceRetentionException("Retention Not found", HttpStatus.NOT_FOUND));
    }

    public Retention createRetention(StudentRetentionDto studentRetentionDto) {
        Retention newRetention = new Retention();
        newRetention.setNote(studentRetentionDto.getNote());
        newRetention.setStudentId(studentRetentionDto.getStudentId());
        newRetention.setStudentName(studentRetentionDto.getStudentName());
        newRetention.setReason(studentRetentionDto.getReason());
        this.retentionRepository.save(newRetention);
        return newRetention;
    }
}
