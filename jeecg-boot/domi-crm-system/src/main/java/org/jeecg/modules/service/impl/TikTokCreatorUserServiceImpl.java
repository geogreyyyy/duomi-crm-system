package org.jeecg.modules.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.entity.TiktokCreatorUser;
import org.jeecg.modules.mapper.TiktokCreatorUserMapper;
import org.jeecg.modules.service.TiktokCreatorUserService;
import org.springframework.stereotype.Service;

@Service
@DS("multi-datasource1")
public class TikTokCreatorUserServiceImpl extends ServiceImpl<TiktokCreatorUserMapper, TiktokCreatorUser> implements TiktokCreatorUserService {

}
