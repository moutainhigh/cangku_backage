package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 *联系人
 * @author zsj
 * @date 2019/12/24  9:29
 */
@Data
@Table(name = "contacts")
public class Contacts extends Model<Contacts> {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "member_id",type = MySqlTypeConstant.INT, length = 10,comment = "会员id")
    @ApiModelProperty("会员id")
    private Long memberId;

    @Column(name = "name",type = MySqlTypeConstant.VARCHAR, length = 25,comment = "姓名")
    @ApiModelProperty("姓名")
    private String name;

    @Column(name = "id_card",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "身份证号码")
    @ApiModelProperty("身份证号码")
    private String idCard;

    @Column(name = "phone_num",type = MySqlTypeConstant.VARCHAR, length = 15,comment = "手机号码")
    @ApiModelProperty("手机号码")
    private String phoneNum;

    @Column(name = "ticket_type",type = MySqlTypeConstant.INT, length = 2,comment = "票型  101-成人票  203-儿童票  308-携童票")
    @ApiModelProperty("联系人类型  101-成人  203-儿童  404-携童")
    private Integer ticketType;

    @Column(name = "create_time",type = MySqlTypeConstant.TIMESTAMP,comment = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @Column(name = "update_time",type = MySqlTypeConstant.TIMESTAMP,comment = "修改时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("修改时间")
    private Timestamp updateTime;
}
