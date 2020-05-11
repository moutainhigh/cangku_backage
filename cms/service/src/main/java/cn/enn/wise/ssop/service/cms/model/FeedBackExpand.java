package cn.enn.wise.ssop.service.cms.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 反馈关联景区
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "xz_feed_back_expand")
public class FeedBackExpand extends TableBase {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    private Long id;


    /**
     * 项目服务名称
     */
    @Column(name = "business_name",type = MySqlTypeConstant.VARCHAR,length = 50,defaultValue = "",comment = "项目服务名称")
    private String businessName;


    /**
     * 1.团餐餐饮2.大峡⾕谷酒店3.景区观光⻋4.景区服务5.其他
     */
    @Column(name = "business_type",type = MySqlTypeConstant.INT,length = 2,defaultValue = "1",comment = "1.团餐餐饮2.大峡⾕谷酒店3.景区观光⻋4.景区服务5.其他")
    private Integer businessType;




}
