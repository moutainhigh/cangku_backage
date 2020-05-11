package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/1/20 17:26
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
@Table(name = "order_check")
@Builder
public class OrderCheck {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Column(name = "order_code",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "订单号")
    @Unique
    private String orderCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time",type =MySqlTypeConstant.DATETIME,comment = "创建时间")
    private Date createTime;

    @Column(name = "handle_sts",type = MySqlTypeConstant.INT,length = 2,defaultValue = "1",comment = "处理状态: 1:未处理 2.已处理")
    private Integer handleSts;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "distributor_time",type =MySqlTypeConstant.DATETIME,comment = "分销账单时间")
    private Date distributorTime;
}
