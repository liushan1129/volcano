package com.mdd.common.entity.reservation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 预约记录表
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
@TableName("va_reservation_record")
@Data
public class ReservationRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("小组id")
    @TableField("group_id")
    private Long groupId;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("会员id")
    @TableField("member_id")
    private String memberId;

    @ApiModelProperty("预约时间")
    @TableField("reservation_date")
    private String reservationDate;

    @ApiModelProperty("预约时间")
    @TableField("reservation_time")
    private String reservationTime;

    @ApiModelProperty("状态: 0=未开始, 1=取消, 2=完成")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private Long createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private Long updateTime;

    @ApiModelProperty("取消时间")
    @TableField("cancel_time")
    private Long cancelTime;


}
