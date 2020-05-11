package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目信息VO
 *
 * @author baijie
 * @date 2019-11-05
 */
@Data
@ApiModel("项目CMS信息VO")
public class ProjectInfoVo {

    @ApiModelProperty("项目Id")
    private Long projectId;

    @ApiModelProperty("项目ID")
    private String code;

    @ApiModelProperty("图文介绍")
    private String content;

    @ApiModelProperty("图集多张图片用逗号隔开")
    private String coverUrl;

    @ApiModelProperty("项目简介")
    private String description;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("使用须知")
    private String instructions;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("标题")
    private String title;

    /**
     * 获取头图
     * @return
     */
    public String getHeadImage() {
        if (StringUtils.isNotEmpty(this.getCoverUrl())) {
            return this.getCoverUrl().split(",")[0];
        }
        return null;
    }

    /**
     * 获取图片集合
     * @return
     */
    public List<String> getImageList(){
        if (StringUtils.isNotEmpty(this.getCoverUrl())) {
            List<String> imageList = new ArrayList<>();
            String[] split = this.getCoverUrl().split(",");
            for (String s : split) {
                imageList.add(s);
            }
            return imageList;
        }
        return null;

    }
}
