package cn.enn.wise.platform.mall.service;


import cn.enn.wise.platform.mall.bean.bo.WithdrawRecord;
import cn.enn.wise.platform.mall.bean.param.WithdrawApplyParam;
import cn.enn.wise.platform.mall.bean.param.WithdrawQueryParam;
import cn.enn.wise.platform.mall.bean.vo.WithdrawWillingVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 提现业务处理
 *
 * @author gaoguanglin
 * @since version.wzd0911
 */
public interface WithdrawService extends IService<WithdrawRecord> {


    /**
     * 分销商是否可以提现
     *
     * @param distributorId 分销商ID
     * @return 可以提现，资质完备，返回true；否则false
     */
    boolean certifyWithdraw(Long distributorId);




    /**
     * 生成并保存记录
     * @param param
     * @return
     */
    String saveRecord(WithdrawApplyParam param);



    /**
     * 获取分销信息
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    WithdrawWillingVO getDistributeInfo(Long id,String startDate,String endDate);


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
    WithdrawRecord getWithdrawInfoBySerial(String withdrawSerial);


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
    List<WithdrawRecord> listRecordsInDate(Long distributorId, String startDate, String endDate);


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
    List<WithdrawRecord> listRecordsByPage(WithdrawQueryParam param);

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
