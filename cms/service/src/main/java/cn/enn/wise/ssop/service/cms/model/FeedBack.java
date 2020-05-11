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
 * 反馈
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "xz_feed_back")
public class FeedBack extends TableBase {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    private Long id;


    /**
     * 反馈内容
     */
    @Column(name = "content",type = MySqlTypeConstant.VARCHAR,length = 300,defaultValue = "",comment = "反馈内容")
    private String content;

    /**
     * 手机号
     */
    @Column(name = "phone",type = MySqlTypeConstant.VARCHAR,length = 13,defaultValue = "",comment = "手机号")
    private String phone;

    /**
     * 1.未回复2.已回复3.已忽略
     */
    @Column(name = "status",type = MySqlTypeConstant.INT,length = 2,defaultValue = "1",comment = "1.未回复2.已回复3.已忽略")
    private Integer status;

    /**
     * 1.团餐餐饮2.大峡⾕谷酒店3.景区观光⻋4.景区服务5.其他
     */
    @Column(name = "business_type",type = MySqlTypeConstant.INT,length = 2,defaultValue = "1",comment = "1.团餐餐饮2.大峡⾕谷酒店3.景区观光⻋4.景区服务5.其他")
    private Integer businessType;

    /**
     * 短信通知
     */
    @Column(name = "messages",type = MySqlTypeConstant.VARCHAR,length = 300,defaultValue = "",comment = "短信通知")
    private String messages;

    /**
     * 反馈图片
     */
    @Column(name = "picture",type = MySqlTypeConstant.VARCHAR,length = 1000,defaultValue = "",comment = "反馈图片")
    private String picture;

    /**
     * 标题
     */
    @Column(name = "title",type = MySqlTypeConstant.VARCHAR,length = 50,defaultValue = "",comment = "标题")
    private String title;




}
