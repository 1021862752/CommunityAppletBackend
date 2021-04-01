package org.jeecg.modules.demo.information.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.information.entity.MdInformationInfo;
import org.jeecg.modules.demo.information.service.IMdInformationInfoService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.model.AnnouncementSendModel;
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
 * @Description: 资讯管理
 * @Author: jeecg-boot
 * @Date: 2020-05-07
 * @Version: V1.0
 */
@Api(tags = "资讯管理")
@RestController
@RequestMapping("/information/mdInformationInfo")
@Slf4j
public class MdInformationInfoController extends JeecgController<MdInformationInfo, IMdInformationInfoService> {
    @Autowired
    private IMdInformationInfoService mdInformationInfoService;

    /**
     * 分页列表查询
     *
     * @param mdInformationInfo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "资讯管理-分页列表查询")
    @ApiOperation(value = "资讯管理-分页列表查询", notes = "资讯管理-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(MdInformationInfo mdInformationInfo,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        Result<IPage<MdInformationInfo>> result = new Result<IPage<MdInformationInfo>>();
//        announcementSendModel.setUserId(userId);
//        MdInformationInfo.setPageNo((pageNo-1)*pageSize);
//        MdInformationInfo.setPageSize(pageSize);
        Page<MdInformationInfo> pageList = new Page<MdInformationInfo>(pageNo, pageSize);
        pageList = mdInformationInfoService.getInformationInfoData(pageList, mdInformationInfo);
        return Result.ok(pageList);
    }

    @AutoLog(value = "资讯管理-分页列表查询(界面)")
    @ApiOperation(value = "资讯管理-分页列表查询(界面)", notes = "资讯管理-分页列表查询(界面)")
    @GetMapping(value = "/listWeb")
    public Result<?> listWeb(MdInformationInfo mdInformationInfo,
                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                             @RequestParam(name = "pageSize", defaultValue = "2") Integer pageSize,
                             HttpServletRequest req) {
        QueryWrapper<MdInformationInfo> queryWrapper = QueryGenerator.initQueryWrapper(mdInformationInfo, req.getParameterMap());
        Page<MdInformationInfo> page = new Page<MdInformationInfo>(pageNo, pageSize);
        IPage<MdInformationInfo> pageList = mdInformationInfoService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 添加
     *
     * @param mdInformationInfo
     * @return
     */
    @AutoLog(value = "资讯管理-添加")
    @ApiOperation(value = "资讯管理-添加", notes = "资讯管理-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody MdInformationInfo mdInformationInfo) {
        mdInformationInfo.setInReleaseTime(new Date());
        mdInformationInfo.setInStatus("已发布");
        mdInformationInfoService.save(mdInformationInfo);
        return Result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param mdInformationInfo
     * @return
     */
    @AutoLog(value = "资讯管理-编辑")
    @ApiOperation(value = "资讯管理-编辑", notes = "资讯管理-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody MdInformationInfo mdInformationInfo) {
        mdInformationInfoService.updateById(mdInformationInfo);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "资讯管理-通过id删除")
    @ApiOperation(value = "资讯管理-通过id删除", notes = "资讯管理-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        mdInformationInfoService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "资讯管理-批量删除")
    @ApiOperation(value = "资讯管理-批量删除", notes = "资讯管理-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.mdInformationInfoService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "资讯管理-通过id查询")
    @ApiOperation(value = "资讯管理-通过id查询", notes = "资讯管理-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        MdInformationInfo mdInformationInfo = mdInformationInfoService.getById(id);
        if (mdInformationInfo == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(mdInformationInfo);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param mdInformationInfo
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MdInformationInfo mdInformationInfo) {
        return super.exportXls(request, mdInformationInfo, MdInformationInfo.class, "资讯管理");
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
        return super.importExcel(request, response, MdInformationInfo.class);
    }

}
