package com.example.cp470project;

import junit.framework.TestCase;

public class ChatDatabaseHelperTest extends TestCase {
    public void testConstants(){
        assertEquals(ChatDatabaseHelper.ACTIVITY_NAME,"ChatDatabaseHelper");
        assertEquals(ChatDatabaseHelper.KEY_CAPTION, "caption");
        assertEquals(ChatDatabaseHelper.KEY_TIME, "time");
        assertEquals(ChatDatabaseHelper.TABLE_NAME, "accs");
        assertNotNull(ChatDatabaseHelper.VERSION_NUM);
        assertEquals(ChatDatabaseHelper.DATABASE_NAME,"Accomplishments.db");
    }
}