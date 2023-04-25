package com.mdd.admin.vo.reservation;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liushan
 * @data 2023/4/22 15:52
 */
@Data
public class ReservationRecordDetailVo implements Serializable {

        private static final long serialVersionUID = 1L;
        private Long id;

        private Long groupId;
        private String groupName;

        private Long userId;
        private String username;
        private String memberId;
        private String memberNames;
        //预约形式 1 小组，2 个人
        private Long reservationType;
        private String reservationDate;
        private String reservationTime;

        //状态: 0=完成, 1=取消
        private Integer status;
        private String statusDesc;

        private String createTime;

        private String updateTime;

        private String cancelTime;
}
