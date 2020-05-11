package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/10/29 14:03
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:评论实体
 ******************************************/
@Data
@TableName("product_comment")
public class ProdComm extends Model<ProdComm> implements Serializable {

    private static final long serialVersionUID = -238889878985290661L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Long projectId;

    private String orderId;

    private String userId;

    private String content;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date recTime;

    private Integer score;

    private Integer status;

    private Integer evaluate;

    private String prodCommLabel;



}
