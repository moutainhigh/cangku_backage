package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商品和佰邦达做关联
 *
 * @author jiabiaye
 * @since 2020.01.03
 */
@Data
@Table(name = "goods_related_bbd")
public class GoodsRelatedBBD extends  TableBase{

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "goods_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品id")
    @ApiModelProperty("商品Id")
    private Long goodsId;

    @Column(name = "bbd_goods_id",type = MySqlTypeConstant.VARCHAR, length = 200,comment = "佰邦达商品id")
    @ApiModelProperty("佰邦达商品id")
    private String bbdGoodsId;

    @Column(name = "goods_day",type = MySqlTypeConstant.VARCHAR, length = 40,comment = "商品售卖日期 格式2020-01-15")
    @ApiModelProperty("佰邦达商品id")
    private String goodsDay;

}

