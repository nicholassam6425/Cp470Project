package com.example.cp470project;

import junit.framework.TestCase;

public class MessageBoardDatabaseHelperTest extends TestCase {
    public void testVars(){
        assertEquals(MessageBoardDatabaseHelper.ACTIVITY_NAME,"MessageBoardDatabaseHelper");
        assertEquals(MessageBoardDatabaseHelper.KEY_MESSAGE,"board_message");
        assertEquals(MessageBoardDatabaseHelper.DATABASE_NAME,"messageboard.db");
        assertEquals(MessageBoardDatabaseHelper.KEY_ID,"_id");
        assertEquals(MessageBoardDatabaseHelper.KEY_USERNAME,"username");
        assertEquals(MessageBoardDatabaseHelper.TABLE_NAME,"tableOfBoardMessages");
    }
}