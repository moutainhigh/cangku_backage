package cn.enn.wise.platform.mall.bean.bo;

import cn.enn.wise.platform.mall.bean.bo.autotable.Knowledge;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/10/30
 */
@Data
@TableName("knowledge")
public class KnowledgeBo extends Knowledge {

    @TableField(exist = false)
    private String businessName;

}
