package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;


import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;

public class ReportingServiceImplTest {
    
    @InjectMocks
    private ReportingServiceImpl reportingService;

    @Mock
    EmployeeService employeeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateReportStructureWithUnderlings() {
        Employee employee = createEmployeeWith4Underlings();
        Mockito.when(employeeService.read(anyString())).thenReturn(employee);
        
        assertEquals(5, reportingService.getReportingStructure("111").getNumberOfReports());
    }

    @Test
    public void testCreateReportStructureWithNoUnderlings() {
        Employee employee = createEmployeeWithNoUnderlings();
        Mockito.when(employeeService.read(anyString())).thenReturn(employee);
        ReportingStructure reportingStructure = reportingService.getReportingStructure("111");
        assertEquals(0, reportingStructure.getNumberOfReports());
        assertEquals("111", reportingStructure.getEmployee().getEmployeeId());
    }

    @Test( expected = RuntimeException.class)
    public void testGetNoEmployees() {
        Mockito.when(employeeService.read(anyString())).thenReturn(null);
        ReportingStructure reportingStructure = reportingService.getReportingStructure("111");
    }

    private Employee createEmployeeWith4Underlings() {
        Employee employee = new Employee();
        employee.setEmployeeId("111");

        Employee employee2 = new Employee();
        employee2.setEmployeeId("222");

        Employee employee2Child = new Employee();
        Employee employee2Child2 = new Employee();
        Employee emp5OfChild2 = new Employee();
        employee2Child2.setDirectReports(Lists.newArrayList(emp5OfChild2));
        employee2Child.setDirectReports(Lists.newArrayList());
        employee2.setDirectReports(Lists.newArrayList(employee2Child, employee2Child2));

        Employee employee3 = new Employee();
        employee3.setEmployeeId("333");
        employee.setDirectReports(Lists.newArrayList(employee2, employee3));
        return employee;
    }

    private Employee createEmployeeWithNoUnderlings() {
        Employee employee = new Employee();
        employee.setEmployeeId("111");
        return employee;
    }
}
