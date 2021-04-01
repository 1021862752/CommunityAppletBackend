package org.jeecg.modules.demo.activitylog.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.activitylog.entity.MdActivityLog;
import org.jeecg.modules.demo.activitylog.service.IMdActivityLogService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.integrallog.entity.MdIntegralLog;
import org.jeecg.modules.demo.integrallog.service.IMdIntegralLogService;
import org.jeecg.modules.demo.volunteer.entity.MdVolunteer;
import org.jeecg.modules.demo.volunteer.service.IMdVolunteerService;
import org.jeecg.modules.demo.volunteeractivity.entity.MdActivityTimeDetail;
import org.jeecg.modules.demo.volunteeractivity.entity.MdVolunteerActivity;
import org.jeecg.modules.demo.volunteeractivity.service.IMdActivityTimeDetailService;
import org.jeecg.modules.demo.volunteeractivity.service.IMdVolunteerActivityService;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 活动参与记录
 * @Author: jeecg-boot
 * @Date: 2020-05-31
 * @Version: V1.0
 */
@Api(tags = "活动参与记录")
@RestController
@RequestMapping("/activitylog/mdActivityLog")
@Slf4j
public class MdActivityLogController extends JeecgController<MdActivityLog, IMdActivityLogService> {
    @Autowired
    private IMdActivityLogService mdActivityLogService;
    @Autowired
    private IMdVolunteerActivityService mdVolunteerActivityService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IMdIntegralLogService mdIntegralLogService;
    @Autowired
    private IMdVolunteerService mdVolunteerService;
    @Autowired
    private IMdActivityTimeDetailService mdActivityTimeDetailService;


    private static ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<>(1);

