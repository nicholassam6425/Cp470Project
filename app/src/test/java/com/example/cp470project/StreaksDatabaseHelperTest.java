package com.example.cp470project;

import junit.framework.TestCase;

public class StreaksDatabaseHelperTest extends TestCase {
    public void testVars(){
        assertEquals(StreaksDatabaseHelper.ACTIVITY_NAME,"StreaksDatabaseHelper");
        assertEquals(StreaksDatabaseHelper.KEY_STREAK_DESC,"streak_desc");
        assertEquals(StreaksDatabaseHelper.KEY_ID,"_id");
        assertEquals(StreaksDatabaseHelper.KEY_STREAK_TIME,"streak_time");
        assertEquals(StreaksDatabaseHelper.TABLE_NAME,"tableOfBoardMessages");
    }
}