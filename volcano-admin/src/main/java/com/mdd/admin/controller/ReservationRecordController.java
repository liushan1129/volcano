package com.mdd.admin.controller;

import com.google.common.collect.Lists;
import com.mdd.admin.config.aop.Log;
import com.mdd.admin.dto.reservation.ReservationListDTO;
import com.mdd.admin.dto.reservation.ReservationTimeDTO;
import com.mdd.admin.service.IReservationRecordService;
import com.mdd.admin.validate.*;
import com.mdd.admin.validate.commons.IdValidate;
import com.mdd.admin.validate.commons.PageValidate;
import com.mdd.admin.vo.reservation.ReservationRecordDetailVo;
import com.mdd.admin.vo.reservation.ReservationRecordListVo;
import com.mdd.admin.vo.reservation.ReservationTimeVo;
import com.mdd.common.core.AjaxResult;
import com.mdd.common.core.PageResult;
import com.mdd.common.util.DozerUtils;
import com.mdd.common.util.StringUtils;
import com.mdd.common.validator.annotation.IDMust;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 预约记录表 前端控制器
 * </p>
 *
 * @author liushan
 * @since 2023-04-16
 */
@RestController
@RequestMapping("/api/reservationRecord")
public class ReservationRecordController {

    @Resource
    private IReservationRecordService iReservationRecordService;
    /**
     * 预约新增
     * @param createValidate 参数
     * @return AjaxResult<Object>
     */
    @Log(title = "预约新增")
    @PostMapping("/add")
    public AjaxResult<Object> add(@Validated @RequestBody ReservationRecordCreateValidate createValidate) {
        iReservationRecordService.add(createValidate);
        return AjaxResult.success();
    }

    /**
     * 预约记录列表
     * @param pageValidate 分页参数
     * @param searchValidate 搜索参数
     * @return AjaxResult<PageResult<ReservationRecordListVo>>
     */
    @GetMapping("/list")
    public AjaxResult<PageResult<ReservationRecordListVo>> list(@Validated PageValidate pageValidate,
                                                                @Validated ReservationRecordSearchValidate searchValidate) {
        PageResult<ReservationListDTO> pageResult = iReservationRecordService.list(pageValidate, searchValidate);
        PageResult result = new PageResult<>();
        if(pageResult == null) {
            result.setCount(0L);
            result.setPageNo(pageValidate.getPageNo());
            result.setPageSize(pageValidate.getPageSize());
            result.setLists(Lists.newArrayList());
            return AjaxResult.success(result);
        }
        List<ReservationRecordListVo> list = DozerUtils.mapList(pageResult.getLists(), ReservationRecordListVo.class);
        result.setCount(pageResult.getCount());
        result.setPageNo(pageResult.getPageNo());
        result.setPageSize(pageResult.getPageSize());
        result.setLists(list);
        return AjaxResult.success(result);
    }

    /**
     * 已被占用的时间列表
     * @return AjaxResult<List<String>>
     */
    @GetMapping("/enableTimeList/list")
    public AjaxResult<Object> enableTimeList(@Validated ReservationRecordSearchValidate searchValidate) {
       if(searchValidate.getUserId() == null) {
           return AjaxResult.failed("请先选择教练");
       }
       if(StringUtils.isBlank(searchValidate.getDate())) {
           return AjaxResult.failed("请先选择预约日期");

       }
        List<String> timeList= iReservationRecordService.getEnableTimeList (searchValidate.getUserId(), searchValidate.getDate());
        return AjaxResult.success(timeList);
    }

    /**
     * 预约详情
     *
     * @author fzr
     * @param id 主键
     * @return AjaxResult<SystemAuthAdminDetailVo>
     */
    @GetMapping("/detail")
    public AjaxResult<ReservationRecordDetailVo> detail(@Validated @IDMust() @RequestParam("id") Long id) {
        ReservationRecordDetailVo vo = DozerUtils.map(iReservationRecordService.detail(id), ReservationRecordDetailVo.class);
        return AjaxResult.success(vo);
    }


    /**
     * 预约编辑
     * @param updateValidate 参数
     * @return AjaxResult<Object>
     */
    @Log(title = "会员编辑")
    @PostMapping("/edit")
    public AjaxResult<Object> edit(@Validated @RequestBody ReservationRecordUpdateValidate updateValidate) {
        iReservationRecordService.edit(updateValidate);
        return AjaxResult.success();
    }

    /**
     * 取消预约
     * @return AjaxResult<Object>
     */
    @Log(title = "预约取消")
    @PostMapping("/cancel")
    public AjaxResult<Object> cancel(@Validated @RequestBody IdValidate idValidate) {
        iReservationRecordService.cancel(idValidate.getId().longValue());
        return AjaxResult.success();
    }

}
