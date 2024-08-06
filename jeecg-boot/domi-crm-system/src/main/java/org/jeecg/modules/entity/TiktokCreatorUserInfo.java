package org.jeecg.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@TableName("tiktok_creator_user_info")
public class TiktokCreatorUserInfo {
    @Id
    @TableField("user_id")
    private Long id;

    @NotNull
    @TableField("creator_oecuid")
    private Long creatorOecuid;

    @TableField("is_authorized")
    private Integer isAuthorized;

    @Size(max = 255)
    @TableField("unique_id")
    private String uniqueId;

    @Size(max = 20)
    @TableField("units_sold_range")
    private String unitsSoldRange;

    @Size(max = 10)
    @TableField("selection_region")
    private String selectionRegion;

    @Size(max = 255)
    @TableField("med_gmv_revenue_range")
    private String medGmvRevenueRange;

    @Size(max = 150)
    @TableField("creator_bind_mcn_name")
    private String creatorBindMcnName;

    @Lob
    @TableField("industry_groups")
    private String industryGroups;

    @Size(max = 150)
    @TableField("top_follower_gender")
    private String topFollowerGender;

    @Size(max = 150)
    @TableField("top_follower_ages")
    private String topFollowerAges;

    @Size(max = 20)
    @TableField("follower_cnt")
    private String followerCnt;

    @Size(max = 150)
    @TableField("top_follower_age")
    private String topFollowerAge;

    @Size(max = 20)
    @TableField("video_avg_view_cnt")
    private String videoAvgViewCnt;

    @Size(max = 120)
    @TableField("create_by")
    private String createBy;

    @TableField("create_time")
    private Instant createTime;

    @Size(max = 120)
    @TableField("update_by")
    private String updateBy;

    @TableField("update_time")
    private Instant updateTime;

}