package org.jeecg.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.entity.TiktokUserInfo;
import org.springframework.web.bind.annotation.RequestParam;

public interface TiktokUserInfoMapper extends BaseMapper<TiktokUserInfo> {

    public IPage<TiktokUserInfo> getPage(Page<TiktokUserInfo> page, @RequestParam("tiktokUserInfo") TiktokUserInfo tiktokUserInfo);
}
