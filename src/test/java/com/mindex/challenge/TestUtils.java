package com.mindex.challenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mindex.challenge.data.Employee;

public class TestUtils {
    public static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
