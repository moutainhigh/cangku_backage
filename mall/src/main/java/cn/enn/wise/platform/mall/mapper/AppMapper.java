package cn.enn.wise.platform.mall.mapper;


import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * App Mapper 接口
 * </p>
 *
 * @author jiaby
 * @since 2019-08-08
 */
public interface AppMapper  {


    /**
     * 获取app版本信息根据companyId
     * @param companyId
     * @return
     */
    String getAppVersionByCompanyId(@Param("companyId")Integer companyId);

}
