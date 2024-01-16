package com.yuyun.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 设备数据
 *
 * @author hyh 
 * @since 1.0.0 2022-02-16
 */
@Data
@TableName("sys_device_data")
public class DeviceDataDTO implements Serializable {
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
	 * 邮箱
	 */
	private String email;

	/**
	 * 数据
	 */
	private Double dataValue;

	/**
	 * 设备数据点产生时间
	 */
	private LocalDateTime dateTime;



}