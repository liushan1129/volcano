package com.mdd.common.entity.reservation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 预约记录表
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
@Getter
@Setter
@TableName("va_reservation_record")
@ApiModel(value = "ReservationRecord对象", description = "预约记录表")
public class ReservationRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("小组id")
    @TableField("group_id")
    private Long groupId;

    @ApiModelProperty("预约时间时间")
    @TableField("reservation_time")
    private Integer reservationTime;

    @ApiModelProperty("状态: 0=完成, 1=取消")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private Integer createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private Integer updateTime;

    @ApiModelProperty("取消时间")
    @TableField("cancel_time")
    private Integer cancelTime;


}
