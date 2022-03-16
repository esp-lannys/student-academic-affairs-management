package iu.cse.lannis.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class GatewayController {
    @GetMapping
    public ResponseEntity<Object> fallback() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        map.put("message", "You have encountered the fallback URI");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
//        return "You have encountered the fallback URL";
    }
}
