package cn.enn.wise.ssop.service.order.config;

import cn.enn.wise.uncs.base.config.MyTenantParser;
import cn.enn.wise.uncs.common.http.HttpContextUtils;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 * </pre>
 *
 * @author shizhai
 * @date 2019/5/3
 */
@Configuration
@MapperScan("cn.enn.wise.ssop.service.order.mapper")
public class MybatisPlusConfig {

    /**
     * 多租户属于 SQL 解析部分，依赖 MP 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {


        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        /*
         * 【测试多租户】 SQL 解析处理拦截器<br>
         * 这里固定写成住户 1 实际情况你可以从cookie读取，因此数据看不到 【 麻花藤 】 这条记录（ 注意观察 SQL ）<br>
         */
        List<ISqlParser> sqlParserList = new ArrayList<>();
        TenantSqlParser tenantSqlParser = new MyTenantParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {

            /**
             * 2019-8-1
             *
             * https://gitee.com/baomidou/mybatis-plus/issues/IZZ3M
             *
             * tenant_id in (1,2)
             * @param  where 如果是where，可以追加，不是where的情况：比如当insert时，不能insert into user(name, tenant_id) values('test', tenant_id IN (1, 2));
             * @return
             */
            @Override
            public Expression getTenantId(boolean where) {
//                final boolean multipleTenantIds = false;//这里只是演示切换单个tenantId和多个tenantId
//                //具体场景，可以根据情况来拼接
//                if (where && multipleTenantIds) {
//                    //演示如何实现tenant_id in (1,2)
//                    return multipleTenantIdCondition();
//                } else {
//                    //演示：tenant_id=1
//                    return singleTenantIdCondition();
//                }
                return singleTenantIdCondition();
            }

            private Expression singleTenantIdCondition() {
                HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
                if(request!=null){
                    String company_id = request.getHeader("company_id");  //header 获取租户id
                    if(!StringUtils.isEmpty(company_id)) return new LongValue(company_id);
                }
                return new LongValue(-1);
            }

            private Expression multipleTenantIdCondition() {
                final InExpression inExpression = new InExpression();
                inExpression.setLeftExpression(new Column(getTenantIdColumn()));
                final ExpressionList itemsList = new ExpressionList();
                final List<Expression> inValues = new ArrayList<>(2);
                inValues.add(new LongValue(1));//ID自己想办法获取到
                inValues.add(new LongValue(2));
                itemsList.setExpressions(inValues);
                inExpression.setRightItemsList(itemsList);
                return inExpression;
            }

            @Override
            public String getTenantIdColumn() {
                return "company_id";
            }

            @Override
            public boolean doTableFilter(String tableName) {
                // 这里可以判断是否过滤表
                if (tableName.toLowerCase().equals("tables")
                        ||tableName.toLowerCase().equals("columns")
                        ||tableName.toLowerCase().equals("statistics")) {
                    return true;
                }
                return false;
            }

        });

        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}