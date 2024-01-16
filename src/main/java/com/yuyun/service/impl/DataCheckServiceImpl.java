package com.yuyun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyun.dto.DeviceDataDTO;
import com.yuyun.mapper.DeviceDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import com.yuyun.service.DataCheckService;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DataCheckServiceImpl extends ServiceImpl<DeviceDataMapper, DeviceDataDTO> implements DataCheckService {
    @Autowired
    private DeviceDataMapper deviceDataMapper;
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void checkData() {
        // 查询异常数据，这里简单示范查询所有邮箱为空的用户数据
        List<DeviceDataDTO> deviceDataDTOS = deviceDataMapper.selectList(null);

        for (DeviceDataDTO deviceDataDTO : deviceDataDTOS) {
            if (deviceDataDTO.getDataValue()<0.01 || deviceDataDTO.getDataValue() >=0.5) {
                //如果监测数据的产生时间距今不超过2秒，相当于实时监测
                if(deviceDataDTO.getDateTime().isAfter(LocalDateTime.now().minusSeconds(2))) {
                    // 发送邮件告警
                    sendEmailAlert(deviceDataDTO);
//                    System.out.println("发送成功");
                }
            }
        }
    }
    private void sendEmailAlert(DeviceDataDTO deviceDataDTO) {
        // 构建邮件内容，并使用JavaMailSender发送邮件
        // 省略邮件发送代码，可以参考官方文档或其他教程
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(deviceDataDTO.getEmail());
            mailMessage.setSubject("水表告警");
            mailMessage.setText("您所管辖的地区，有用户出现异常用水量，请登录系统查看。");
            mailMessage.setFrom("1728017141@qq.com");
            javaMailSender.send(mailMessage);
        }
    }

