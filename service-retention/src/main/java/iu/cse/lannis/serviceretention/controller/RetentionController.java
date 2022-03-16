package iu.cse.lannis.serviceretention.controller;

import iu.cse.lannis.serviceretention.dto.RetentionRequestDto;
import iu.cse.lannis.serviceretention.dto.RetentionResponse;
import iu.cse.lannis.serviceretention.dto.RetentionResponseV2;
import iu.cse.lannis.serviceretention.entity.Retention;
import iu.cse.lannis.serviceretention.service.RetentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@CrossOrigin("*")
public class RetentionController {
    private final RetentionService retentionService;

    @Autowired
    public RetentionController(RetentionService retentionService) {
        this.retentionService = retentionService;
    }

    @GetMapping(value = "/retentions", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Retention> getAllRetentions() {
        return this.retentionService.getAllRetentions();
    }

    @GetMapping(value = "/retentions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Retention> getOneRetention(@PathVariable("id") Long retentionId) {
        var retention = this.retentionService.getRetentionById(retentionId);
        return ResponseEntity.status(HttpStatus.OK).body(retention);
    }

    @PostMapping(value = "/retentions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RetentionResponse> createRetention(@RequestBody RetentionRequestDto retentionRequestDto) {
        this.retentionService.createRetention(retentionRequestDto);
        var retentionResponse = new RetentionResponse(HttpStatus.CREATED, "Retention request has been made");
        return ResponseEntity.status(HttpStatus.CREATED).body(retentionResponse);
    }

    @PostMapping( value = "/retentions/v2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RetentionResponseV2> createRetentionV2(@RequestBody RetentionRequestDto retentionRequestDto) {
        var retention = this.retentionService.createRetentionVer2(retentionRequestDto);
        var response = new RetentionResponseV2(HttpStatus.CREATED, "Successfully created retention", retention);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
