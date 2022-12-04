package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RiderPage {

    private int riderId;
    Connection con;
    PreparedStatement prep_stmt = null;
    Statement stmt = null;


    public RiderPage(int riderId, Connection con) {
        this.riderId = riderId;
        this.con = con;
    }

}
