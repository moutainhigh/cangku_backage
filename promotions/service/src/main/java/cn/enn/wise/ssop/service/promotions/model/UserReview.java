package cn.enn.wise.ssop.service.promotions.model;


import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户回访
 * @author yangshuaiquan
 */
@Data
@Table(name = "user_review")
public class UserReview extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "用户id")
    @Column(name = "user_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "用户id")
    private Long userId;

    @Column(name = "activity_base_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "活动id")
    @ApiModelProperty("活动id")
    private Long activityBaseId;

    @Column(name = "review_info",type = MySqlTypeConstant.TEXT, comment = "回访内容")
    @ApiModelProperty("回访内容")
    private String reviewInfo;
}
