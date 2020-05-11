package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jiaby
 */
@Data
@Table(name = "promotion_and_goods")
public class PromotionAndGoods extends  TableBase{

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(name = "promotion_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "优惠券活动Id")
    @ApiModelProperty("景区Id")
    private Long promotionId;

    @Column(name = "goods_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品id")
    @ApiModelProperty("商品Id")
    private Long goodsId;
}
