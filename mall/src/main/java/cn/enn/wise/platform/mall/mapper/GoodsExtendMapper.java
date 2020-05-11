package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.GoodsExtend;
import cn.enn.wise.platform.mall.bean.vo.LineCabinVo;
import cn.enn.wise.platform.mall.bean.vo.LineTicketVo;
import cn.enn.wise.platform.mall.bean.vo.LineVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 热气球时段表 Mapper 接口
 * </p>
 *
 * @author caiyt
 * @since 2019-05-24
 */
public interface GoodsExtendMapper extends BaseMapper<GoodsExtend> {

    /**
     * 获取航线
     * @param from
     * @param to
     * @return
     */
    List<LineVo> getLine(@Param("date") String date,@Param("from") String from, @Param("to") String to);

    /**
     * 获取航线票信息
     * @param date
     * @param from
     * @param to
     * @return
     */
    List<LineTicketVo> getLineTicket(@Param("date") String date,@Param("from") String from, @Param("to") String to,@Param("order") Integer order);

    /**
     * 获取仓位
     * @param date
     * @param from
     * @param to
     * @param startTime
     * @return
     */
    List<LineCabinVo> getCabinList(@Param("date") String date,@Param("from") String from, @Param("to") String to,@Param("startTime") String startTime,@Param("nickName") String nickName);


    /**
     * 获取船票信息
     * @param date
     * @param from
     * @param to
     * @param startTime
     * @return
     */
    LineTicketVo getLineTicketByTime(@Param("date") String date,@Param("from") String from, @Param("to") String to,@Param("startTime") String startTime,@Param("nickName") String nickName);

    List<GoodsExtend> getShipName();

    List<GoodsExtend> getCabin();

    List<GoodsExtend> getCabinByShipName(@Param("shipName") String shipName);


    List<Long> selectIdsByWrapper(@Param(Constants.WRAPPER) Wrapper wrapper);


    /**
     * 根据GoodsId查询可用的GoodExtendsId
     * @param goodsId
     * @return
     */
    List<Long> selectGoodsExtendIdUsable(@Param("goodsId") Long goodsId);
}
