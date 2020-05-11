package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.DrawRecordListParam;
import cn.enn.wise.ssop.api.promotions.dto.response.DrawRecordDTO;
import cn.enn.wise.ssop.service.promotions.mapper.DrawRecordMapper;
import cn.enn.wise.ssop.service.promotions.model.DrawRecord;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/**
 * @author 安辉
 * 抽奖记录
 */
@Service("drawRecordService")
public class DrawRecordService extends ServiceImpl<DrawRecordMapper, DrawRecord> {
    /**
     * 参与名单
     * @param params
     * @return
     */
    public R<QueryData<DrawRecordDTO>> listByPage(DrawRecordListParam params) {
        QueryData<DrawRecordDTO> result =new QueryData<>();
        List<DrawRecordDTO> list = new ArrayList<>();
        QueryWrapper<DrawRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_base_id",params.getActivityBaseId());
        IPage<DrawRecord> recordIPage= new Page<>(params.getPageNo(),params.getPageSize());
        recordIPage = this.page(recordIPage,queryWrapper);
        recordIPage.getRecords().forEach(x->{
            DrawRecordDTO recordDTO =new DrawRecordDTO();
            BeanUtils.copyProperties(x,recordDTO,getNullPropertyNames(x));
            list.add(recordDTO);
        });
        result.setRecords(list);
        result.setTotalCount(recordIPage.getTotal());
        result.setPageSize(recordIPage.getSize());
        result.setPageNo(recordIPage.getCurrent());
        return new R(result);
    }
}
