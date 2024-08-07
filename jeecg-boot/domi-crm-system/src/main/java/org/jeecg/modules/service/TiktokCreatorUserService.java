package org.jeecg.modules.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.entity.TiktokCreatorUser;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface TiktokCreatorUserService extends IService<TiktokCreatorUser> {
   
    List<TiktokCreatorUser> listByIds(List<String> ids); ;
    
}
