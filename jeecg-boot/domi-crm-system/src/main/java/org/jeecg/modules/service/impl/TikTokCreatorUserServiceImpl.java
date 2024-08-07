package org.jeecg.modules.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.entity.TiktokCreatorUser;
import org.jeecg.modules.mapper.TiktokCreatorUserMapper;
import org.jeecg.modules.service.TiktokCreatorUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
@DS("multi-datasource1")
public class TikTokCreatorUserServiceImpl extends ServiceImpl<TiktokCreatorUserMapper, TiktokCreatorUser> implements TiktokCreatorUserService {
    @Resource
    private TiktokCreatorUserMapper tiktokCreatorUserMapper;
    
    @Override
    public List<TiktokCreatorUser> listByIds(List<String> ids) {
        return tiktokCreatorUserMapper.listByUserIds(ids);
    }
}
