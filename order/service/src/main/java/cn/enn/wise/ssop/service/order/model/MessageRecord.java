package cn.enn.wise.ssop.service.order.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

@Data
@Table(name = "message_record")
public class MessageRecord extends TableBase {

    /**
     * 主键id
     */
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    private Long id;

    /**
     * 队列名称
     */
    @Column(name = "queue_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "队列名称")
    private String queueName;

    /**
     * 消息类型 1 接收消息  2  发送消息
     */
    @Column(name = "type",type = MySqlTypeConstant.VARCHAR,length = 4,comment = "消息类型 1 接收消息  2  发送消息")
    private Byte type;
    /**
     * 消息体
     */
    @Column(name = "body",type = MySqlTypeConstant.TEXT,length = 0,comment = "消息体")
    private String body;
}
