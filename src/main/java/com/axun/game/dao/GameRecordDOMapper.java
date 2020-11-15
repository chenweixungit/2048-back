package com.axun.game.dao;

import com.axun.game.dataObjects.GameRecordDO;

public interface GameRecordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GameRecordDO record);

    int insertSelective(GameRecordDO record);

    GameRecordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GameRecordDO record);

    int updateByPrimaryKey(GameRecordDO record);
}