package by.it.pvt;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.*;
public class HelloMysqlTest {
    @Test
    public void testConnection() {

        try {
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:2016/hello_mysql", "root", "root");

            PreparedStatement ps = connection.prepareStatement("select * from system_users");

            ResultSet rs = ps.executeQuery();

            assertNotNull(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
