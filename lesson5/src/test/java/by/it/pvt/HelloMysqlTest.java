package by.it.pvt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import by.pvt.dto.SystemUsers;
import by.pvt.service.SystemUsersService;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.*;
public class HelloMysqlTest extends DBTestCase  {
    public HelloMysqlTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:2016/hello_mysql_junit");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "root");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(HelloMysqlTest.class.getResourceAsStream("system_users.xml"));
    }

    @Test
    public void testConnection() {

        try (Connection connection =
                     DriverManager
                             .getConnection("jdbc:mysql://localhost:2016/hello_mysql_junit", "root", "root");
             PreparedStatement ps = connection.prepareStatement("select * from system_users");
        ) {
            ResultSet rs = ps.executeQuery();
            assertNotNull(rs);

            int rawCount = 0;
            int activeUser = 0;

            while (rs.next()) {
                rawCount++;
                if (rs.getBoolean("active")) activeUser++;
            }
            assertEquals(4, rawCount);
            assertEquals(2, activeUser);

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testCreate() {
        try (Connection connection =
                     DriverManager
                             .getConnection("jdbc:mysql://localhost:2016/hello_mysql", "root", "root");
             PreparedStatement ps = connection.prepareStatement("select * from system_users where id=3");

        ) {
            SystemUsers systemUser = new SystemUsers();
            systemUser.setId(3);
            systemUser.setUsername("user3");
            systemUser.setActive(false);
            systemUser.setDateofbirth(new Date());
            new SystemUsersService().add(systemUser);
            ResultSet rs = ps.executeQuery();

            int id = 0;
            String username = "";
            boolean active = true;

            while (rs.next()) {
                id = rs.getInt("id");
                username = rs.getString("username");
                active = rs.getBoolean("active");
            }

            assertEquals(3, id);
            assertEquals("user3", username);
            assertEquals(false, active);

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testUpdate() {
        try (Connection connection =
                     DriverManager
                             .getConnection("jdbc:mysql://localhost:2016/hello_mysql", "root", "root");
             PreparedStatement ps = connection.prepareStatement("select * from system_users where id=3");

        ) {
            SystemUsers systemUser = new SystemUsers();
            systemUser.setId(3);
            systemUser.setUsername("update");
            systemUser.setActive(false);
            systemUser.setDateofbirth(new Date());
            new SystemUsersService().update(systemUser);
            ResultSet rs = ps.executeQuery();

            int id = 0;
            String username = "";
            boolean active = true;

            while (rs.next()) {
                id = rs.getInt("id");
                username = rs.getString("username");
                active = rs.getBoolean("active");
            }

            assertEquals(3, id);
            assertEquals("update", username);
            assertEquals(false, active);

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
