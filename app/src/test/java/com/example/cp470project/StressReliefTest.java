package com.example.cp470project;

import junit.framework.TestCase;

public class StressReliefTest extends TestCase {
    public void testVars(){
        assertEquals(StressRelief.ACTIVITY_NAME,"StressRelief");
        assertEquals(StressRelief.starttimeinms,5999);
    }
}