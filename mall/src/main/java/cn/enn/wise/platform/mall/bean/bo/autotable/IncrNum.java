package cn.enn.wise.platform.mall.bean.bo.autotable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Table(name = "incr_num")
public class IncrNum {


  @Column(name = "id",type = MySqlTypeConstant.INT, length = 11,comment = "主键id")
  @TableId(type = IdType.AUTO)
  @ApiModelProperty("主键")
  private Integer id;

  @Column(name = "value",type = MySqlTypeConstant.VARCHAR,length =255,comment = "值")
  @ApiModelProperty(value = "值")
  private String value;

}
