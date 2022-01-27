package iu.cse.lannis.serviceretention.controller;

import iu.cse.lannis.serviceretention.dto.RetentionRequestDto;
import iu.cse.lannis.serviceretention.dto.RetentionResponse;
import iu.cse.lannis.serviceretention.entity.Retention;
import iu.cse.lannis.serviceretention.service.RetentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RefreshScope
@RestController
@RequestMapping("retentions")
@CrossOrigin("*")
public class RetentionController {
    private final RetentionService retentionService;

    @Autowired
    public RetentionController(RetentionService retentionService) {
        this.retentionService = retentionService;
    }

    @GetMapping
    public List<Retention> getAllRetentions() {
        return this.retentionService.getAllRetentions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Retention> getOneRetention(@PathVariable("id") Long retentionId) {
        var retention = this.retentionService.getRetentionById(retentionId);
        return ResponseEntity.status(HttpStatus.OK).body(retention);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RetentionResponse> createRetention(@RequestBody RetentionRequestDto retentionRequestDto) {
        this.retentionService.createRetention(retentionRequestDto);
        RetentionResponse retentionResponse = new RetentionResponse(HttpStatus.CREATED, "Retention request has been made");
        return ResponseEntity.status(HttpStatus.CREATED).body(retentionResponse);
    }
}
