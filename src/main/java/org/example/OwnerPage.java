package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class OwnerPage {

    private int ownerId;
    private Connection con;
    private PreparedStatement prep_stmt = null;
    private Statement stmt = null;


    public OwnerPage(int ownerId, Connection con) {
        this.ownerId = ownerId;
        this.con = con;
    }

}
