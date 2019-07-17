package by.pvt.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
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
                    Resources.getResourceAsStream("service/mybatis-config.xml")
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
        new SystemUsersService().delete(2);
    }

    public List<SystemUsers> getSystemUsers() {
        return sqlSessionFactory
                .openSession()
                .getMapper(SystemUsersMapper.class)
                .selectByExample(null);
    }

    public  void add(SystemUsers systemUser) {
        SqlSession sqlSession =
                sqlSessionFactory.openSession();

            SystemUsersMapper systemUsersMapper =
                    sqlSession.getMapper(SystemUsersMapper.class);
            systemUsersMapper.insert(systemUser);
            sqlSession.commit();

            sqlSession.close();
    }
    public void update(SystemUsers systemUser) {
        SqlSession sqlSession =
                sqlSessionFactory.openSession();

            SystemUsersMapper systemUsersMapper =
                    sqlSession.getMapper(SystemUsersMapper.class);
            systemUsersMapper.updateByPrimaryKey(systemUser);
            sqlSession.commit();

            sqlSession.close();
    }

    public void delete(int id) {
        SqlSession sqlSession =
                sqlSessionFactory.openSession();

            SystemUsersMapper systemUsersMapper =
                    sqlSession.getMapper(SystemUsersMapper.class);
            systemUsersMapper.deleteByPrimaryKey(id);
            sqlSession.commit();

            sqlSession.close();
    }
}

