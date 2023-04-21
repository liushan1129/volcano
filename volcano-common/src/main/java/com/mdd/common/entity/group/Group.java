package com.mdd.common.entity.group;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * <p>
 * 小组表
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */

@TableName("va_group")
@Data
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("课程名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("用户name")
    @TableField("username")
    private String username;

    @ApiModelProperty("会员id集合")
    @TableField("member_ids")
    private String memberIds;

    @ApiModelProperty("会员name集合")
    @TableField("member_names")
    private String memberNames;

    @ApiModelProperty("是否删除: 0=否, 1=是")
    @TableField("is_delete")
    private Integer isDelete;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private Long createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private Long updateTime;

    @ApiModelProperty("删除时间")
    @TableField("delete_time")
    private Long deleteTime;


}
