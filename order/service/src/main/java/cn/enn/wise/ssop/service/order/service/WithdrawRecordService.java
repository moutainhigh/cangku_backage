package cn.enn.wise.ssop.service.order.service;


import cn.enn.wise.ssop.api.order.dto.request.WithdrawApplyParam;
import cn.enn.wise.ssop.api.order.dto.request.WithdrawQueryParam;
import cn.enn.wise.ssop.api.order.dto.response.WithdrawRecordDTO;
import cn.enn.wise.ssop.api.order.dto.response.WithdrawWillingDTO;
import java.util.List;




/**
 * 提现
 * <p>提现相关业务处理方法</p>
 * @author gaoguanglin
 * @since JDK1.8 normalize-1.0
 * @date 2020-03-30
 */
public interface WithdrawRecordService {



    /**
     * 分销商是否可以提现
     *
     * @param distributorId 分销商ID
     * @return 可否提现，资质是否完备；是，返回true；否则false
     */
    boolean certifyWithdraw(Long distributorId);


    /**
     * 创建提现单
     *
     * @param param
     * @return
     */
    String saveRecord(WithdrawApplyParam param);



    /**
     * 获取分销可提现信息
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    WithdrawWillingDTO getDistributeInfo(Long id, String startDate, String endDate);


    /**
     * 更新分销单状态
     * @param ordersId
     * @param status
     * @return
     */
    boolean updateDistributeStatus(String ordersId,Integer status);


    /**
     * 根据提现单号获取提现单详情
     * @param withdrawSerial
     * @return
     */
    WithdrawRecordDTO getWithdrawInfoBySerial(String withdrawSerial);


    /**
     * 根据提现单号更新已经更新分销单状态
     * @param withdrawSerial
     */
    void modifyWithdrawDistributeStatus(String withdrawSerial);


    /**
     * 清理审核状态
     * @param withdrawSerials
     */
    void clearWithdrawPermitStatus(String withdrawSerials);


    /**
     * 查询指定日期内的提现订单数据
     * @param distributorId
     * @param startDate
     * @param endDate
     * @return
     */
    List<WithdrawRecordDTO> listRecordsInDate(Long distributorId, String startDate, String endDate);


    /**
     * 发送短信通知给审批人
     * @param withdrawSerials 提现单ID，使用半角逗号分隔
     */
    void sendComplementMessage(String withdrawSerials);


    /**
     * PC 后台根据参数查询
     * @param param
     * @return
     */
    List<WithdrawRecordDTO> listRecordsByPage(WithdrawQueryParam param);

    /**
     * 总数据量
     * @param param
     * @return
     */
    Long listRecordsByPageCount(WithdrawQueryParam param);


    /**
     * 批量发放
     * @param withdrawSerials 提现单ID，使用半角逗号分隔
     */
    void putOut(String withdrawSerials);



}
