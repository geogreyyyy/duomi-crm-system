package org.jeecg.modules.vo;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

@Data
public class ExportTikTokUser {
    @Excel(name = "User Id", width = 40)
    private String id;
    @Excel(name = "Unique Id", width = 40)
    private String uniqueId;
    @Excel(name = "名称", width = 40)
    private String nickname;
    @Excel(name = "邮箱", width = 40)
    private String signature;
    @Excel(name = "头像", width = 40)
    private String region;
    @Excel(name = "主页挂载", width = 40)
    private String bioLink;
    
    @Excel(name = "售出商品数量", width = 40)
    private String unitsSoldRange;
    @Excel(name = "国家", width = 40)
    private String selectionRegion;
    @Excel(name = "GMV", width = 40)
    private String medGmvRevenueRange;
    @Excel(name = "MCN机构", width = 40)
    private String creatorBindMcnName;
    @Excel(name = "创作者标签", width = 40)
    private String industryGroups;
//    @Excel(name = "粉丝性别和百分比", width = 40)
//    private String topFollowerGender;
//    @Excel(name = "粉丝年龄", width = 40)
//    private String topFollowerAge;
    @Excel(name = "粉丝数量", width = 40)
    private String followerCnt;
    @Excel(name = "视频平均观看数量", width = 40)
    private String videoAvgViewCnt;
}
