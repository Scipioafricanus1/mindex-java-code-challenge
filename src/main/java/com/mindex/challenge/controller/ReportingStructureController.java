package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);
    
    @Autowired
    private ReportingService reportingService;

    @GetMapping("reporting/{id}")
    public ReportingStructure gReportingStructure(@PathVariable String id) {
        LOG.debug("Received request for Reporting structure for employee id [{}]", id);

        return reportingService.getReportingStructure(id);
    }
    

}
