package org.jeecg.modules.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.entity.TiktokCreatorUser;
import org.jeecg.modules.entity.TiktokUserInfo;
import org.jeecg.modules.entity.TiktokUserStats;
import org.jeecg.modules.mapper.TiktokCreatorUserMapper;
import org.jeecg.modules.mapper.TiktokUserInfoMapper;
import org.jeecg.modules.mapper.TiktokUserStatsMapper;
import org.jeecg.modules.service.TiktokUserInfoService;
import org.jeecg.modules.vo.resp.TikTokUserInfoResp;
import org.jeecg.modules.vo.resp.TikTokUserListResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@DS("multi-datasource1")
public class TikTokUserInfoServiceImpl extends ServiceImpl<TiktokUserInfoMapper, TiktokUserInfo> implements TiktokUserInfoService {
    @Resource
    private TiktokUserInfoMapper mapper;
    @Resource
    private TiktokUserStatsMapper statsMapper;
    @Resource
    private TiktokCreatorUserMapper creatorUserMapper;

    @Override
    public IPage<TikTokUserListResp> getPage(Page<TiktokUserInfo> page, QueryWrapper<TiktokUserInfo> queryWrapper) {
        IPage<TiktokUserInfo> tiktokUserInfoIPage = super.page(page,queryWrapper);
        List<String> userIdList =
                tiktokUserInfoIPage.getRecords().stream().map(TiktokUserInfo::getId).collect(Collectors.toList());
        List<TiktokUserStats> statsList = statsMapper.selectBatchIds(userIdList);
        Map<String,TiktokUserStats> statsMap = statsList.stream().collect(Collectors.toMap(TiktokUserStats::getUserId,v->v
                ,(v1,v2) -> v1));
        IPage<TikTokUserListResp> tiktokUserInfoIPage1 = new Page<>();
        List<TikTokUserListResp> resps = tiktokUserInfoIPage.getRecords().stream().map(item->{
            TikTokUserListResp tiktokUserListResp = new TikTokUserListResp();
            BeanUtils.copyProperties(item, tiktokUserListResp);
            TiktokUserStats stats = statsMap.get(item.getId());
            tiktokUserListResp.setFollowerCount(stats.getFollowerCount());
            tiktokUserListResp.setFollowingCount(stats.getFollowingCount());
            tiktokUserListResp.setHeart(stats.getHeart());
            tiktokUserListResp.setDiggCount(stats.getDiggCount());
            tiktokUserListResp.setHeart(stats.getHeart());
            tiktokUserListResp.setFriendCount(stats.getFriendCount());
            tiktokUserListResp.setVideoCount(stats.getVideoCount());
            return tiktokUserListResp;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(tiktokUserInfoIPage,tiktokUserInfoIPage1);
        tiktokUserInfoIPage1.setRecords(resps);
        return tiktokUserInfoIPage1;
    }

    @Override
    public TikTokUserInfoResp getUserInfoById(String id) {
        TiktokUserInfo userInfo = super.getById(id);
        if (ObjectUtil.hasEmpty(userInfo)) {
            return null;
        }
        TiktokUserStats userStats = statsMapper.selectById(userInfo.getId());
        TiktokCreatorUser creatorUserInfo = creatorUserMapper.selectById(id);
        TikTokUserInfoResp userInfoResp = new TikTokUserInfoResp();
        userInfoResp.setCreatorUserInfo(creatorUserInfo);
        userInfoResp.setUserInfo(userInfo);
        userInfoResp.setUserStats(userStats);
        return userInfoResp;
    }
}
