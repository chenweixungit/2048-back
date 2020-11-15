package com.axun.game;

import com.axun.game.dao.UserDOMapper;
import com.axun.game.dataObjects.UserDO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GameApplicationTests {
    @Autowired
    UserDOMapper userDOMapper;
    @Test
    void contextLoads() {
        UserDO userDO = userDOMapper.selectByTelephone("15056928950");
        System.out.println(userDO.toString());
    }

}
