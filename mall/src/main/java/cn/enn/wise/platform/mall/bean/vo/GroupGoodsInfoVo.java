package cn.enn.wise.platform.mall.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 拼团商品Vo
 *
 * @author baijie
 * @date 2019-11-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupGoodsInfoVo {

    private Long goodId;

    private Integer isPackage;

    private Integer maxNum;

    private Long goodExtendId;

    private Long projectId;

    private String projectCode;

    private String goodName;
}
