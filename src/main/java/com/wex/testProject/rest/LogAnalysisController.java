package com.wex.testProject.rest;

import com.wex.testProject.service.LogAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LogAnalysisController {
    @Autowired
    private LogAnalysisService logAnalysisService;

    @GetMapping("/loyalCustomers")
    public ResponseEntity<List<Integer>> getLoyalCustomers() throws IOException {
        List<Integer> loyalCustomers = logAnalysisService.findLoyalCustomers();
        return ResponseEntity.ok(loyalCustomers);
    }
}
