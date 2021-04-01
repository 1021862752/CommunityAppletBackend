package org.jeecg.modules.demo.appeal.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.jeecg.modules.demo.appeal.entity.MdPictureInfo;
import org.jeecg.modules.demo.appeal.entity.MdAppealInfo;
import org.jeecg.modules.demo.appeal.vo.MdAppealInfoPage;
import org.jeecg.modules.demo.appeal.service.IMdAppealInfoService;
import org.jeecg.modules.demo.appeal.service.IMdPictureInfoService;
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
 * @Description: 诉求管理
 * @Author: jeecg-boot
 * @Date:   2020-05-05
 * @Version: V1.0
 */
@Api(tags="诉求管理")
@RestController
@RequestMapping("/appeal/mdAppealInfo")
@Slf4j
public class MdAppealInfoController {
	@Autowired
	private IMdAppealInfoService mdAppealInfoService;
	@Autowired
	private IMdPictureInfoService mdPictureInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param mdAppealInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "诉求管理-分页列表查询")
	@ApiOperation(value="诉求管理-分页列表查询", notes="诉求管理-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MdAppealInfo mdAppealInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MdAppealInfo> queryWrapper = QueryGenerator.initQueryWrapper(mdAppealInfo, req.getParameterMap());
		Page<MdAppealInfo> page = new Page<MdAppealInfo>(pageNo, pageSize);
		IPage<MdAppealInfo> pageList = mdAppealInfoService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param mdAppealInfoPage
	 * @return
	 */
	@AutoLog(value = "诉求管理-添加")
	@ApiOperation(value="诉求管理-添加", notes="诉求管理-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MdAppealInfoPage mdAppealInfoPage) {
		MdAppealInfo mdAppealInfo = new MdAppealInfo();
		BeanUtils.copyProperties(mdAppealInfoPage, mdAppealInfo);
		mdAppealInfo.setAppealReplyStatus("未回复");
		mdAppealInfoService.saveMain(mdAppealInfo, mdAppealInfoPage.getMdPictureInfoList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param mdAppealInfoPage
	 * @return
	 */
	@AutoLog(value = "诉求管理-编辑")
	@ApiOperation(value="诉求管理-编辑", notes="诉求管理-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MdAppealInfoPage mdAppealInfoPage) {
		MdAppealInfo mdAppealInfo = new MdAppealInfo();
		BeanUtils.copyProperties(mdAppealInfoPage, mdAppealInfo);
		MdAppealInfo mdAppealInfoEntity = mdAppealInfoService.getById(mdAppealInfo.getId());
		if(mdAppealInfoEntity==null) {
			return Result.error("未找到对应数据");
		}
		mdAppealInfoService.updateMain(mdAppealInfo, mdAppealInfoPage.getMdPictureInfoList());
		return Result.ok("编辑成功!");
	}


	 /**
	  *  回复内容
	  *
	  * @param mdAppealInfoPage
	  * @return
	  */
	 @AutoLog(value = "诉求管理-回复")
	 @ApiOperation(value="诉求管理-回复", notes="诉求管理-回复")
	 @PutMapping(value = "/reply")
	 public Result<?> reply(@RequestBody MdAppealInfoPage mdAppealInfoPage) {
		 MdAppealInfo mdAppealInfo = new MdAppealInfo();
		 BeanUtils.copyProperties(mdAppealInfoPage, mdAppealInfo);
		 mdAppealInfoPage.setAppealReplyStatus("已回复");
		 mdAppealInfo.setAppealReplyStatus("已回复");
		 mdAppealInfoService.updateMain(mdAppealInfo, mdAppealInfoPage.getMdPictureInfoList());
		 return Result.ok("回复成功!");
	 }

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "诉求管理-通过id删除")
	@ApiOperation(value="诉求管理-通过id删除", notes="诉求管理-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		mdAppealInfoService.delMain(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "诉求管理-批量删除")
	@ApiOperation(value="诉求管理-批量删除", notes="诉求管理-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.mdAppealInfoService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "诉求管理-通过id查询")
	@ApiOperation(value="诉求管理-通过id查询", notes="诉求管理-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MdAppealInfo mdAppealInfo = mdAppealInfoService.getById(id);
		if(mdAppealInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(mdAppealInfo);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "诉求图片管理集合-通过id查询")
	@ApiOperation(value="诉求图片管理集合-通过id查询", notes="诉求图片管理-通过id查询")
	@GetMapping(value = "/queryMdPictureInfoByMainId")
	public Result<?> queryMdPictureInfoListByMainId(@RequestParam(name="id",required=true) String id) {
		List<MdPictureInfo> mdPictureInfoList = mdPictureInfoService.selectByMainId(id);
		return Result.ok(mdPictureInfoList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param mdAppealInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MdAppealInfo mdAppealInfo) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<MdAppealInfo> queryWrapper = QueryGenerator.initQueryWrapper(mdAppealInfo, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<MdAppealInfo> queryList = mdAppealInfoService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<MdAppealInfo> mdAppealInfoList = new ArrayList<MdAppealInfo>();
      if(oConvertUtils.isEmpty(selections)) {
          mdAppealInfoList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          mdAppealInfoList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<MdAppealInfoPage> pageList = new ArrayList<MdAppealInfoPage>();
      for (MdAppealInfo main : mdAppealInfoList) {
          MdAppealInfoPage vo = new MdAppealInfoPage();
          BeanUtils.copyProperties(main, vo);
          List<MdPictureInfo> mdPictureInfoList = mdPictureInfoService.selectByMainId(main.getId());
          vo.setMdPictureInfoList(mdPictureInfoList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "诉求管理列表");
      mv.addObject(NormalExcelConstants.CLASS, MdAppealInfoPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("诉求管理数据", "导出人:"+sysUser.getRealname(), "诉求管理"));
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
              List<MdAppealInfoPage> list = ExcelImportUtil.importExcel(file.getInputStream(), MdAppealInfoPage.class, params);
              for (MdAppealInfoPage page : list) {
                  MdAppealInfo po = new MdAppealInfo();
                  BeanUtils.copyProperties(page, po);
                  mdAppealInfoService.saveMain(po, page.getMdPictureInfoList());
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
