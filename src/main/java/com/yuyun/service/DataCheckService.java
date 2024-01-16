package com.yuyun.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuyun.dto.DeviceDataDTO;

public interface DataCheckService extends IService<DeviceDataDTO> {

    void checkData();

}