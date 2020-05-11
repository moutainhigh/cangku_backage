package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/10/30
 */
@Data
@Table(name = "knowledge")
@NoArgsConstructor
@AllArgsConstructor
public class Knowledge {

    /**
     * id
     */
    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 业务类别：1 订单问题 2 退款问题 3 安全指引 4景区实况
     */
    @Column(name = "business_category",type = MySqlTypeConstant.INT, length = 10,comment = "业务类别：1 订单问题 2 退款问题 3 安全指引 4景区实况")
    private Integer businessCategory;

    /**
     * 编号
     */
    @Column(name = "code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "编号")
    private String code;

    /**
     * 1、文章 2 问答 3 案例
     */
    @Column(name = "knowledge_type",type = MySqlTypeConstant.INT, length = 50,comment = "1、文章 2 问答 3 案例")
    private Integer knowledgeType;

    /**
     * 标题
     */
    @Column(name = "title",type = MySqlTypeConstant.VARCHAR, length = 500,comment = "标题")
    private String title;

    /**
     * 知识内容
     */
    @Column(name = "answer",type = MySqlTypeConstant.VARCHAR, length = 4000,comment = "答案")
    private String answer;

    /**
     * 附件
     */
    @Column(name = "attachment",type = MySqlTypeConstant.VARCHAR, length = 2000,comment = "附件")
    private String attachment;


    /**
     * 标记删除
     */
    @Column(name = "is_delete",type = MySqlTypeConstant.TINYINT, length = 1,comment = "标记删除")
    private Byte isDelete;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time",type = MySqlTypeConstant.TIMESTAMP,comment = "创建时间")
    private Timestamp createTime;

    @Column(name = "create_by",type = MySqlTypeConstant.BIGINT,length = 20,comment = "创建人id")
    private Long createBy;

    /**
     * 更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time",type = MySqlTypeConstant.TIMESTAMP,comment = "更新时间")
    private Timestamp updateTime;

    @Column(name = "update_by",type = MySqlTypeConstant.BIGINT,length = 20,comment = "创建人id")
    private Long updateBy;



}
