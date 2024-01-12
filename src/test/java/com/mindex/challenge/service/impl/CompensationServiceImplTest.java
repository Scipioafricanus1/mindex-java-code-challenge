package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.TestUtils;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {


    private String compensationUrl;
    private String compenSationIdUrl;

    @Autowired
    private CompensationService compensationService;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compenSationIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreateRead() {

        Employee testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
        testEmployee.setEmployeeId("111");
        Compensation compensation = new Compensation();
        Date date = new Date();
        compensation.setEffectiveDate(date);
        compensation.setEmployee(testEmployee);
        compensation.setSalary(1000000);

        Employee testEmployee2 = new Employee();
        testEmployee2.setFirstName("Jill");
        testEmployee2.setLastName("Dont");
        testEmployee2.setDepartment("Software");
        testEmployee2.setPosition("Engineering");
        testEmployee2.setEmployeeId("222");
        Compensation compensation2 = new Compensation();
        Date date2 = new Date();
        compensation.setEffectiveDate(date2);
        compensation.setEmployee(testEmployee);
        compensation.setSalary(500);
        //Create checks
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, compensation, Compensation.class).getBody();
        Compensation createdCompensation2 = restTemplate.postForEntity(compensationUrl, compensation2, Compensation.class).getBody();
        assertNotNull(createdCompensation2);
        assertNotNull(createdCompensation);
        TestUtils.assertEmployeeEquivalence(testEmployee, createdCompensation.getEmployee());

        //Read Check
        Compensation readCompensation = restTemplate.getForEntity(compenSationIdUrl, Compensation.class, createdCompensation.getEmployee().getEmployeeId()).getBody();
        assertEquals(createdCompensation.getEmployee().getEmployeeId(), readCompensation.getEmployee().getEmployeeId());
        TestUtils.assertEmployeeEquivalence(createdCompensation.getEmployee(), readCompensation.getEmployee());

        assertEquals(createdCompensation.getEffectiveDate(), readCompensation.getEffectiveDate());
        assertEquals(createdCompensation.getSalary(), readCompensation.getSalary());
    }
    
}
