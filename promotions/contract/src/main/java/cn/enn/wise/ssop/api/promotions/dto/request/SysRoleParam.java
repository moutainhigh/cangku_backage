package cn.enn.wise.ssop.api.promotions.dto.request;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author jiabaiye
 */
@Data
@ApiModel("角色表")
public class SysRoleParam {


    /**
     * 主键
     */

    private Long id;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 角色名称
     */
    private String roleName;




}
