package org.jeecg.modules.demo.clapwill.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.demo.appeal.entity.MdAppealInfo;
import org.jeecg.modules.demo.appeal.vo.MdAppealInfoPage;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.clapwill.entity.MdCwInfo;
import org.jeecg.modules.demo.clapwill.entity.MdClapWillInfo;
import org.jeecg.modules.demo.clapwill.vo.MdClapWillInfoPage;
import org.jeecg.modules.demo.clapwill.service.IMdClapWillInfoService;
import org.jeecg.modules.demo.clapwill.service.IMdCwInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 随手拍管理
 * @Author: jeecg-boot
 * @Date:   2020-05-02
 * @Version: V1.0
 */
@Api(tags="随手拍管理")
@RestController
@RequestMapping("/clapwill/mdClapWillInfo")
@Slf4j
public class MdClapWillInfoController {
	@Autowired
	private IMdClapWillInfoService mdClapWillInfoService;
	@Autowired
	private IMdCwInfoService mdCwInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param mdClapWillInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "随手拍管理-分页列表查询")
	@ApiOperation(value="随手拍管理-分页列表查询", notes="随手拍管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MdClapWillInfo mdClapWillInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MdClapWillInfo> queryWrapper = QueryGenerator.initQueryWrapper(mdClapWillInfo, req.getParameterMap());
		Page<MdClapWillInfo> page = new Page<MdClapWillInfo>(pageNo, pageSize);
		IPage<MdClapWillInfo> pageList = mdClapWillInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param mdClapWillInfoPage
	 * @return
	 */
	@AutoLog(value = "随手拍管理-添加")
	@ApiOperation(value="随手拍管理-添加", notes="随手拍管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MdClapWillInfoPage mdClapWillInfoPage) {
		MdClapWillInfo mdClapWillInfo = new MdClapWillInfo();
		BeanUtils.copyProperties(mdClapWillInfoPage, mdClapWillInfo);
		mdClapWillInfoPage.setCwReplyStatus("未回复");
		mdClapWillInfoService.saveMain(mdClapWillInfo, mdClapWillInfoPage.getMdCwInfoList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param mdClapWillInfoPage
	 * @return
	 */
	@AutoLog(value = "随手拍管理-编辑")
	@ApiOperation(value="随手拍管理-编辑", notes="随手拍管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MdClapWillInfoPage mdClapWillInfoPage) {
		MdClapWillInfo mdClapWillInfo = new MdClapWillInfo();
		BeanUtils.copyProperties(mdClapWillInfoPage, mdClapWillInfo);
		MdClapWillInfo mdClapWillInfoEntity = mdClapWillInfoService.getById(mdClapWillInfo.getId());
		if(mdClapWillInfoEntity==null) {
			return Result.error("未找到对应数据");
		}
		mdClapWillInfoService.updateMain(mdClapWillInfo, mdClapWillInfoPage.getMdCwInfoList());
		return Result.ok("编辑成功!");
	}

	 /**
	  *  回复内容
	  *
	  * @param mdClapWillInfoPage
	  * @return
	  */
	 @AutoLog(value = "随手拍管理-回复")
	 @ApiOperation(value="随手拍管理-回复", notes="随手拍管理-回复")
	 @PutMapping(value = "/reply")
	 public Result<?> reply(@RequestBody MdClapWillInfoPage mdClapWillInfoPage) {
		 MdClapWillInfo mdClapWillInfo = new MdClapWillInfo();
		 BeanUtils.copyProperties(mdClapWillInfoPage, mdClapWillInfo);
		 mdClapWillInfoPage.setCwReplyStatus("已回复");
		 mdClapWillInfo.setCwReplyStatus("已回复");//
		 mdClapWillInfo.setCwReleaseTime(DateUtils.getDate("yyyy-MM-dd hh:mm:ss"));
		 mdClapWillInfoService.updateMain(mdClapWillInfo, mdClapWillInfoPage.getMdCwInfoList());
		 return Result.ok("回复成功!");
	 }

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "随手拍管理-通过id删除")
	@ApiOperation(value="随手拍管理-通过id删除", notes="随手拍管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		mdClapWillInfoService.delMain(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "随手拍管理-批量删除")
	@ApiOperation(value="随手拍管理-批量删除", notes="随手拍管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.mdClapWillInfoService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "随手拍管理-通过id查询")
	@ApiOperation(value="随手拍管理-通过id查询", notes="随手拍管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MdClapWillInfo mdClapWillInfo = mdClapWillInfoService.getById(id);
		if(mdClapWillInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(mdClapWillInfo);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "随手怕图片管理集合-通过id查询")
	@ApiOperation(value="随手怕图片管理集合-通过id查询", notes="随手怕图片管理-通过id查询")
	@GetMapping(value = "/queryMdCwInfoByMainId")
	public Result<?> queryMdCwInfoListByMainId(@RequestParam(name="id",required=true) String id) {
		List<MdCwInfo> mdCwInfoList = mdCwInfoService.selectByMainId(id);
		return Result.ok(mdCwInfoList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mdClapWillInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MdClapWillInfo mdClapWillInfo) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<MdClapWillInfo> queryWrapper = QueryGenerator.initQueryWrapper(mdClapWillInfo, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<MdClapWillInfo> queryList = mdClapWillInfoService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<MdClapWillInfo> mdClapWillInfoList = new ArrayList<MdClapWillInfo>();
      if(oConvertUtils.isEmpty(selections)) {
          mdClapWillInfoList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          mdClapWillInfoList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<MdClapWillInfoPage> pageList = new ArrayList<MdClapWillInfoPage>();
      for (MdClapWillInfo main : mdClapWillInfoList) {
          MdClapWillInfoPage vo = new MdClapWillInfoPage();
          BeanUtils.copyProperties(main, vo);
          List<MdCwInfo> mdCwInfoList = mdCwInfoService.selectByMainId(main.getId());
          vo.setMdCwInfoList(mdCwInfoList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "随手拍管理列表");
      mv.addObject(NormalExcelConstants.CLASS, MdClapWillInfoPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("随手拍管理数据", "导出人:"+sysUser.getRealname(), "随手拍管理"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<MdClapWillInfoPage> list = ExcelImportUtil.importExcel(file.getInputStream(), MdClapWillInfoPage.class, params);
              for (MdClapWillInfoPage page : list) {
                  MdClapWillInfo po = new MdClapWillInfo();
                  BeanUtils.copyProperties(page, po);
                  mdClapWillInfoService.saveMain(po, page.getMdCwInfoList());
              }
              return Result.ok("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
    }

}
