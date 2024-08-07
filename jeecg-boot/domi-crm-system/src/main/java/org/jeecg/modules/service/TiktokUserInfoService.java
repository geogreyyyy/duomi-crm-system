package org.jeecg.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.entity.TiktokUserInfo;
import org.jeecg.modules.vo.ExportTikTokUser;
import org.jeecg.modules.vo.resp.TikTokUserInfoResp;
import org.jeecg.modules.vo.resp.TikTokUserListResp;

import java.util.List;

public interface TiktokUserInfoService extends IService<TiktokUserInfo> {
    IPage<TikTokUserListResp> getPage(Page<TiktokUserInfo> page, QueryWrapper<TiktokUserInfo> queryWrapper);
    
    TikTokUserInfoResp getUserInfoById(String id);
    
}
