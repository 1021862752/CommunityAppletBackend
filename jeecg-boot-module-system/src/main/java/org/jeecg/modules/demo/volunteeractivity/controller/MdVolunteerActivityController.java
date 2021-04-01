package org.jeecg.modules.demo.volunteeractivity.controller;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.config.QRCodeUtils;
import org.jeecg.modules.demo.activitylog.entity.MdActivityLog;
import org.jeecg.modules.demo.activitylog.service.IMdActivityLogService;
import org.jeecg.modules.demo.volunteeractivity.entity.MdActivityTimeDetail;
import org.jeecg.modules.demo.volunteeractivity.entity.MdVolunteerActivity;
import org.jeecg.modules.demo.volunteeractivity.service.IMdActivityTimeDetailService;
import org.jeecg.modules.demo.volunteeractivity.service.IMdVolunteerActivityService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.jeecg.modules.demo.volunteeractivity.vo.MdVolunteerActivityPage;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 志愿活动
 * @Author: jeecg-boot
 * @Date: 2020-05-29
 * @Version: V1.0
 */
@Api(tags = "志愿活动")
@RestController
@RequestMapping("/volunteeractivity/mdVolunteerActivity")
@Slf4j
public class MdVolunteerActivityController extends JeecgController<MdVolunteerActivity, IMdVolunteerActivityService> {
    @Autowired
    private IMdVolunteerActivityService mdVolunteerActivityService;
    @Autowired
    private IMdActivityLogService mdActivityLogService;

    @Autowired
    private IMdActivityTimeDetailService mdActivityTimeDetailService;

    @Value(value = "${jeecg.path.upload}")
    private String uploadpath;

    /**
     * 本地：local minio：minio 阿里：alioss
     */
    @Value(value = "${jeecg.uploadType}")
    private String uploadType;

    /**
     * 分页列表查询
     *
     * @param mdVolunteerActivity
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "志愿活动-分页列表查询")
    @ApiOperation(value = "志愿活动-分页列表查询", notes = "志愿活动-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(MdVolunteerActivity mdVolunteerActivity,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<MdVolunteerActivity> queryWrapper = QueryGenerator.initQueryWrapper(mdVolunteerActivity, req.getParameterMap());
        Page<MdVolunteerActivity> page = new Page<MdVolunteerActivity>(pageNo, pageSize);
        IPage<MdVolunteerActivity> pageList = mdVolunteerActivityService.page(page, queryWrapper);
        List<MdVolunteerActivity> listRecords = pageList.getRecords();
        for (int i = 0; i < listRecords.size(); i++) {
            Date timeStart = listRecords.get(i).getVaTimeStart();
            Date timeEnd = listRecords.get(i).getVaTimeEnd();
            if (timeStart.getTime() > new Date().getTime()) {
                listRecords.get(i).setVaActivityStatus("未开始");
            } else {
                if (timeEnd.getTime() < new Date().getTime()) {
                    listRecords.get(i).setVaActivityStatus("已结束");
                } else {
                    listRecords.get(i).setVaActivityStatus("进行中");
                }
            }
        }
        return Result.ok(pageList);
    }

    @AutoLog(value = "志愿活动-获取活动信息")
    @ApiOperation(value = "志愿活动-获取活动信息", notes = "志愿活动-获取活动信息")
    @GetMapping(value = "/listInfo")
    public Result<?> queryPageList(@RequestParam(name = "vaStatus", required = true) String vaStatus,
                                   @RequestParam(name = "openId", required = true) String openId,
                                   @RequestParam(name = "type", required = false) String type,
                                   @RequestParam(name = "activityStatus", required = false) String activityStatus) {
        List<Map> list = mdVolunteerActivityService.getVolunteerActivityList(vaStatus, type);
        List<Map> listByUser = mdActivityLogService.getActivityLogByUser(openId);
        List<Map> returnList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Date timeStart = (Date) list.get(i).get("va_time_start");
            Date timeEnd = (Date) list.get(i).get("va_time_end");
            list.get(i).put("va_details", null);
            if (timeStart.getTime() > new Date().getTime()) {
                list.get(i).put("activity_status", "未开始");
            } else {
                if (timeEnd.getTime() < new Date().getTime()) {
                    list.get(i).put("activity_status", "已结束");
                } else {
                    list.get(i).put("activity_status", "进行中");
                }
            }
            if (listByUser == null || listByUser.size() == 0) {
                list.get(i).put("sign_flag", "未报名");
            } else {
                for (int j = 0; j < listByUser.size(); j++) {
                    if (list.get(i).get("id").equals(listByUser.get(j).get("al_id"))) {
                        list.get(i).put("sign_flag", "已报名");
                        break;
                    }
                    if (listByUser.size() - 1 == j) {
                        list.get(i).put("sign_flag", "未报名");
                    }
                }
            }
            if (activityStatus == null || "".equals(activityStatus)) {
                returnList.add(list.get(i));
            } else {
                if (list.get(i).get("activity_status").equals(activityStatus)) {
                    returnList.add(list.get(i));
                }
            }
        }
        return Result.ok(returnList);
    }


    /**
     * 添加
     *
     * @param mdVolunteerActivity
     * @return
     */
    @AutoLog(value = "志愿活动-添加")
    @ApiOperation(value = "志愿活动-添加", notes = "志愿活动-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody MdVolunteerActivity mdVolunteerActivity) throws Exception {
        //默认数据状态为草稿箱，类型为志愿活动
        mdVolunteerActivity.setVaType("志愿活动");
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        mdVolunteerActivity.setId(uuid);
        String arCodeStr = getVrCode(uuid);
        mdVolunteerActivity.setVaQrCode("temp/" + arCodeStr);
        mdVolunteerActivity.setVaParticipants("0");
        mdVolunteerActivityService.save(mdVolunteerActivity);
        return Result.ok("添加成功！");
    }

