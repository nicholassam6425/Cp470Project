package com.example.cp470project;

import junit.framework.TestCase;

public class ChecklistDatabaseHelperTest extends TestCase {
    public void testConstants(){
        assertEquals(ChecklistDatabaseHelper.DATABASE_NAME, "Messages.db");
        assertEquals(ChecklistDatabaseHelper.checked_name, "checked");
        assertEquals(ChecklistDatabaseHelper.objective_value,"objvalue");
        assertEquals(ChecklistDatabaseHelper.table_name,"myTable");
        assertEquals(ChecklistDatabaseHelper.id,"_id");
        assertNotNull(ChecklistDatabaseHelper.VERSION_NUM);
    }
}