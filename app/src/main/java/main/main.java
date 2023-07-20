
package main;

import java.sql.Connection;
import util.ConnectionFactory;

public class main {

    public static void main(String[] args) {
      
        Connection c = ConnectionFactory.getConnection();
        
        ConnectionFactory.closeConnection(c);
    }
}
