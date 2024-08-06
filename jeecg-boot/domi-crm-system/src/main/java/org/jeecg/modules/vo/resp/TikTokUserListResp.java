package org.jeecg.modules.vo.resp;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.jeecg.modules.entity.TiktokUserInfo;

@Data
@ApiModel("tiktok用户列表返回类")
public class TikTokUserListResp extends TiktokUserInfo {
    private Integer followerCount;
    private Integer followingCount;
    private Integer heart;
    private Integer videoCount;
    private Integer diggCount;
    private Integer friendCount;
}
