package com.mdd.admin.dto.reservation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/17 12:54
 */
@Data
public class ReservationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long groupId;


    private Long userId;


    private String memberIds;

    private Integer reservationTime;


    private Integer status;


    private Long createTime;


    private Long updateTime;


    private Long cancelTime;


}
