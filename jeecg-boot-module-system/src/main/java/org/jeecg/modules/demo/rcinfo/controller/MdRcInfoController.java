package org.jeecg.modules.demo.rcinfo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.rcinfo.entity.MdRcInfo;
import org.jeecg.modules.demo.rcinfo.service.IMdRcInfoService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
 * @Description: 轮播图管理
 * @Author: jeecg-boot
 * @Date:   2020-05-05
 * @Version: V1.0
 */
@Api(tags="轮播图管理")
@RestController
@RequestMapping("/rcinfo/mdRcInfo")
@Slf4j
public class MdRcInfoController extends JeecgController<MdRcInfo, IMdRcInfoService> {
	@Autowired
	private IMdRcInfoService mdRcInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param mdRcInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "轮播图管理-分页列表查询")
	@ApiOperation(value="轮播图管理-分页列表查询", notes="轮播图管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MdRcInfo mdRcInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MdRcInfo> queryWrapper = QueryGenerator.initQueryWrapper(mdRcInfo, req.getParameterMap());
		Page<MdRcInfo> page = new Page<MdRcInfo>(pageNo, pageSize);
		IPage<MdRcInfo> pageList = mdRcInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param mdRcInfo
	 * @return
	 */
	@AutoLog(value = "轮播图管理-添加")
	@ApiOperation(value="轮播图管理-添加", notes="轮播图管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MdRcInfo mdRcInfo) {
		mdRcInfoService.save(mdRcInfo);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param mdRcInfo
	 * @return
	 */
	@AutoLog(value = "轮播图管理-编辑")
	@ApiOperation(value="轮播图管理-编辑", notes="轮播图管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MdRcInfo mdRcInfo) {
		mdRcInfoService.updateById(mdRcInfo);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "轮播图管理-通过id删除")
	@ApiOperation(value="轮播图管理-通过id删除", notes="轮播图管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		mdRcInfoService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "轮播图管理-批量删除")
	@ApiOperation(value="轮播图管理-批量删除", notes="轮播图管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.mdRcInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "轮播图管理-通过id查询")
	@ApiOperation(value="轮播图管理-通过id查询", notes="轮播图管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MdRcInfo mdRcInfo = mdRcInfoService.getById(id);
		if(mdRcInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(mdRcInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mdRcInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MdRcInfo mdRcInfo) {
        return super.exportXls(request, mdRcInfo, MdRcInfo.class, "轮播图管理");
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
        return super.importExcel(request, response, MdRcInfo.class);
    }

}
