package org.jeecg.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.common.system.base.entity.JeecgEntity;

import java.io.Serializable;

@Data
@ApiModel("tiktok用户粉丝数据")
public class TiktokUserStats implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId("user_id")
    private String userId;
    @TableField("follower_count")
    private Integer followerCount;
    @TableField("following_count")
    private Integer followingCount;
    @TableField("heart")
    private Integer heart;
    @TableField("video_count")
    private Integer videoCount;
    @TableField("digg_count")
    private Integer diggCount;
    @TableField("friend_count")
    private Integer friendCount;
}
