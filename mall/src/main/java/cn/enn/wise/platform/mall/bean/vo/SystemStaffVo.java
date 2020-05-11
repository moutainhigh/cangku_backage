package cn.enn.wise.platform.mall.bean.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SystemStaffVo implements Serializable {
    /**
     * 管理员ID
     */
    private Long id;
    /**
     * 管理员登录名
     */
    private String name;
    /**
     * 用户登录密码
     */
    private String passwd;
    /**
     * 管理员所属公司ID
     */
    private Long companyId;
    /**
     * 管理员所属公司名称
     */
    private String companyName;
    /**
     * 管理员token
     */
    private String token;
}
