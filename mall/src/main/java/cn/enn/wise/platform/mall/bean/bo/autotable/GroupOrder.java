package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 拼团表
 *
 * @author baijie
 * @date 2019-09-11
 */
@Data
@Table(name = "group_order")
@NoArgsConstructor
@AllArgsConstructor
public class GroupOrder extends Model<GroupOrder> {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(name = "group_promotion_id",type = MySqlTypeConstant.BIGINT, length = 20, comment = "外键,指向活动id")
    private Long groupPromotionId;

    @Column(name = "group_order_code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "拼团编码")
    @Unique
    private String groupOrderCode;

    @Column(name = "goods_num",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "商品编码")
    private String goodsNum;

    @Column(name = "goods_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品id")
    private Long goodsId;

    @Column(name = "create_time",type = MySqlTypeConstant.TIMESTAMP,comment = "创建时间")
    private Timestamp createTime;

    @Column(name = "group_size",type = MySqlTypeConstant.INT,length = 11,comment = "成团人数")
    private Integer groupSize;

    @Column(name = "status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "状态(1待成团 2 拼团中 3拼团成功 4拼团失败)")
    private Byte status;

    @Column(name = "end_time",type = MySqlTypeConstant.TIMESTAMP,comment = "拼团结束时间")
    private Timestamp endTime;

    @Column(name = "available_time",type = MySqlTypeConstant.TIMESTAMP,comment = "拼团有效时间")
    private Timestamp availableTime;

    @Column(name = "user_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "团长用户id")
    private Long userId;

}
