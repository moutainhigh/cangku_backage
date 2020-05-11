package cn.enn.wise.platform.mall.bean.bo.autotable;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Table(name = "short_link")
public class ShortLink {


  @Column(name = "id",type = MySqlTypeConstant.INT,length =11,isKey = true,isAutoIncrement = true,comment = "null")
  @ApiModelProperty(value = "null")
  private Integer id;

  @Column(name = "short_url",type = MySqlTypeConstant.VARCHAR,length =100,comment = "null")
  @ApiModelProperty(value = "null")
  private String shortUrl;

  @Column(name = "real_url",type = MySqlTypeConstant.VARCHAR,length =1000,comment = "null")
  @ApiModelProperty(value = "null")
  private String realUrl;

}
