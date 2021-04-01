package org.jeecg.modules.demo.shop.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.shop.entity.BookingOrder;
import org.jeecg.modules.demo.shop.service.IBookingOrderService;
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
 * @Description: 预约订单管理
 * @Author: jeecg-boot
 * @Date:   2020-07-20
 * @Version: V1.0
 */
@Slf4j
@Api(tags="(商城)预约订单管理")
@RestController
@RequestMapping("/shop/bookingOrder")
public class BookingOrderController extends JeecgController<BookingOrder, IBookingOrderService> {
	@Autowired
	private IBookingOrderService bookingOrderService;
	
	/**
	 * 分页列表查询
	 *
	 * @param bookingOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "预约订单管理-分页列表查询")
	@ApiOperation(value="预约订单管理-分页列表查询", notes="预约订单管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BookingOrder bookingOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		bookingOrder.setGoodsName("*"+bookingOrder.getGoodsName()+"*");
		QueryWrapper<BookingOrder> queryWrapper = QueryGenerator.initQueryWrapper(bookingOrder, req.getParameterMap());
		Page<BookingOrder> page = new Page<BookingOrder>(pageNo, pageSize);
		IPage<BookingOrder> pageList = bookingOrderService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param bookingOrder
	 * @return
	 */
	@AutoLog(value = "预约订单管理-添加")
	@ApiOperation(value="预约订单管理-添加", notes="预约订单管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BookingOrder bookingOrder) {
		bookingOrderService.save(bookingOrder);
		return Result.ok(bookingOrder);
	}
	
	/**
	 * 编辑
	 *
	 * @param bookingOrder
	 * @return
	 */
	@AutoLog(value = "预约订单管理-编辑")
	@ApiOperation(value="预约订单管理-编辑", notes="预约订单管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BookingOrder bookingOrder) {
		bookingOrder.setGoodsName(null);
		bookingOrderService.updateById(bookingOrder);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "预约订单管理-通过id删除")
	@ApiOperation(value="预约订单管理-通过id删除", notes="预约订单管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		bookingOrderService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "预约订单管理-批量删除")
	@ApiOperation(value="预约订单管理-批量删除", notes="预约订单管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.bookingOrderService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "预约订单管理-通过id查询")
	@ApiOperation(value="预约订单管理-通过id查询", notes="预约订单管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BookingOrder bookingOrder = bookingOrderService.getById(id);
		return Result.ok(bookingOrder);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param bookingOrder
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, BookingOrder bookingOrder) {
      return super.exportXls(request, bookingOrder, BookingOrder.class, "预约订单管理");
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
      return super.importExcel(request, response, BookingOrder.class);
  }

}
