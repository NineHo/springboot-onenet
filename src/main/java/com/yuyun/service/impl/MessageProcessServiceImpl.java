package com.yuyun.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yuyun.dto.DeviceDataDTO;
import com.yuyun.dto.DeviceStatusDTO;
import com.yuyun.dto.SysMeterAlarm;
import com.yuyun.service.DeviceDataService;
import com.yuyun.service.DeviceEventService;
import com.yuyun.service.MessageProcessService;
import com.yuyun.service.SysMeterAlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @author hyh
 */
@Slf4j
@Service
public class MessageProcessServiceImpl implements MessageProcessService {
    
    @Autowired
    private DeviceEventService deviceEventService;
    @Autowired
    private DeviceDataService deviceDataService;
    @Autowired
    private SysMeterAlarmService sysMeterAlarmService;
    @Override
    @Transactional
    public void messageProcess(String body) {
        JSONObject obj = JSONObject.parseObject(body);
        //产品信息
        JSONObject data = obj.getJSONObject("data");
        // 消息类型
        String messageType = obj.getString("messageType");
        //设备id
        Long deviceId = obj.getLong("deviceId");
        //联系邮箱
        String email = new String("dick14178@outlook.com");
        // 生命周期事件
        if ("lifeCycle".equals(messageType)){
            String status = data.getString("status");
            LocalDateTime time = LocalDateTime.ofEpochSecond(data.getLong("time")/1000, 0, ZoneOffset.ofHours(8));
            DeviceStatusDTO deviceEvent = new DeviceStatusDTO();
            deviceEvent.setDeviceId(deviceId);
            deviceEvent.setDateTime(time);
            deviceEvent.setStatus(status);
            // 存储到数据库
            deviceEventService.save(deviceEvent);
        }
        // 数据点消息
        else if ("notify".equals(messageType)){
            //产品信息参数
            JSONObject params = data.getJSONObject("params");
            //水流量参数
            JSONObject waterfloat = params.getJSONObject("waterfloat");
            //水流量数值
            Double value = waterfloat.getDouble("value");
            //数据上传时间
            LocalDateTime time = LocalDateTime.ofEpochSecond(waterfloat.getLong("time")/1000, 0, ZoneOffset.ofHours(8));
            DeviceDataDTO deviceDataDTO = new DeviceDataDTO();
            deviceDataDTO.setDeviceId(deviceId);
            deviceDataDTO.setDateTime(time);
            deviceDataDTO.setDataValue(value);
            deviceDataDTO.setEmail(email);

            SysMeterAlarm sysMeterAlarm = new SysMeterAlarm();
            sysMeterAlarm.setMeterId(deviceId);
            sysMeterAlarm.setStatus(2);
            sysMeterAlarm.setConnector("Patrick");
            sysMeterAlarm.setCreateTime(time);



            // 存储到数据库
            deviceDataService.save(deviceDataDTO);
            sysMeterAlarmService.save(sysMeterAlarm);

        } else {
            log.error("未知消息类型的数据：" + body);
        }
    }
}
