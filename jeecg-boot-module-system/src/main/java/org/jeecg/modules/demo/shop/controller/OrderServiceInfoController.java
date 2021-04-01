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
import org.jeecg.modules.demo.shop.entity.OrderServiceInfo;
import org.jeecg.modules.demo.shop.service.IOrderServiceInfoService;
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
 * @Description: 预约项目列表
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
@Slf4j
@Api(tags="(商城)预约项目列表")
@RestController
@RequestMapping("/shop/orderServiceInfo")
public class OrderServiceInfoController extends JeecgController<OrderServiceInfo, IOrderServiceInfoService> {
	@Autowired
	private IOrderServiceInfoService orderServiceInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param orderServiceInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "预约项目列表-分页列表查询")
	@ApiOperation(value="预约项目列表-分页列表查询", notes="预约项目列表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(OrderServiceInfo orderServiceInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OrderServiceInfo> queryWrapper = QueryGenerator.initQueryWrapper(orderServiceInfo, req.getParameterMap());
		Page<OrderServiceInfo> page = new Page<OrderServiceInfo>(pageNo, pageSize);
		IPage<OrderServiceInfo> pageList = orderServiceInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param orderServiceInfo
	 * @return
	 */
	@AutoLog(value = "预约项目列表-添加")
	@ApiOperation(value="预约项目列表-添加", notes="预约项目列表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody OrderServiceInfo orderServiceInfo) {
		orderServiceInfoService.save(orderServiceInfo);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param orderServiceInfo
	 * @return
	 */
	@AutoLog(value = "预约项目列表-编辑")
	@ApiOperation(value="预约项目列表-编辑", notes="预约项目列表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody OrderServiceInfo orderServiceInfo) {
		orderServiceInfoService.updateById(orderServiceInfo);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "预约项目列表-通过id删除")
	@ApiOperation(value="预约项目列表-通过id删除", notes="预约项目列表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		orderServiceInfoService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "预约项目列表-批量删除")
	@ApiOperation(value="预约项目列表-批量删除", notes="预约项目列表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.orderServiceInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "预约项目列表-通过id查询")
	@ApiOperation(value="预约项目列表-通过id查询", notes="预约项目列表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		OrderServiceInfo orderServiceInfo = orderServiceInfoService.getById(id);
		return Result.ok(orderServiceInfo);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param orderServiceInfo
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, OrderServiceInfo orderServiceInfo) {
      return super.exportXls(request, orderServiceInfo, OrderServiceInfo.class, "预约项目列表");
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
      return super.importExcel(request, response, OrderServiceInfo.class);
  }

}
