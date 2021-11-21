package iu.cse.lannis.serviceretention.controller;

import iu.cse.lannis.serviceretention.dto.StudentRetentionDto;
import iu.cse.lannis.serviceretention.entity.Retention;
import iu.cse.lannis.serviceretention.service.RetentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@RestController
@RequestMapping("retentions")
public class RetentionController {
    private final RetentionService retentionService;

    @Autowired
    public RetentionController(RetentionService retentionService) {
        this.retentionService = retentionService;
    }

    @GetMapping()
    public List<Retention> getAllRetentions() {
        return this.retentionService.getAllRetentions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Retention> getOneRetention(@PathVariable("id") Long retentionId) {
        Retention retention = this.retentionService.getRetentionById(retentionId);
        return ResponseEntity.status(HttpStatus.OK).body(retention);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Retention> createRetention(@RequestBody StudentRetentionDto studentRetentionDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.retentionService.createRetention(studentRetentionDto));
    }
}
