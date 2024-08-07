package org.jeecg.modules.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.boot.starter.lock.annotation.JLock;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
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
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    private static void formatCreatorUser(TiktokCreatorUser tiktokCreatorUser) {
        if (ObjectUtil.isEmpty(tiktokCreatorUser)) {
            return;
        }
//        MCN名称
        if (StringUtils.isNotEmpty(tiktokCreatorUser.getCreatorBindMcnName())) {
            StringBuilder text = new StringBuilder(tiktokCreatorUser.getCreatorBindMcnName().replace("'", "\"").replace("True", "true").replace("False", "false"));
            JSONObject jsonObject = JSON.parseObject(text.toString());
            if (jsonObject.containsKey("value")) {
                tiktokCreatorUser.setCreatorBindMcnName(jsonObject.getString("value"));
            } else {
                tiktokCreatorUser.setCreatorBindMcnName("");
            }
        }
//        作者标签
        if (StringUtils.isNotEmpty(tiktokCreatorUser.getIndustryGroups())) {
            StringBuilder text = new StringBuilder(tiktokCreatorUser.getIndustryGroups().replace("'", "\"").replace("True", "true").replace("False", "false"));
            
            JSONObject jsonObject = JSON.parseObject(text.toString());
            if (jsonObject.containsKey("value")) {
                JSONArray jsonArray = jsonObject.getJSONArray("value");
                text = new StringBuilder();
                for (Object o : jsonArray) {
                    JSONObject json = (JSONObject) o;
                    text.append(json.getString("name")).append(" , ");
                }
                tiktokCreatorUser.setIndustryGroups(text.toString());
            }
        } else {
            tiktokCreatorUser.setIndustryGroups("");
        }
//        主要粉丝性别
        
    }
    
    private static void extracted(ExportTikTokUser exportTikTokUser, TiktokCreatorUser tiktokCreatorUser) {
        if (ObjectUtil.isEmpty(tiktokCreatorUser)) {
            return;
        }
        exportTikTokUser.setUnitsSoldRange(tiktokCreatorUser.getUnitsSoldRange());
        exportTikTokUser.setSelectionRegion(tiktokCreatorUser.getSelectionRegion());
        exportTikTokUser.setMedGmvRevenueRange(tiktokCreatorUser.getMedGmvRevenueRange());
        exportTikTokUser.setCreatorBindMcnName(tiktokCreatorUser.getCreatorBindMcnName());
        exportTikTokUser.setIndustryGroups(tiktokCreatorUser.getIndustryGroups());
        exportTikTokUser.setFollowerCnt(tiktokCreatorUser.getFollowerCnt());
        exportTikTokUser.setVideoAvgViewCnt(tiktokCreatorUser.getVideoAvgViewCnt());
    }
    
    @ApiOperation("获取tiktok用户信息")
    @GetMapping("/list")
    @AutoLog(value = "请求tiktok用户")
    public Result<IPage<TikTokUserListResp>> getTiktokUserInfo(TiktokUserInfo tiktokUserInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req
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
    public Result<?> queryById(@ApiParam(name = "id", value = "示例id", required = true) @RequestParam(name = "id", required = true) String id
    ) {
        TikTokUserInfoResp userInfoResp = tiktokUserInfoService.getUserInfoById(id);
        formatCreatorUser(userInfoResp.getCreatorUserInfo());
        return Result.OK(userInfoResp);
    }
    
    /**
     * 导出excel
     */
    @GetMapping(value = "/exportXls")
    @JLock(lockKey = "exportExcel")
    public ModelAndView exportXls(TiktokUserInfo tiktokUserInfo, HttpServletRequest request, HttpServletResponse response) {
        // Step.1 组装查询条件
        QueryWrapper<TiktokUserInfo>  queryWrapper = QueryGenerator.initQueryWrapper(tiktokUserInfo, request.getParameterMap());
        validParams(request, queryWrapper);
        if (request.getParameter("exportLimit") != null && request.getParameter("exportPageNo") != null) {
            String limit = request.getParameterMap().get("exportLimit")[0];
            String pageNo = request.getParameterMap().get("exportPageNo")[0];
            queryWrapper.last("limit "+pageNo+","+limit);
        }else {
            queryWrapper.last("limit 1000");
        }
        log.info("开始导出");
        //Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        List<TiktokUserInfo> pageList = tiktokUserInfoService.list(queryWrapper);
        log.info("用户数据获取完成，总共:{}", pageList.size());
        List<String> userIdList = pageList.stream().map(TiktokUserInfo::getId).collect(Collectors.toList());
        Map<String, TiktokCreatorUser> creatorUserMap = getStringTiktokCreatorUserMap(userIdList);
        log.info("达人数据获取完成，总共:{}", creatorUserMap.size());
        List<ExportTikTokUser> exportTikTokUsers = new ArrayList<>();
        for (TiktokUserInfo userInfo : pageList) {
            ExportTikTokUser exportTikTokUser = new ExportTikTokUser();
            if (StringUtils.isNotEmpty(userInfo.getBioLink())) {
                String text = userInfo.getBioLink().replace("'", "\"").replace("True", "true").replace("False", "false");
                JSONObject jsonObject = JSON.parseObject(text);
                userInfo.setBioLink(jsonObject.getString("link"));
            }
            
            BeanUtils.copyProperties(userInfo, exportTikTokUser);
            TiktokCreatorUser tiktokCreatorUser = creatorUserMap.get(userInfo.getId());
            if (ObjectUtil.isNotEmpty(tiktokCreatorUser)) {
                formatCreatorUser(tiktokCreatorUser);
                extracted(exportTikTokUser, tiktokCreatorUser);
            }
            exportTikTokUsers.add(exportTikTokUser);
        }
        log.info("转化完成，总共:{}", exportTikTokUsers.size());
        log.info(exportTikTokUsers.toString());
        LoginUser loginUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        log.info("当前登录用户:{}", loginUser.getUsername());
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "达人数据列表");
        mv.addObject(NormalExcelConstants.CLASS, ExportTikTokUser.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("达人数据列表", "导出人:" + loginUser.getUsername(), "导出信息"));
        mv.addObject(NormalExcelConstants.DATA_LIST, exportTikTokUsers);
        return mv;
    }
    
    private @NotNull Map<String, TiktokCreatorUser> getStringTiktokCreatorUserMap(List<String> userIdList) {
        if (userIdList.isEmpty()) {
            throw new RuntimeException("数据为空");
        }
        List<List<String>> splitList = Lists.partition(userIdList, 1000);
        Map<String, TiktokCreatorUser> creatorUserMap = new HashMap<>();
        splitList.forEach(item -> {
            creatorUserService.listByIds(item).forEach(mapItem -> {
                creatorUserMap.put(mapItem.getUserId(), mapItem);
            });
        });
        return creatorUserMap;
    }
}
