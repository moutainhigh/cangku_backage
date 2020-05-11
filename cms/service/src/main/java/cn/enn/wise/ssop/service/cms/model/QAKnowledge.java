package cn.enn.wise.ssop.service.cms.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "qa")
@TableName("qa")
@NoArgsConstructor
@AllArgsConstructor
public class QAKnowledge extends TableBase {

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


    @TableField(exist = false)
    private String businessName;
}