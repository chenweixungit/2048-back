package com.axun.game.dao;

import com.axun.game.dataObjects.GameRecordDO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GameRecordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GameRecordDO record);

    int insertSelective(GameRecordDO record);

    GameRecordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GameRecordDO record);

    int updateByPrimaryKey(GameRecordDO record);

    List<GameRecordDO> selectByUserId(Integer user_id);
}