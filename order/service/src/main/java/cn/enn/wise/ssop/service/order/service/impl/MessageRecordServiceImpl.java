package cn.enn.wise.ssop.service.order.service.impl;

import cn.enn.wise.ssop.service.order.mapper.MessageRecordMapper;
import cn.enn.wise.ssop.service.order.model.MessageRecord;
import cn.enn.wise.ssop.service.order.service.MessageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageRecordServiceImpl implements MessageRecordService {

    @Autowired
    private MessageRecordMapper messageRecordMapper;

    /**
     * 保存消息对列表
     * @param messageRecord
     */
    @Override
    public void saveMessageQueue(MessageRecord messageRecord) {
        messageRecordMapper.insert(messageRecord);
    }
}
