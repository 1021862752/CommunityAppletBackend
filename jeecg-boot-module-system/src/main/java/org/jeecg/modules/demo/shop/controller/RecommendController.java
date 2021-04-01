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
import org.jeecg.modules.demo.shop.entity.Recommend;
import org.jeecg.modules.demo.shop.service.IRecommendService;
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
 * @Description: 推荐管理
 * @Author: jeecg-boot
 * @Date:   2020-07-18
 * @Version: V1.0
 */
@Slf4j
@Api(tags="(商城)推荐管理")
@RestController
@RequestMapping("/shop/recommend")
public class RecommendController extends JeecgController<Recommend, IRecommendService> {
	@Autowired
	private IRecommendService recommendService;
	
	/**
	 * 分页列表查询
	 *
	 * @param recommend
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "推荐管理-分页列表查询")
	@ApiOperation(value="推荐管理-分页列表查询", notes="推荐管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Recommend recommend,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Recommend> queryWrapper = QueryGenerator.initQueryWrapper(recommend, req.getParameterMap());
		Page<Recommend> page = new Page<Recommend>(pageNo, pageSize);
		IPage<Recommend> pageList = recommendService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param recommend
	 * @return
	 */
	@AutoLog(value = "推荐管理-添加")
	@ApiOperation(value="推荐管理-添加", notes="推荐管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Recommend recommend) {
		recommendService.save(recommend);
		return Result.ok("添加成功！");
	}
	
	/**
	 * 编辑
	 *
	 * @param recommend
	 * @return
	 */
	@AutoLog(value = "推荐管理-编辑")
	@ApiOperation(value="推荐管理-编辑", notes="推荐管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Recommend recommend) {
		recommendService.updateById(recommend);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "推荐管理-通过id删除")
	@ApiOperation(value="推荐管理-通过id删除", notes="推荐管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		recommendService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "推荐管理-批量删除")
	@ApiOperation(value="推荐管理-批量删除", notes="推荐管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.recommendService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "推荐管理-通过id查询")
	@ApiOperation(value="推荐管理-通过id查询", notes="推荐管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Recommend recommend = recommendService.getById(id);
		return Result.ok(recommend);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param recommend
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, Recommend recommend) {
      return super.exportXls(request, recommend, Recommend.class, "推荐管理");
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
      return super.importExcel(request, response, Recommend.class);
  }

}
