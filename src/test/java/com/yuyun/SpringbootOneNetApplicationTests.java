package com.yuyun;

import com.alibaba.fastjson.JSONObject;
import com.yuyun.utils.Base64Utils;
import com.yuyun.utils.HttpUtils;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootTest
class SpringbootOneNetApplicationTests {
    @Autowired
    JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String username;



    @Test
    public void dispatchOrders() {
        String deviceId = "866760442";
        String token = "version=2018-10-31&res=products%2F480701&et=1712112818&method=sha1&sign=89dqMv4ygwfq5dUZXEHsv88m%2BGI%3D";

        JSONObject headers = new JSONObject();
        headers.put("Authorization", token);
        JSONObject jsonObject = new JSONObject();

        // 发送给设备的命令参数
        jsonObject.put("color", "-101");

        String url = "http://api.heclouds.com/v1/synccmds?device_id=" + deviceId + "&timeout=30";
        System.out.println(jsonObject);
        String sendPost = HttpUtils.SendPost(url, headers, jsonObject);

        System.out.println(sendPost);

        JSONObject sp = JSONObject.parseObject(sendPost);
        int errno = sp.containsKey("errno") ? sp.getInteger("errno") : -1;
        System.out.println("errno:" + errno);

        String error = sp.containsKey("error") ? sp.getString("error") : "";
        System.out.println("error:" + error);

        if (errno != 0 && StringUtils.isNotBlank(error)) {
            System.out.println(error);
        }
        JSONObject data = sp.containsKey("data") ? sp.getJSONObject("data") : new JSONObject();
        System.out.println(data);

        if (data.containsKey("cmd_resp")) {
            String cmdResp = data.getString("cmd_resp");
            try {
                String str = Base64Utils.Base64Decode(cmdResp.getBytes());

                System.out.println("解码后的cmd_resp：" + str);
            } catch (Exception e) {
                System.out.println("解码失败！");
            }
        }
    }

    @Test
    public void dispatchOrders2() {
        net.sf.json.JSONObject headers = new net.sf.json.JSONObject();

        String token = "version=2018-10-31&res=products%2F480701&et=1712112818&method=sha1&sign=89dqMv4ygwfq5dUZXEHsv88m%2BGI%3D";
        headers.put("Authorization", token);

        String deviceId = "866760442";
        // 发送给设备的命令参数
        String param = "100";

        String params = String.format("\"color:%s\"", param);
        String url = "http://api.heclouds.com/v1/synccmds?device_id=" + deviceId + "&timeout=30";
        String sendPost = HttpUtils.SendPost(url, headers, params);

        System.out.println(params);
        System.out.println(sendPost);
    }

    @Test
    void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("这里写测试邮件标题");
        message.setText("这里写正文内容");
        message.setTo("dick14178@outlook.com"); //收件人邮箱地址,请自行修改
        message.setFrom(username);
        mailSender.send(message);
    }

}
