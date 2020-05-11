/**
 * Copyright (C), 2018-2019
 * FileName: BaseVo
 * Author:   Administrator
 * Date:     2019-04-19 10:52
 * Description:
 */
package cn.enn.wise.platform.mall.bean.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Administrator
 */
@Data
public class BaseVo implements Serializable {
    private static final long serialVersionUID = -141590900711528621L;
    private String token;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateTime;

    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp expiresTime;

    private Integer servicePlaceId;


}