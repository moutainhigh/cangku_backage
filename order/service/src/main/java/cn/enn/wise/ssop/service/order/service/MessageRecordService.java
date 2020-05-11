package cn.enn.wise.ssop.service.order.service;

import cn.enn.wise.ssop.service.order.model.MessageRecord;

public interface MessageRecordService {

    /**
     * 保存消息对列表
     * @param messageRecord
     */
    void saveMessageQueue(MessageRecord messageRecord);
}
