package org.jeecg.modules.demo.shop.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.http.useragent.Browser;
import com.alibaba.fastjson.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.shop.entity.Borrow;
import org.jeecg.modules.demo.shop.service.IBorrowService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.demo.shop.service.IBorrowingGoodsInfoService;
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
 * @Description: 借用订单
 * @Author: jeecg-boot
 * @Date:   2020-07-19
 * @Version: V1.0
 */
@Slf4j
@Api(tags="(商城)借用订单")
@RestController
@RequestMapping("/shop/borrow")
public class BorrowController extends JeecgController<Borrow, IBorrowService> {
	@Autowired
	private IBorrowService borrowService;
	
	/**
	 * 分页列表查询
	 *
	 * @param borrow
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "借用订单-分页列表查询")
	@ApiOperation(value="借用订单-分页列表查询", notes="借用订单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Borrow borrow,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		borrow.setGoodsName("*"+borrow.getGoodsName()+"*");
		QueryWrapper<Borrow> queryWrapper = QueryGenerator.initQueryWrapper(borrow, req.getParameterMap());
		Page<Borrow> page = new Page<Borrow>(pageNo, pageSize);
		IPage<Borrow> pageList = borrowService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param borrow
	 * @return
	 */
	@AutoLog(value = "借用订单-添加")
	@ApiOperation(value="借用订单-添加", notes="借用订单-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Borrow borrow) {
		borrowService.save(borrow);
		JSONObject json = JSON.parseObject(borrow.getGoodsName());
		String commodityName = json.getString("commodityName");
		borrowService.updateGoodsCount(commodityName);
		return Result.ok(borrow);
	}
	
	/**
	 * 编辑
	 *
	 * @param borrow
	 * @return
	 */
	@AutoLog(value = "借用订单-编辑")
	@ApiOperation(value="借用订单-编辑", notes="借用订单-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Borrow borrow) {
		borrow.setGoodsName(null);
		borrowService.updateById(borrow);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "借用订单-通过id删除")
	@ApiOperation(value="借用订单-通过id删除", notes="借用订单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		borrowService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "借用订单-批量删除")
	@ApiOperation(value="借用订单-批量删除", notes="借用订单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.borrowService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "借用订单-通过id查询")
	@ApiOperation(value="借用订单-通过id查询", notes="借用订单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Borrow borrow = borrowService.getById(id);
		return Result.ok(borrow);
	}



  /**
   * 导出excel
   *
   * @param request
   * @param borrow
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, Borrow borrow) {
      return super.exportXls(request, borrow, Borrow.class, "借用订单");
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
      return super.importExcel(request, response, Borrow.class);
  }

}
