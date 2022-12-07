package com.example.cp470project;

import junit.framework.TestCase;

public class LoginActivityTest extends TestCase {
    public  void testConstants(){
        assertEquals(LoginActivity.ACTIVITY_NAME,"LoginActivity");
        assertNotNull(LoginActivity.username);
    }
}