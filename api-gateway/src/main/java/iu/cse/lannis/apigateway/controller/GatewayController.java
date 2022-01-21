package iu.cse.lannis.apigateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class GatewayController {
    @GetMapping
    public String fallback() {
        return "You have encountered the fallback URL";
    }
}
