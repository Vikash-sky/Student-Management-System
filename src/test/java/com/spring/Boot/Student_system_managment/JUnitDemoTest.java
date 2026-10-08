package com.spring.Boot.Student_system_managment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JUnitDemoTest {
    @Test
    void shouldReturnExpectedResult(){
        Long expected = 5L;
        Long actual = 5L;
        assertEquals(expected,actual);
    }



}
