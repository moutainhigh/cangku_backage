package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/1/13 17:44
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
@Table(name = "distribute_bind_user")
public class DistributeBindUser {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Column(name = "distribute_phone",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "分销商手机号")
    private String distributePhone;

    @Column(name = "user_id",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "用户ID")
    private String userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "bind_time",type =MySqlTypeConstant.DATETIME,comment = "绑定时间")
    private Date bindTime;

}
