package org.jeecg.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.entity.TiktokCreatorUser;

public interface TiktokCreatorUserMapper extends BaseMapper<TiktokCreatorUser> {
    
    TiktokCreatorUser selectById(@Param("userId") Long userId);
}
