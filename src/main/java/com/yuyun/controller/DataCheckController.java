package com.yuyun.controller;
import com.yuyun.service.DataCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DataCheckController {
    @Autowired
    private DataCheckService dataCheckService;
    @Scheduled(cron = "*/1 * * * *  ?") // 每1秒执行一次
    public void checkDataJob() {
        dataCheckService.checkData();
    }
}

