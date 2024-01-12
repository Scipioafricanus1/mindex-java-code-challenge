package com.mindex.challenge.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingService;

@Service
public class ReportingServiceImpl implements ReportingService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;
    

    @Override
    public ReportingStructure getReportingStructure(String id) {
        LOG.debug("Getting reportingStructure by Emplyee id [{}]", id);
        
        Employee employee = employeeService.read(id);
        if ( employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        return createReportingStructure(employee);
    }
    /***
     * Digs through employee's direct reports 
     * to determine count of all reports under the main parent
     * Then creates report with fields:
     * EMPLOYEE
     * NUM_OF_REPORTS
     * @param employee
     * @return ReportingStructure 
     */
    private ReportingStructure createReportingStructure(Employee employee) {
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);
        if ( employee.getDirectReports() == null || employee.getDirectReports().isEmpty()) {
            reportingStructure.setNumberOfReports(0);
        } else {
            reportingStructure.setNumberOfReports(addUpReports(employee.getDirectReports()));
        }
        
        return reportingStructure;
    }

    /***
     * iterates through each direct Report list resursively. 
     * @param employees
     * @return number of reports total under the parent.
     */
    private int addUpReports(List<Employee> employees) {
        
        int i = 0;
        for ( Employee employee : employees) {
            //dig down.
            if ( employee.getDirectReports() != null && !employee.getDirectReports().isEmpty()) {
                i += addUpReports(employee.getDirectReports());
            }
            // don't skip mid-loop people
            i++;
        }
        return i;
    }
}