    /**
     * 分页列表查询
     *
     * @param mdActivityLog
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "活动参与记录-分页列表查询")
    @ApiOperation(value = "活动参与记录-分页列表查询", notes = "活动参与记录-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(MdActivityLog mdActivityLog,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<MdActivityLog> queryWrapper = QueryGenerator.initQueryWrapper(mdActivityLog, req.getParameterMap());
        Page<MdActivityLog> page = new Page<MdActivityLog>(pageNo, pageSize);
        IPage<MdActivityLog> pageList = mdActivityLogService.page(page, queryWrapper);
        List<MdActivityLog> returnList = pageList.getRecords();
        for (int i = 0; i < returnList.size(); i++) {
            if (returnList.get(i).getAlTimeStart().getTime() > new Date().getTime()) {
                returnList.get(i).setActivityStatus("未开始");
            } else {
                if (returnList.get(i).getAlTimeEnd().getTime() < new Date().getTime()) {
                    returnList.get(i).setActivityStatus("已结束");
                } else {
                    returnList.get(i).setActivityStatus("进行中");
                }
            }
        }
        pageList.setRecords(returnList);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param mdActivityLog
     * @return
     */
    @AutoLog(value = "活动参与记录-报名")
    @ApiOperation(value = "活动参与记录-报名", notes = "活动参与记录-报名")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody MdActivityLog mdActivityLog) throws Exception {
        MdVolunteerActivity mdVolunteerActivity = mdVolunteerActivityService.getActivityInfoBy(mdActivityLog.getAlId());
        if (mdVolunteerActivity == null || mdVolunteerActivity.getId() == null) {
            return Result.error("该活动不存在");
        }
        if (mdVolunteerActivity.getVaTimeEnd().getTime() < new Date().getTime()) {
            return Result.error("该活动已结束");
        }
        if (mdVolunteerActivity.getVaStatus().equals("草稿箱")) {
            return Result.error("该活动未发布");
        }
        MdVolunteer mdVolunteer = mdVolunteerService.getVolunteerByid(mdActivityLog.getAlOpenId());
        if (mdVolunteer == null || mdVolunteer.getId() == null) {
            return Result.error("未报名志愿者");
        }
        putThread();
        if (mdVolunteerActivity.getVaQuota() <= Integer.parseInt(mdVolunteerActivity.getVaParticipants())) {
            pollThread();
            return Result.error("活动报名人数已满");
        }
        //更新报名人数
        mdVolunteerActivity.setVaParticipants(mdVolunteerActivity.getVaParticipants() + 1);
        mdVolunteerActivityService.updateById(mdVolunteerActivity);
        mdActivityLog.setAlName(mdVolunteerActivity.getVaTitle());
        mdActivityLog.setAlSignIn("未签到");
        pollThread();
        if (mdVolunteerActivity.getVaTimeStart().getTime() < new Date().getTime()) {
            mdActivityLog.setActivityStatus("未开始");
        } else {
            if (mdVolunteerActivity.getVaTimeEnd().getTime() < new Date().getTime()) {
                mdActivityLog.setActivityStatus("已结束");
            } else {
                mdActivityLog.setActivityStatus("进行中");
            }
        }
        if (mdActivityLog.getAlDetailId().equals(mdActivityLog.getAlId())) {
            mdActivityLog.setAlTimeStart(mdVolunteerActivity.getVaTimeStart());
            mdActivityLog.setAlTimeEnd(mdVolunteerActivity.getVaTimeEnd());
            mdActivityLog.setAlIntegral(mdVolunteerActivity.getVaIntegral());
        } else {
            List<MdActivityTimeDetail> mdActivityTimeDetailList = mdActivityTimeDetailService.selectByMainId(mdActivityLog.getAlId());
            for (int i = 0; i < mdActivityTimeDetailList.size(); i++) {
                if (mdActivityLog.getAlDetailId().equals(mdActivityTimeDetailList.get(i).getId())) {
                    mdActivityLog.setAlTimeStart(mdActivityTimeDetailList.get(i).getVaTimeStart());
                    mdActivityLog.setAlTimeEnd(mdActivityTimeDetailList.get(i).getVaTimeEnd());
                    mdActivityLog.setAlIntegral(Integer.parseInt(mdActivityTimeDetailList.get(i).getVaIntegral()));
                    break;
                }
            }
        }
        mdActivityLog.setAlAddress(mdVolunteerActivity.getVaAddress());
        mdActivityLog.setAlType(mdVolunteerActivity.getVaType());
        mdActivityLog.setAlPhone(mdVolunteer.getVtPhone());
        mdActivityLog.setAlIdentity(mdActivityLog.getAlOpenId());
        mdActivityLogService.save(mdActivityLog);
        pollThread();
        return Result.ok("报名成功！");
    }

    /**
     * 编辑
     *
     * @param mdActivityLog
     * @return
     */
    @AutoLog(value = "活动参与记录-编辑")
    @ApiOperation(value = "活动参与记录-编辑", notes = "活动参与记录-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody MdActivityLog mdActivityLog) {
        mdActivityLogService.updateById(mdActivityLog);
        return Result.ok("编辑成功!");
    }

    /**
     * 签到
     *
     * @param mdActivityLog
     * @return
     */
    @AutoLog(value = "活动参与记录-签到")
    @ApiOperation(value = "活动参与记录-签到", notes = "活动参与记录-签到")
    @PutMapping(value = "/sign")
    public Result<?> sign(@RequestBody MdActivityLog mdActivityLog) {
        MdVolunteerActivity mdVolunteerActivity = mdVolunteerActivityService.getActivityInfoBy(mdActivityLog.getAlId());
        SysUser sysUser = sysUserService.getUserByOpid(mdActivityLog.getAlOpenId());
        List<MdActivityLog> mdActivityLogList = mdActivityLogService.getActivityLogByDetailId(mdActivityLog.getAlId(), mdActivityLog.getAlOpenId(), mdActivityLog.getAlDetailId());
        if (mdActivityLogList.size() == 0) {
            return Result.error("不是当前用户报名，无法签到!");
        }
        String signStr = mdActivityLogList.get(0).getAlSignIn();
        if ("已签到".equals(signStr)) {
            return Result.error("您已签到!");
        }
        Integer intNum = 0;
        if (mdActivityLog.getAlDetailId().equals(mdActivityLog.getAlId())) {
            intNum = mdVolunteerActivity.getVaIntegral();
        } else {
            List<MdActivityTimeDetail> mdActivityTimeDetailList = mdActivityTimeDetailService.selectByMainId(mdActivityLog.getAlId());
            for (int i = 0; i < mdActivityTimeDetailList.size(); i++) {
                if (mdActivityLog.getAlDetailId().equals(mdActivityTimeDetailList.get(i).getId())) {
                    intNum = Integer.parseInt(mdActivityTimeDetailList.get(i).getVaIntegral());
                    break;
                }
            }
        }
        //保存积分积分
        if (mdVolunteerActivity.getVaIntegral() != 0) {
            MdIntegralLog integralLog = new MdIntegralLog();
            integralLog.setIlOpenid(mdActivityLog.getAlOpenId());
            integralLog.setIlUserName(sysUser.getRealname());
            integralLog.setIlIntegral(intNum);
            integralLog.setIlActivityId(mdActivityLog.getAlId());
            integralLog.setIlActivityName(mdVolunteerActivity.getVaTitle());
            mdIntegralLogService.save(integralLog);
            //更新用户积分
            if (sysUser.getIntegral() != null) {
                sysUser.setIntegral(sysUser.getIntegral() + intNum);
            } else {
                sysUser.setIntegral(intNum);
            }
            sysUser.setDelFlag("0");
            sysUserService.updateUserByOpid(sysUser.getId(), sysUser);
            sysUserService.updateById(sysUser);
        }
        mdActivityLogService.updateSign("已签到", mdActivityLog.getAlId(), mdActivityLog.getAlOpenId(), mdActivityLog.getAlDetailId());
        return Result.ok("签到成功!");
    }


    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "活动参与记录-通过id删除")
    @ApiOperation(value = "活动参与记录-通过id删除", notes = "活动参与记录-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        mdActivityLogService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "活动参与记录-批量删除")
    @ApiOperation(value = "活动参与记录-批量删除", notes = "活动参与记录-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.mdActivityLogService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "活动参与记录-通过id查询")
    @ApiOperation(value = "活动参与记录-通过id查询", notes = "活动参与记录-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        MdActivityLog mdActivityLog = mdActivityLogService.getById(id);
        if (mdActivityLog == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(mdActivityLog);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param mdActivityLog
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MdActivityLog mdActivityLog) {
        return super.exportXls(request, mdActivityLog, MdActivityLog.class, "活动参与记录");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, MdActivityLog.class);
    }

    void putThread() throws Exception {
        arrayBlockingQueue.put("1");
    }

    void pollThread() throws Exception {
        arrayBlockingQueue.poll();
    }

}
