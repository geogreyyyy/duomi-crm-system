package org.jeecg.modules.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.entity.TiktokCreatorUser;
import org.jeecg.modules.entity.TiktokUserInfo;
import org.jeecg.modules.service.TiktokCreatorUserService;
import org.jeecg.modules.service.TiktokUserInfoService;
import org.jeecg.modules.vo.ExportTikTokUser;
import org.jeecg.modules.vo.resp.TikTokUserInfoResp;
import org.jeecg.modules.vo.resp.TikTokUserListResp;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Api("tiktok用户信息")
@Slf4j
@RestController
@RequestMapping("/tiktok_user_info")
public class TiktokUserInfoController {
    @Resource
    private TiktokUserInfoService tiktokUserInfoService;
    @Resource
    private TiktokCreatorUserService creatorUserService;
    
    private static void validParams(HttpServletRequest req, QueryWrapper<TiktokUserInfo> queryWrapper) {
        if (req.getParameter("haveEmail") != null) {
            String haveEmali = req.getParameterMap().get("haveEmail")[0];
            if (haveEmali.equals("Y")) {
                queryWrapper.ne("signature", "");
            }
        }
        if (req.getParameter("haveBioLink") != null) {
            String haveEmali = req.getParameterMap().get("haveBioLink")[0];
            if (haveEmali.equals("Y")) {
                queryWrapper.ne("bio_link", "");
            }
        }
    }
    
    @ApiOperation("获取tiktok用户信息")
    @GetMapping("/list")
    @AutoLog(value = "请求tiktok用户")
    public Result<IPage<TikTokUserListResp>> getTiktokUserInfo(TiktokUserInfo tiktokUserInfo,
                                                               @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                               HttpServletRequest req
    ) {
        QueryWrapper<TiktokUserInfo> queryWrapper = QueryGenerator.initQueryWrapper(tiktokUserInfo, req.getParameterMap());
        validParams(req, queryWrapper);
        queryWrapper.orderByDesc("create_time");
        Page<TiktokUserInfo> page = new Page<>(pageNo, pageSize);
        IPage<TikTokUserListResp> tiktokUserInfoIPage = tiktokUserInfoService.getPage(page, queryWrapper);
        return Result.ok(tiktokUserInfoIPage);
    }
    
    /**
     * 通过id查询
     */
    @GetMapping(value = "/queryById")
    @ApiOperation(value = "通过ID查询达人数据", notes = "通过ID查询达人数据")
    public Result<?> queryById(@ApiParam(name = "id", value = "示例id", required = true) @RequestParam(name = "id",
            required = true) String id
    ) {
        TikTokUserInfoResp userInfoResp = tiktokUserInfoService.getUserInfoById(id);
        TiktokCreatorUser tiktokCreatorUser = userInfoResp.getCreatorUserInfo();
        JSONObject jsonObject = JSON.parseObject(tiktokCreatorUser.getIndustryGroups());
        log.info(jsonObject.toJSONString());
        return Result.OK(userInfoResp);
    }
    
    
    /**
     * 导出excel
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
        // Step.1 组装查询条件
        QueryWrapper<TiktokUserInfo> queryWrapper = null;
        try {
            String paramsStr = request.getParameter("paramsStr");
            if (oConvertUtils.isNotEmpty(paramsStr)) {
                String deString = URLDecoder.decode(paramsStr, "UTF-8");
                TiktokUserInfo tiktokUserInfo = JSON.parseObject(deString, TiktokUserInfo.class);
                queryWrapper = QueryGenerator.initQueryWrapper(tiktokUserInfo, request.getParameterMap());
                validParams(request, queryWrapper);
            }
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        
        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<TiktokUserInfo> pageList = tiktokUserInfoService.list(queryWrapper);
        List<String> userIdList = pageList.stream().map(TiktokUserInfo::getId).collect(Collectors.toList());
        Map<String, TiktokCreatorUser> creatorUserMap =
                creatorUserService.listByIds(userIdList).stream().collect(Collectors.toMap(TiktokCreatorUser::getUserId, v -> v, (v1, v2) -> v1));
        List<ExportTikTokUser> exportTikTokUsers = new ArrayList<>();
        for (TiktokUserInfo userInfo : pageList) {
            ExportTikTokUser exportTikTokUser = new ExportTikTokUser();
            BeanUtils.copyProperties(userInfo,exportTikTokUser);
            TiktokCreatorUser tiktokCreatorUser = creatorUserMap.get(userInfo.getId());
            
            extracted(exportTikTokUser, tiktokCreatorUser);
            exportTikTokUsers.add(exportTikTokUser);
        }
        LoginUser loginUser =  (LoginUser) SecurityUtils.getSubject().getPrincipal();
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "达人数据列表");
        mv.addObject(NormalExcelConstants.CLASS, ExportTikTokUser.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("流程测试列表数据", "导出人:"+loginUser.getUsername(), "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, exportTikTokUsers);
        return mv;
    }
    
    private static void extracted(ExportTikTokUser exportTikTokUser, TiktokCreatorUser tiktokCreatorUser) {
        exportTikTokUser.setUnitsSoldRange(tiktokCreatorUser.getUnitsSoldRange());
        exportTikTokUser.setSelectionRegion(tiktokCreatorUser.getSelectionRegion());
        exportTikTokUser.setMedGmvRevenueRange(tiktokCreatorUser.getMedGmvRevenueRange());
        exportTikTokUser.setCreatorBindMcnName(tiktokCreatorUser.getCreatorBindMcnName());
        exportTikTokUser.setIndustryGroups(tiktokCreatorUser.getIndustryGroups());
        exportTikTokUser.setTopFollowerAge(tiktokCreatorUser.getTopFollowerAge());
        exportTikTokUser.setTopFollowerGender(tiktokCreatorUser.getTopFollowerGender());
        exportTikTokUser.setFollowerCnt(tiktokCreatorUser.getFollowerCnt());
        exportTikTokUser.setVideoAvgViewCnt(tiktokCreatorUser.getVideoAvgViewCnt());
    }
}
