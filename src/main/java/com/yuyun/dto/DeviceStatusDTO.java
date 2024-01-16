package com.yuyun.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 设备在线状态表
 *
 * @author hyh 
 * @since 1.0.0 2022-02-16
 */
@Data
@TableName("sys_device_status")
public class DeviceStatusDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * 产品ID
	 */
	//private String productId;

	/**
	 * 设备ID
	 */
	private Long deviceId;

	/**
	 * 时间
	 */
	private LocalDateTime dateTime;

	/**
	 * 生命周期事件
	 */
	private String status;

}