    /**
     * 添加
     *
     * @param mdVolunteerActivityPage
     * @return
     */
    @AutoLog(value = "众筹活动-添加")
    @ApiOperation(value = "众筹活动-添加", notes = "众筹活动-添加")
    @PostMapping(value = "/detailAdd")
    public Result<?> detailAdd(@RequestBody MdVolunteerActivityPage mdVolunteerActivityPage) throws Exception {
        MdVolunteerActivity mdVolunteerActivity = new MdVolunteerActivity();
        BeanUtils.copyProperties(mdVolunteerActivityPage, mdVolunteerActivity);
        mdVolunteerActivity.setVaType("众筹活动");
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        mdVolunteerActivity.setId(uuid);
        String arCodeStr = getVrCode(uuid);
        mdVolunteerActivity.setVaQrCode("temp/" + arCodeStr);
        mdVolunteerActivity.setVaParticipants("0");
        List<MdActivityTimeDetail> activityTimeDetailList = mdVolunteerActivityPage.getMdActivityTimeDetailList();
        Date maxTime = null;
        Date minTime = null;
        int countNum = 0;
        for (int i = 0; i < activityTimeDetailList.size(); i++) {
            if (i == 0) {
                minTime = activityTimeDetailList.get(i).getVaTimeStart();
                maxTime = activityTimeDetailList.get(i).getVaTimeEnd();
            } else {
                if (minTime.getTime() > activityTimeDetailList.get(i).getVaTimeStart().getTime()) {
                    minTime = activityTimeDetailList.get(i).getVaTimeStart();
                }
                if (maxTime.getTime() < activityTimeDetailList.get(i).getVaTimeEnd().getTime()) {
                    maxTime = activityTimeDetailList.get(i).getVaTimeEnd();
                }
            }
            countNum += Integer.parseInt(activityTimeDetailList.get(i).getVaIntegral());
        }
        mdVolunteerActivity.setVaTimeStart(minTime);
        mdVolunteerActivity.setVaTimeEnd(maxTime);
        mdVolunteerActivity.setVaQuota(activityTimeDetailList.size());
        mdVolunteerActivity.setVaIntegral(countNum);
        mdVolunteerActivityService.saveMain(mdVolunteerActivity, mdVolunteerActivityPage.getMdActivityTimeDetailList());
        return Result.ok("添加成功！");
    }


    /**
     * 编辑
     *
     * @param mdVolunteerActivity
     * @return
     */
    @AutoLog(value = "志愿活动-编辑")
    @ApiOperation(value = "志愿活动-编辑", notes = "志愿活动-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody MdVolunteerActivity mdVolunteerActivity) {
        mdVolunteerActivityService.updateById(mdVolunteerActivity);
        return Result.ok("编辑成功!");
    }

