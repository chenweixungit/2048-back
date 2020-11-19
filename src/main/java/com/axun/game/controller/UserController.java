package com.axun.game.controller;

import com.alibaba.druid.util.StringUtils;

import com.axun.game.controller.viewObjects.UserVO;
import com.axun.game.error.BussinessException;
import com.axun.game.error.EnumBusinessError;
import com.axun.game.response.CommonReturnType;
import com.axun.game.service.UserService;
import com.axun.game.service.model.UserModel;
import com.axun.game.utils.MD5;
import com.axun.game.utils.RedisUtil;
import com.axun.game.utils.SendSmsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;



@Controller
@RequestMapping("/user")
@CrossOrigin
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SendSmsUtils sendSmsUtils;
    /**
     * 用户登录
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType uerLogin(String telephone, String password) throws BussinessException{

        if(StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)){
            throw new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        String encrypedPassword = MD5.MD5Encode(password);
        UserModel userModel = userService.validateLogin(telephone,encrypedPassword);
//        httpServletRequest.getSession().setAttribute("userModel",userModel);
        UserModel returnUserModel = userService.getUserByTelephone(telephone);
        return CommonReturnType.create("success",convertFromModel(returnUserModel));
    }


    /**
     * 用户注册
     * @return
     * @throws BussinessException
     */

    @RequestMapping(value = "/register",method = {RequestMethod.POST})
    @ResponseBody
    public CommonReturnType uerRegister(String telephone,
                                        String otpCode,
                                        String name,
                                        Integer gender,
                                        Integer age,
                                        String password) throws BussinessException{
        String sessionCode = String.valueOf(redisUtil.get(telephone));
        // 判断校验码是否正确
        if(!StringUtils.equals(otpCode,sessionCode)){
            throw new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        // 把数据插入数据库
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setEncrypedPassword(MD5.MD5Encode(password));
        userModel.setAge(age);
        userModel.setGender(gender);
        userModel.setTelephone(telephone);
        userService.register(userModel);
        return CommonReturnType.create("success",null);
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     * @throws BussinessException
     */

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="id")Integer id) throws BussinessException{
        UserModel userModel = userService.getUserById(id);
        if(userModel == null){
            throw new BussinessException(EnumBusinessError.UER_NOT_EXIST);
        }
        return CommonReturnType.create(convertFromModel(userService.getUserById(id)));
    }

    /**
     * userModel转换为用户可见的userVO类型
     * @param userModel
     * @return
     */
    public UserVO convertFromModel(UserModel userModel){
        if(userModel == null) return null;
        UserVO userVo = new UserVO();
        BeanUtils.copyProperties(userModel,userVo);
        return userVo;
    }


    /**
     * 发送验证码
     * @return
     */

    @Transactional
    @RequestMapping(value = "/getOtp",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType getOtp(String telephone) throws BussinessException{
        if(telephone == null || telephone.equals("")){
            throw new BussinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        // 生成随机的六位数验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 100000;
        String code = String.valueOf(randomInt);
        // redis存储验证码，同时设置过期时间
        redisUtil.set(telephone,randomInt);
        redisUtil.expire(telephone,300);

        // 向手机发送验证码
        sendSmsUtils.sendMsg(telephone,code);
        // 这里方便使用，使用了httpsession来存储电话和验证码
        // 正常的分布式系统中，采用redis来存储的验证码，redis本身存储键值对格式的数据，同时redis自带过期处理，
//        httpServletRequest.getSession().setAttribute(telephone,randomInt);
        System.out.println("telephone: "+ telephone + " 验证码" + randomInt);
        Map<String,String> obj = new HashMap<>();
        obj.put("telephone",telephone);
        obj.put("code",code);
        return CommonReturnType.create("success",obj);
    }

}
