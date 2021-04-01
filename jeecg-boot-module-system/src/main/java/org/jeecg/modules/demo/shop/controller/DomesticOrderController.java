package org.jeecg.modules.demo.shop.controller;

import java.util.Arrays;
import java.util.HashMap;
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
import org.jeecg.common.util.IPUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.shop.entity.BookingOrder;
import org.jeecg.modules.demo.shop.entity.DomesticOrder;
import org.jeecg.modules.demo.shop.service.IDomesticOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.demo.wechat.pay.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 家政订单管理
 * @Author: jeecg-boot
 * @Date:   2020-07-19
 * @Version: V1.0
 */
@Slf4j
@Api(tags="(商城)家政订单管理")
@RestController
@RequestMapping("/shop/domesticOrder")
public class DomesticOrderController extends JeecgController<DomesticOrder, IDomesticOrderService> {
	@Autowired
	private IDomesticOrderService domesticOrderService;
	@Autowired
	private MyWxPayConfig config;

	/**
	 * 分页列表查询
	 *
	 * @param domesticOrder
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "家政订单管理-分页列表查询")
	@ApiOperation(value="家政订单管理-分页列表查询", notes="家政订单管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DomesticOrder domesticOrder,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		domesticOrder.setGoodsName("*"+domesticOrder.getGoodsName()+"*");
		QueryWrapper<DomesticOrder> queryWrapper = QueryGenerator.initQueryWrapper(domesticOrder, req.getParameterMap());
		Page<DomesticOrder> page = new Page<DomesticOrder>(pageNo, pageSize);
		IPage<DomesticOrder> pageList = domesticOrderService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 * 添加
	 *
	 * @param domesticOrder
	 * @return
	 */
	@AutoLog(value = "家政订单管理-添加")
	@ApiOperation(value="家政订单管理-添加", notes="家政订单管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DomesticOrder domesticOrder) {
		domesticOrderService.save(domesticOrder);
		return Result.ok(domesticOrder);
	}
	
	/**
	 * 编辑
	 *
	 * @param domesticOrder
	 * @return
	 */
	@AutoLog(value = "家政订单管理-编辑")
	@ApiOperation(value="家政订单管理-编辑", notes="家政订单管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DomesticOrder domesticOrder) {
		domesticOrder.setGoodsName(null);
		domesticOrderService.updateById(domesticOrder);
		return Result.ok("编辑成功!");
	}
	
	/**
	 * 通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "家政订单管理-通过id删除")
	@ApiOperation(value="家政订单管理-通过id删除", notes="家政订单管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		domesticOrderService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 * 批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "家政订单管理-批量删除")
	@ApiOperation(value="家政订单管理-批量删除", notes="家政订单管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.domesticOrderService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "家政订单管理-通过id查询")
	@ApiOperation(value="家政订单管理-通过id查询", notes="家政订单管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DomesticOrder domesticOrder = domesticOrderService.getById(id);
		return Result.ok(domesticOrder);
	}

  /**
   * 导出excel
   *
   * @param request
   * @param domesticOrder
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, DomesticOrder domesticOrder) {
      return super.exportXls(request, domesticOrder, DomesticOrder.class, "家政订单管理");
  }

  /**
   * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel")
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      return super.importExcel(request, response, DomesticOrder.class);
  }
	 @GetMapping("/toPay")
	 public Result<?> toPay(@RequestParam(name = "id", required = true) String id, @RequestParam(name = "openid", required = true) String openid, HttpServletRequest request) {
		 DomesticOrder domesticOrder = domesticOrderService.getById(id);
		 try {
			 if(domesticOrder != null && domesticOrder.getOrderState() == 2) {
				 WXPay wxpay = new WXPay(config);
				 Map<String, String> data = new HashMap<String, String>();
				 data.put("body", domesticOrder.getCustomerName() + "-家政订单支付");
				 String outTradeNo = DigestUtils.md5DigestAsHex((domesticOrder.getId()+"-"+System.currentTimeMillis()).getBytes());
				 data.put("out_trade_no", outTradeNo);
				 data.put("fee_type", "CNY");
				 data.put("total_fee", String.valueOf((int) (domesticOrder.getCost() * 100)));
				 data.put("spbill_create_ip", IPUtils.getIpAddr(request));
				 data.put("notify_url", config.getCallback(domesticOrder.getId()));
				 data.put("trade_type", "JSAPI");  // 此处指定为小程序支付
				 data.put("openid", openid);
				 Map<String, String> resp = wxpay.unifiedOrder(data);
				 log.info("------统一支付接口响应数据------" + resp.toString());
				 if ("SUCCESS".equals(resp.get("return_code")) && "SUCCESS".equals(resp.get("result_code"))) {
					 Map<String, String> result = new HashMap<>();
					 String appId = resp.get("appid");
					 String timeStamp = String.valueOf(System.currentTimeMillis());
					 String nonceStr = resp.get("nonce_str");
					 String packageStr = "prepay_id=" + resp.get("prepay_id");
					 result.put("appId", appId);
					 result.put("timeStamp", timeStamp);
					 result.put("nonceStr", nonceStr);
					 result.put("package", packageStr);
					 result.put("signType", WXPayConstants.HMACSHA256);
					 String paySign =  WXPayUtil.generateSignature(result, config.pkey(), WXPayConstants.SignType.HMACSHA256);
					 result.put("paySign", paySign);
					 result.remove("appId");
					 DomesticOrder upOrder = new DomesticOrder();
					 upOrder.setId(domesticOrder.getId());
					 upOrder.setPayOrderId(resp.get("prepay_id"));
					 domesticOrderService.updateById(upOrder);
					 log.info("------统一支付接口处理正常------");
					 return Result.ok(result);
				 } else {
					 log.info("------统一支付接口处理失败------" + resp.get("return_msg"));
					 return Result.error(resp.get("return_msg"));
				 }
			 }else{
				 log.info("------统一支付接口订单状态错误------");
				 return Result.error("订单状态错误");
			 }
		 } catch (Exception e) {
			 e.printStackTrace();
			 log.info("------统一支付接口响应异常------");
			 return Result.error(e.getMessage());
		 }
	 }
}
