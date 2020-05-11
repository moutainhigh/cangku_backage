package cn.enn.wise.ssop.api.promotions.dto.request;

import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@ApiModel("优惠券分页参数")
public class CouponListParam extends QueryParam {

    @ApiModelProperty("主键")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("优惠券类型 1-体验券 2-满减券 3-代金券")
    private Byte couponType;

    @ApiModelProperty("状态 1 有效 2 无效")
    private Byte state;

    @ApiModelProperty("创建时间-开始")
    private Timestamp createTimeStart;

    @ApiModelProperty("创建时间-结束")
    private Timestamp createTimeEnd;

    @ApiModelProperty("操作人")
    private String operationUser;

}
