package com.axun.game.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Component;


/**
 * 调用阿里云第三方接口实现发送短信功能
 */
@Component
public class SendSmsUtils {

    public void sendMsg(String phone,String code){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FzD9qQPTGEAbFb7CGrN", "3FjFPwV2O5ukMbdxTBypdc45fwDh96");//这里是你的密钥
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "2048小游戏");//填写你的签名
        request.putQueryParameter("TemplateCode", "SMS_205472250");
        request.putQueryParameter("TemplateParam","{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (
                ClientException e) {
            e.printStackTrace();
        }
    }

}

