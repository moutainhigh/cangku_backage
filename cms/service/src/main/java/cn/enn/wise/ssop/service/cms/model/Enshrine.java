package cn.enn.wise.ssop.service.cms.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @date:2020/4/9
 * @author:hsq
 * 收藏
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Table(name = "enshrine")
public class Enshrine extends TableBase {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    private Long id;

    /**
     * 用户id
     */
    @Unique(name = "MEMBER_ARTICLE_COMBINED_INDEX",value = {"member_id", "article_id", "type"})
    @Column(name = "member_id",type = MySqlTypeConstant.BIGINT,length = 20,defaultValue = "1",comment = "用户id")
    private Long memberId;

    /**
     * 文章id
     */
    @Column(name = "article_id",type = MySqlTypeConstant.BIGINT,length = 20,defaultValue = "0",comment = "文章id")
    private Long articleId;

    /**
     * 类型
     */
    @Column(name = "type",type = MySqlTypeConstant.TINYINT,length = 20,defaultValue = "0",comment = "1景点 2行程")
    private Byte type;


    /**
     * 收藏时间
     */
    @Column(name = "enshrine_time",type = MySqlTypeConstant.TIMESTAMP,comment = "收藏时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp enshrineTime;



}
