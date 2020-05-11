package cn.enn.wise.ssop.service.cms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 租户行程
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
public class CompanyRoute {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * companyId
     */
    private Long companyId;

    /**
     * compnay_route_id
     */
    private Long companyRouteId;

    /**
     * route_id
     */
    private Long routeId;

    public CompanyRoute(Long companyId, Long companyRouteId, Long routeId) {
        this.companyId = companyId;
        this.companyRouteId = companyRouteId;
        this.routeId = routeId;
    }
}