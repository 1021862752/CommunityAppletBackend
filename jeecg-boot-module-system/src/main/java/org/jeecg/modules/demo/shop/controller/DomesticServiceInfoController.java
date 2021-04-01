package org.jeecg.modules.demo.shop.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.shop.entity.DomesticServiceInfo;
import org.jeecg.modules.demo.shop.service.IDomesticServiceInfoService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 家政服务列表
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
@Slf4j
@Api(tags="(商城)家政服务列表")
@RestController
@RequestMapping("/shop/domesticServiceInfo")
public class DomesticServiceInfoController extends JeecgController<DomesticServiceInfo, IDomesticServiceInfoService> {
	@Autowired
	private IDomesticServiceInfoService domesticServiceInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param domesticServiceInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "家政服务列表-分页列表查询")
	@ApiOperation(value="家政服务列表-分页列表查询", notes="家政服务列表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DomesticServiceInfo domesticServiceInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DomesticServiceInfo> queryWrapper = QueryGenerator.initQueryWrapper(domesticServiceInfo, req.getParameterMap());
		Page<DomesticServiceInfo> page = new Page<DomesticServiceInfo>(pageNo, pageSize);
		IPage<DomesticServiceInfo> pageList = domesticServiceInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param domesticServiceInfo
	 * @return
	 */
	@AutoLog(value = "家政服务列表-添加")
	@ApiOperation(value="家政服务列表-添加", notes="家政服务列表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DomesticServiceInfo domesticServiceInfo) {
		domesticServiceInfoService.save(domesticServiceInfo);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param domesticServiceInfo
	 * @return
	 */
	@AutoLog(value = "家政服务列表-编辑")
	@ApiOperation(value="家政服务列表-编辑", notes="家政服务列表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DomesticServiceInfo domesticServiceInfo) {
		domesticServiceInfoService.updateById(domesticServiceInfo);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "家政服务列表-通过id删除")
	@ApiOperation(value="家政服务列表-通过id删除", notes="家政服务列表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		domesticServiceInfoService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "家政服务列表-批量删除")
	@ApiOperation(value="家政服务列表-批量删除", notes="家政服务列表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.domesticServiceInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "家政服务列表-通过id查询")
	@ApiOperation(value="家政服务列表-通过id查询", notes="家政服务列表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DomesticServiceInfo domesticServiceInfo = domesticServiceInfoService.getById(id);
		return Result.ok(domesticServiceInfo);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param domesticServiceInfo
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, DomesticServiceInfo domesticServiceInfo) {
      return super.exportXls(request, domesticServiceInfo, DomesticServiceInfo.class, "家政服务列表");
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
      return super.importExcel(request, response, DomesticServiceInfo.class);
  }

}
