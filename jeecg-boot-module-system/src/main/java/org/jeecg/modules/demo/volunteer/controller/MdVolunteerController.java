package org.jeecg.modules.demo.volunteer.controller;

import java.text.SimpleDateFormat;
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
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.activitylog.service.IMdActivityLogService;
import org.jeecg.modules.demo.integrallog.service.IMdIntegralLogService;
import org.jeecg.modules.demo.running.entity.FlowingWater;
import org.jeecg.modules.demo.running.service.IFlowingWaterService;
import org.jeecg.modules.demo.volunteer.entity.MdVolunteer;
import org.jeecg.modules.demo.volunteer.service.IMdVolunteerService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: 志愿者
 * @Author: jeecg-boot
 * @Date: 2020-05-27
 * @Version: V1.0
 */
@Api(tags = "志愿者")
@RestController
@RequestMapping("/volunteer/mdVolunteer")
@Slf4j
public class MdVolunteerController extends JeecgController<MdVolunteer, IMdVolunteerService> {
    @Autowired
    private IMdVolunteerService mdVolunteerService;

    @Autowired
    private IFlowingWaterService flowingWaterService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IMdIntegralLogService mdIntegralLogService;

    @Autowired
    private IMdActivityLogService mdActivityLogService;

    private static ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<>(1);

    /**
     * 分页列表查询
     *
     * @param mdVolunteer
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "志愿者-分页列表查询")
    @ApiOperation(value = "志愿者-分页列表查询", notes = "志愿者-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(MdVolunteer mdVolunteer,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<MdVolunteer> queryWrapper = QueryGenerator.initQueryWrapper(mdVolunteer, req.getParameterMap());
        Page<MdVolunteer> page = new Page<MdVolunteer>(pageNo, pageSize);
        IPage<MdVolunteer> pageList = mdVolunteerService.page(page, queryWrapper);
        List<MdVolunteer> volunteers = page.getRecords();
        for (int i = 0; i < volunteers.size(); i++) {
            SysUser user = sysUserService.getUserByOpid(volunteers.get(i).getVtOpenId());
            if(null != user) {
                volunteers.get(i).setVtIntegral(user.getIntegral() == null ? 0 : user.getIntegral());
            }
        }
        page.setRecords(volunteers);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param mdVolunteer
     * @return
     */
    @AutoLog(value = "志愿者-添加")
    @ApiOperation(value = "志愿者-添加", notes = "志愿者-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody MdVolunteer mdVolunteer) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String newStr = simpleDateFormat.format(date);//改变格式
        putThread();
        FlowingWater flowingWater = flowingWaterService.getWaterByDate(newStr);
        if (flowingWater == null || flowingWater.getDateStr() == null) {
            String wStr = String.format("%03d", 1);
            String vtCode = "FZ" + newStr + wStr;
            mdVolunteer.setVtCode(vtCode);
            flowingWater = new FlowingWater();
            flowingWater.setDateStr(newStr);
            flowingWater.setWater(1);
            flowingWaterService.save(flowingWater);
            mdVolunteerService.save(mdVolunteer);
        } else {
            String wStr = String.format("%03d", flowingWater.getWater() + 1);
            String vtCode = "FZ" + newStr + wStr;
            mdVolunteer.setVtCode(vtCode);
            flowingWater.setWater(flowingWater.getWater() + 1);
            flowingWaterService.updateById(flowingWater);
            mdVolunteerService.save(mdVolunteer);
        }
        pollThread();
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param mdVolunteer
     * @return
     */
    @AutoLog(value = "志愿者-编辑")
    @ApiOperation(value = "志愿者-编辑", notes = "志愿者-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody MdVolunteer mdVolunteer) {
        SysUser sysUser = sysUserService.getUserByOpid(mdVolunteer.getVtOpenId());
        //更新用户积分
        sysUser.setIntegral(mdVolunteer.getVtIntegral());
        sysUser.setDelFlag("0");
        sysUserService.updateUserByOpid(sysUser.getId(), sysUser);
        sysUserService.updateById(sysUser);
        mdVolunteerService.updateById(mdVolunteer);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "志愿者-通过id删除")
    @ApiOperation(value = "志愿者-通过id删除", notes = "志愿者-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        mdVolunteerService.removeById(id);
        MdVolunteer mdVolunteer = mdVolunteerService.getById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "志愿者-批量删除")
    @ApiOperation(value = "志愿者-批量删除", notes = "志愿者-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.mdVolunteerService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "志愿者-通过id查询")
    @ApiOperation(value = "志愿者-通过id查询", notes = "志愿者-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        MdVolunteer mdVolunteer = mdVolunteerService.getById(id);
        if (mdVolunteer == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(mdVolunteer);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param mdVolunteer
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MdVolunteer mdVolunteer) {
        return super.exportXls(request, mdVolunteer, MdVolunteer.class, "志愿者");
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
        return super.importExcel(request, response, MdVolunteer.class);
    }

    void putThread() throws Exception {
        arrayBlockingQueue.put("1");
    }

    void pollThread() throws Exception {
        arrayBlockingQueue.poll();
    }
}
