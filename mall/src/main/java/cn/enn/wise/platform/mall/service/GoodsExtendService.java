package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.GoodsExtend;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 热气球时段表 服务类
 * </p>
 *
 * @author caiyt
 * @since 2019-05-24
 */
public interface GoodsExtendService extends IService<GoodsExtend> {
    /**
     * 根据商品ID查询商品扩展响应信息
     *
     * @param goodsId 商品ID查
     * @return
     */
    List<GoodsExtendResVo> getGoodsExtendInfoByGoodsId(long goodsId);

    /**
     * 根据商品ID查询商品扩展信息
     *
     * @param goodsId 商品ID查
     * @return
     */
    List<GoodsExtend> getGoodsExtendByGoodsId(long goodsId);

    /**
     * 禁用商品扩展信息
     *
     * @param goodsId
     * @param staffVo
     * @return
     */
    boolean disableByGoodsId(long goodsId, SystemStaffVo staffVo);

    /**
     * 保存商品时段信息
     *
     * @param goodsExtend
     * @return
     */
    boolean saveGoodExtend(GoodsExtend goodsExtend);

    /**
     * 根据商品ID和时段ID修改商品在该时段的运营价格
     *
     * @param goodsId
     * @param periodId
     * @param salePrice
     */
    void update(long goodsId, long periodId,String timeLabel, BigDecimal salePrice, SystemStaffVo staffVo);

    /**
     * 根据时段ID集合返回在用的时段ID
     *
     * @param periodIds
     * @return
     */
    Set<Long> getPeriodIdsInUse(List<Long> periodIds);

    /**
     * 将与商品关联的时段信息逻辑删除
     *
     * @param goodsIds
     * @param staffVo
     */
    void batchDeleteByGoodsId(List<Long> goodsIds, SystemStaffVo staffVo);

    /**
     * 获取航线
     * @param from
     * @param to
     * @return
     */
    ResponseEntity<LineVo> getLine(String date, String from, String to);

    /**
     * 获取航线的票
     * @param date
     * @return
     */
    ResponseEntity<LineTicketVo> getTicketByDate(String date, String from, String to, Integer order);

    /**
     * 获取详情
     * @param date
     * @param from
     * @param to
     * @param startTime
     * @return
     */
    ResponseEntity<LineDetailVo> getTicketDetail(String date, String from, String to, String startTime,String shipName);
}
