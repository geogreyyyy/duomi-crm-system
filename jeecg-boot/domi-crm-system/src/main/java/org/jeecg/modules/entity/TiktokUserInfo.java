package org.jeecg.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.common.system.base.entity.JeecgEntity;

import java.io.Serializable;

@Data
@TableName("tiktok_user_info")
@ApiModel("tiktok达人信息")
public class TiktokUserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableField
    private String id;
    @TableField("unique_id")
    private String uniqueId;
    @TableField("nickname")
    private String nickname;
    @TableField("avatar_larger")
    private String avatarLarger;
    @TableField("signature")
    private String signature;
    @TableField("region")
    private String region;
    @TableField("bio_link")
    private String bioLink;
    @TableField("sec_uid")
    private String secUid;
}
