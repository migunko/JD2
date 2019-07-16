package by.pvt.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import by.pvt.dao.SystemUsersMapper;
import by.pvt.dto.SystemUsers;

public class SystemUsersService {

    private static Logger log = Logger.getLogger(SystemUsersService.class.getName());

    private SqlSessionFactory sqlSessionFactory;

    public SystemUsersService() {
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(
                    Resources.getResourceAsStream("by/pvt/service/mybatis-config.xml")
            );
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public static void main(String[] args) {
        SystemUsers systemUsers = new SystemUsers();
        systemUsers.setId(2);
        systemUsers.setUsername("User2");
        systemUsers.setActive(false);
        systemUsers.setDateofbirth(new Date());

        new SystemUsersService().add(systemUsers);

        new SystemUsersService()
                .getSystemUsers()
                .forEach(user ->
                        log.info(user.getId() + " "
                                + user.getUsername() + " "
                                + user.getActive()));
    }

    public List<SystemUsers> getSystemUsers() {
        return sqlSessionFactory
                .openSession()
                .getMapper(SystemUsersMapper.class)
                .selectByExample(null);
    }

    public void add(SystemUsers systemUser) {
        int result = sqlSessionFactory
                .openSession()
                .getMapper(SystemUsersMapper.class)
                .insert(systemUser);

        log.info("Added new systemUser with result=" + result);
    }
}
