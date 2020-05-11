package cn.enn.wise.ssop.service.order.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 请求日志记录表
 *
 * @author baijie
 * @date 2020-01-02
 */
@Mapper
public interface RequestLogMapper {

    /**
     * 创建一张日志表
     * @param tableName 表名称
     */
    void createLogTable(@Param("tableName") String tableName);

    /**
     * 查询表名称是否存在
     * @param tableName
     * @return
     */
    String findTableName(String tableName);

}
