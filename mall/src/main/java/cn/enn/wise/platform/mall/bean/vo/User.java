/**
 * @Project tourism-common
 * @Title user.java
 * @Package cn.enn.tourism.common.bean
 * @author anhui
 * @date 2019年4月18日 上午11:24:16
 * @version V1.0
 * @Copyright 2019 All rights Reserved, Designed By anhui.
 *
 */
package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.BaseVo;
import cn.enn.wise.platform.mall.bean.bo.SysRole;

import java.io.Serializable;
import java.util.List;

/**
 * 常量类
 * @Description
 * @ClassName  user
 * @date  2019年4月18日 上午11:24:16
 * @author anhui
 * @since JDK 1.8
 */
public class User extends BaseVo implements Serializable{

    private static final long serialVersionUID = -7789368425289461451L;
    private Long id;
    private String name;
    private String nickName;
    private Integer gender;
    private String headImg;
    private String idCard;

    /**
     * 一个用户对应多个角色
     */
    private List<SysRole> sysRoles;

    public List<SysRole> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(List<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }


}
