package cn.enn.wise.platform.mall.service;


/**
 * 票状态（与百邦达）同步
 */
public interface BBDTicketService {


    /**
     * 根据百邦达状态更新本地船票状态
     *
     * @param ticketId
     */
    void updateTicketFromBBD(Long ticketId);


    /**
     * 更新船票状态
     *
     * @param ticketId
     * @param state
     */
    void updateBBDTicketState(Long ticketId,int state);


    /**
     * 通知（同步到）百邦达执行退票
     * @param ticketNum
     * @return
     */
    boolean refundNotifyBBD(String ticketNum);


}
