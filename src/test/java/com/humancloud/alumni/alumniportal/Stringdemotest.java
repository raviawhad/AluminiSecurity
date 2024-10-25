package com.humancloud.alumni.alumniportal;

import com.humancloud.alumni.alumniportal.controller.Stringdemo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Stringdemotest {
    @Test
    public void testEmptyString() {
        assertEquals(0, Stringdemo.add(""));
    }
    @Test
    public void testSingleNumber() {
        assertEquals(1, Stringdemo.add("1"));
    }

    @Test
    public void testTwoNumbers() {
        assertEquals(3, Stringdemo.add("1,2"));
    }


    @Test
    public void testMultipleNumbers() {
        assertEquals(10, Stringdemo.add("1,2,3,4"));
    }
}
