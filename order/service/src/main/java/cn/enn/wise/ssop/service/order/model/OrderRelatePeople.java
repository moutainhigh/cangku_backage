package cn.enn.wise.ssop.service.order.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;



@Data
@Table(name="order_relate_people")
public class OrderRelatePeople extends TableBase {


    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id ;

    @Column(name = "order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "订单id")
    private Long orderId;

    @Column(name = "member_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "会员id")
    private Long memberId;

    @Column(name = "customer_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "联系人id")
    private Long customerId;

    @Column(name = "customer_name",type = MySqlTypeConstant.VARCHAR,length = 20,comment = "客人姓名")
    private String customerName;

    @Column(name = "phone",type = MySqlTypeConstant.VARCHAR,length = 15,comment = "订单联系人手机号")
    private String phone;

    @Column(name = "certificate_no",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "订单联系人证件号")
    private String certificateNo;

    @Column(name = "certificate_type",type = MySqlTypeConstant.TINYINT,length = 10,defaultValue = "1",comment = "订单联系人证件类型：1.身份证 ，2：护照 3：学生证")
    private Byte certificateType;

    @Column(name = "parent_order_id",type = MySqlTypeConstant.BIGINT,length = 10,comment = "父订单id")
    private Long parentOrderId;

    @Column(name = "extra_information",type = MySqlTypeConstant.TEXT,comment = "附加信息")
    private String extraInformation;

}
