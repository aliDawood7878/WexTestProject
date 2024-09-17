package com.wex.testProject.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wex.testProject.model.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogAnalysisService {

    @Autowired
    private ObjectMapper objectMapper;

    public List<Integer> findLoyalCustomers() throws IOException {
        List<LogEntry> day1Logs = parseLogs("day_1.json");
        List<LogEntry> day2Logs = parseLogs("day_2.json");

        Set<Integer> day1Users = new HashSet<>();
        Map<Integer, Set<Integer>> userPages = new HashMap<>();

        for(LogEntry log: day1Logs){
            day1Users.add(log.getCustomerId());
            userPages.computeIfAbsent(log.getCustomerId(), k -> new HashSet<>()).add(log.getPageId());

        }

        List<Integer> loyalCustomers = new ArrayList<>();
        for(LogEntry log : day2Logs){
            if(day1Users.contains(log.getCustomerId())){
                Set<Integer> pages = userPages.get(log.getCustomerId());
                pages.add(log.getPageId());
                if(pages.size() >=2){
                    loyalCustomers.add(log.getCustomerId());
                }
            }
        }
        return loyalCustomers.stream().distinct().collect(Collectors.toList());

    }

    private List<LogEntry> parseLogs(String fileName) throws IOException {

        return Arrays.asList(objectMapper.readValue(new ClassPathResource(fileName).getFile(),LogEntry[].class));
    }
}
