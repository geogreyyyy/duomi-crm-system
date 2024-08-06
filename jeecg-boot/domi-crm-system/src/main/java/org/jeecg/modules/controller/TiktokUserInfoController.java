package org.jeecg.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.entity.TiktokUserInfo;
import org.jeecg.modules.service.TiktokUserInfoService;
import org.jeecg.modules.vo.resp.TikTokUserInfoResp;
import org.jeecg.modules.vo.resp.TikTokUserListResp;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Api("tiktok用户信息")
@Slf4j
@RestController
@RequestMapping("/tiktok_user_info")
public class TiktokUserInfoController {
    @Resource
    private TiktokUserInfoService tiktokUserInfoService;


    @ApiOperation("获取tiktok用户信息")
    @GetMapping("/list")
    @AutoLog(value = "请求tiktok用户")
    public Result< IPage<TikTokUserListResp>> getTiktokUserInfo(TiktokUserInfo tiktokUserInfo ,
                                                                @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                                @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                                HttpServletRequest req) {
        QueryWrapper<TiktokUserInfo> queryWrapper = QueryGenerator.initQueryWrapper(tiktokUserInfo, req.getParameterMap());
        queryWrapper.orderByDesc("create_time");
        Page<TiktokUserInfo> page = new Page<>(pageNo,pageSize);
        IPage<TikTokUserListResp> tiktokUserInfoIPage =  tiktokUserInfoService.getPage(page, queryWrapper);
        return Result.ok(tiktokUserInfoIPage);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    @ApiOperation(value = "通过ID查询达人数据", notes = "通过ID查询达人数据")
    public Result<?> queryById(@ApiParam(name = "id", value = "示例id", required = true) @RequestParam(name = "id",
            required = true) Long id) {
        TikTokUserInfoResp userInfoResp = tiktokUserInfoService.getUserInfoById(id);
        return Result.OK(userInfoResp);
    }
}
