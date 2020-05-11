package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.controller.export.BBDTicketStateParam;
import cn.enn.wise.platform.mall.util.ResponseEntity;

/**
 * 购票历史记录接口
 * @author zsj
 */
public interface OrderHistoryService {

    /**
     * 换票
     * @param bBDTicketStateParam
     * @return
     */
    ResponseEntity ticketChange(BBDTicketStateParam bBDTicketStateParam);

    /**
     * 记录票号
     * @param ticketId
     * @param ticketNum
     * @return
     */
    ResponseEntity ticketSave(Long ticketId, String ticketNum, String qrCode);
}
