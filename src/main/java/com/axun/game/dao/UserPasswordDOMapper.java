package com.axun.game.dao;

import com.axun.game.dataObjects.UserPasswordDO;
import org.springframework.stereotype.Component;

@Component
public interface UserPasswordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPasswordDO record);

    int insertSelective(UserPasswordDO record);

    UserPasswordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPasswordDO record);

    int updateByPrimaryKey(UserPasswordDO record);

    UserPasswordDO selectByUserId(Integer id);
}