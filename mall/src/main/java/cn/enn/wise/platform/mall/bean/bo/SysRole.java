package cn.enn.wise.platform.mall.bean.bo;


import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author baijie
 * @since 2019-05-23
 */
@Data
public class SysRole implements Serializable {

private static final long serialVersionUID=1L;

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
