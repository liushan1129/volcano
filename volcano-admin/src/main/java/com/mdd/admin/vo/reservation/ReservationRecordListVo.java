package com.mdd.admin.vo.reservation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liushan
 * @data 2023/4/22 14:13
 */
@Data
public class ReservationRecordListVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    private Long groupId;
    private String groupName;


    private Long userId;

    private String userName;
    private String memberId;
    private String memberNames;

    private String reservationDateTime;

    private Integer status;
    private String statusDesc;


    private Long createTime;


    private Long updateTime;

    private Long cancelTime;
}
