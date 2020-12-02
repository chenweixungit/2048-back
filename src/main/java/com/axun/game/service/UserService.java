package com.axun.game.service;


import com.axun.game.dataObjects.GameRecordDO;
import com.axun.game.error.BussinessException;
import com.axun.game.service.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BussinessException;
    UserModel validateLogin(String telephone, String encrypedPassword) throws BussinessException;
    UserModel getUserByTelephone(String telephone) throws BussinessException;
    void addGameRecord(GameRecordDO gameRecordDO) throws BussinessException;
    GameRecordDO getMaxScore(Integer user_id) throws BussinessException;
}
