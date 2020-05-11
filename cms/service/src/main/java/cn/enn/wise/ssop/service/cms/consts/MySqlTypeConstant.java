package cn.enn.wise.ssop.service.cms.consts;

import com.gitee.sunchenbin.mybatis.actable.annotation.LengthCount;

/**
 * @author baijie
 * @date 2019-09-05
 */
public class MySqlTypeConstant {
    @LengthCount
    public static final String INT = "int";

    @LengthCount
    public static final String TINYINT = "tinyint";

    @LengthCount
    public static final String VARCHAR = "varchar";
    @LengthCount(
            LengthCount = 0
    )
    public static final String TEXT = "text";
    @LengthCount(
            LengthCount = 0
    )
    public static final String MEDIUMTEXT = "mediumtext";
    @LengthCount(
            LengthCount = 0
    )
    public static final String LONGTEXT = "longtext";
    @LengthCount(
            LengthCount = 0
    )
    public static final String DATETIME = "datetime";
    @LengthCount(
            LengthCount = 2
    )
    public static final String DECIMAL = "decimal";
    @LengthCount(
            LengthCount = 2
    )
    public static final String DOUBLE = "double";
    @LengthCount
    public static final String CHAR = "char";
    @LengthCount
    public static final String BIGINT = "bigint";
    @LengthCount(
            LengthCount = 1
    )
    public static final String BIT = "bit";
    @LengthCount(
            LengthCount = 0
    )
    public static final String TIMESTAMP = "timestamp";
    @LengthCount(
            LengthCount = 0
    )
    public static final String DATE = "date";
    @LengthCount(
            LengthCount = 0
    )
    public static final String TIME = "time";
    @LengthCount(
            LengthCount = 0
    )
    public static final String FLOAT = "float";



    public MySqlTypeConstant() {
    }
}
