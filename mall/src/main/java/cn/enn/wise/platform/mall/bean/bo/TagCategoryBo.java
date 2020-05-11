package cn.enn.wise.platform.mall.bean.bo;

import cn.enn.wise.platform.mall.bean.bo.autotable.TagCategory;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/9/16
 */
@Data
@TableName("tag_category")
public class TagCategoryBo extends TagCategory {
}