    /**
     * 编辑
     *
     * @param mdVolunteerActivityPage
     * @return
     */
    @AutoLog(value = "志愿活动-编辑")
    @ApiOperation(value = "志愿活动-编辑", notes = "志愿活动-编辑")
    @PutMapping(value = "/detailEdit")
    public Result<?> edit(@RequestBody MdVolunteerActivityPage mdVolunteerActivityPage) {
        MdVolunteerActivity mdVolunteerActivity = new MdVolunteerActivity();
        BeanUtils.copyProperties(mdVolunteerActivityPage, mdVolunteerActivity);
        MdVolunteerActivity mdVolunteerActivityEntity = mdVolunteerActivityService.getById(mdVolunteerActivity.getId());
        if (mdVolunteerActivityEntity == null) {
            return Result.error("未找到对应数据");
        }
        List<MdActivityTimeDetail> activityTimeDetailList = mdVolunteerActivityPage.getMdActivityTimeDetailList();
        Date maxTime = null;
        Date minTime = null;
        int countNum = 0;
        for (int i = 0; i < activityTimeDetailList.size(); i++) {
            if (i == 0) {
                minTime = activityTimeDetailList.get(i).getVaTimeStart();
                maxTime = activityTimeDetailList.get(i).getVaTimeEnd();
            } else {
                if (minTime.getTime() > activityTimeDetailList.get(i).getVaTimeStart().getTime()) {
                    minTime = activityTimeDetailList.get(i).getVaTimeStart();
                }
                if (maxTime.getTime() < activityTimeDetailList.get(i).getVaTimeEnd().getTime()) {
                    maxTime = activityTimeDetailList.get(i).getVaTimeEnd();
                }
            }
            countNum += Integer.parseInt(activityTimeDetailList.get(i).getVaIntegral());
        }
        mdVolunteerActivity.setVaTimeStart(minTime);
        mdVolunteerActivity.setVaTimeEnd(maxTime);
        mdVolunteerActivity.setVaQuota(activityTimeDetailList.size());
        mdVolunteerActivity.setVaIntegral(countNum);
        mdVolunteerActivityService.updateMain(mdVolunteerActivity, mdVolunteerActivityPage.getMdActivityTimeDetailList());
        return Result.ok("编辑成功!");
    }

    /**
     * 编辑
     *
     * @param mdVolunteerActivity
     * @return
     */
    @AutoLog(value = "志愿活动-发布")
    @ApiOperation(value = "志愿活动-发布", notes = "志愿活动-发布")
    @PutMapping(value = "/release")
    public Result<?> release(@RequestBody MdVolunteerActivity mdVolunteerActivity) {
        mdVolunteerActivity.setVaStatus("已发布");
        mdVolunteerActivityService.updateById(mdVolunteerActivity);
        return Result.ok("发布成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "志愿活动-通过id删除")
    @ApiOperation(value = "志愿活动-通过id删除", notes = "志愿活动-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        mdVolunteerActivityService.removeById(id);
        mdActivityLogService.deleteByAlId(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "志愿活动-批量删除")
    @ApiOperation(value = "志愿活动-批量删除", notes = "志愿活动-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.mdVolunteerActivityService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "志愿活动-通过id查询")
    @ApiOperation(value = "志愿活动-通过id查询", notes = "志愿活动-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        MdVolunteerActivity mdVolunteerActivity = mdVolunteerActivityService.getById(id);
        if (mdVolunteerActivity == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(mdVolunteerActivity);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "活动时间域信息集合-通过id查询")
    @ApiOperation(value = "活动时间域信息集合-通过id查询", notes = "活动时间域信息-通过id查询")
    @GetMapping(value = "/queryMdActivityTimeDetailByMainId")
    public Result<?> queryMdActivityTimeDetailListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<MdActivityTimeDetail> mdActivityTimeDetailList = mdActivityTimeDetailService.selectByMainId(id);
        return Result.ok(mdActivityTimeDetailList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "活动时间域信息集合-获取当前用户报名信息")
    @ApiOperation(value = "活动时间域信息集合-获取当前用户报名信息", notes = "活动时间域信息-获取当前用户报名信息")
    @GetMapping(value = "/queryMdActivityTimeDetailByAlId")
    public Result<?> queryMdActivityTimeDetailByAlId(@RequestParam(name = "id", required = true) String id,
                                                     @RequestParam(name = "openId", required = true) String openId) {
        List<MdActivityTimeDetail> mdActivityTimeDetailList = mdActivityTimeDetailService.selectByMainId(id);
        List<MdActivityLog> mdActivityLogList = null;
        for (int i = 0; i < mdActivityTimeDetailList.size(); i++) {
            mdActivityLogList = mdActivityLogService.getActivityLogByDetailId(id, openId, mdActivityTimeDetailList.get(i).getId());
            if (mdActivityLogList == null || mdActivityLogList.size() == 0) {
                mdActivityTimeDetailList.get(i).setVaIsSign("N");
            } else if (mdActivityLogList.size() > 0) {
                mdActivityTimeDetailList.get(i).setVaIsSign("Y");
            }
        }
        return Result.ok(mdActivityTimeDetailList);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param mdVolunteerActivity
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MdVolunteerActivity mdVolunteerActivity) {
        return super.exportXls(request, mdVolunteerActivity, MdVolunteerActivity.class, "志愿活动");
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
        return super.importExcel(request, response, MdVolunteerActivity.class);
    }

    String getVrCode(String uuid) throws Exception {
        String bizPath = "temp";
        String ctxPath = uploadpath;
        String fileName = null;
        File file = new File(ctxPath + File.separator + bizPath + File.separator);
        if (!file.exists()) {
            file.mkdirs();// 创建文件根目录
        }
        String savePath = file.getPath() + File.separator;
        String text = uuid;  //这里设置自定义网站url
        String logoPath = null;
        return QRCodeUtils.encode(text, logoPath, savePath, true);
    }
}
