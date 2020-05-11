package cn.enn.wise.ssop.service.cms.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "product_comment")
public class ProductComment extends TableBase {


    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Column(name = "project_id",type = MySqlTypeConstant.INT,length = 11,comment = "项目Id")
    private Integer projectId;

    @Column(name = "order_id",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "订单号")
    private String orderId;

    @Column(name = "user_id",type = MySqlTypeConstant.VARCHAR,length = 100,comment = "用户Id")
    private String userId;

    @Column(name = "content",type = MySqlTypeConstant.VARCHAR,length = 200,comment = "评价内容")
    private String content;

    @Column(name = "rec_time",type =MySqlTypeConstant.DATETIME,comment = "记录时间")
    private Date recTime;

    @Column(name = "score", type = MySqlTypeConstant.INT,length = 1,comment = "星级,0-5级")
    private Integer score;

    @Column(name = "status",type = MySqlTypeConstant.INT,length = 1,comment = "是否显示，1:为显示，2:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是2,，否则1")
    private Integer status;

    @Column(name = "evaluate" ,type= MySqlTypeConstant.INT,length = 1,comment = "评价(1.很差 2.一般 3.满意 4.非常满意 5.无可挑剔)")
    private Integer evaluate;

    @Column(name = "prod_comm_label",type=MySqlTypeConstant.VARCHAR,length = 200,comment = "评价标签")
    private String prodCommLabel;

}