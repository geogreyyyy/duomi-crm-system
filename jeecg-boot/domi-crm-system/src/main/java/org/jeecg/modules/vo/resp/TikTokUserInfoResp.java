package org.jeecg.modules.vo.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.jeecg.modules.entity.TiktokCreatorUserInfo;
import org.jeecg.modules.entity.TiktokUserInfo;
import org.jeecg.modules.entity.TiktokUserStats;

@Data
@ApiModel("达人信息详情返回类")
public class TikTokUserInfoResp {
    private TiktokUserInfo userInfo;
    private TiktokUserStats userStats;
    private TiktokCreatorUserInfo creatorUserInfo;
}
