package cn.enn.wise.ssop.service.cms.config;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import com.baomidou.mybatisplus.core.parser.SqlInfo;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;

import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.SupportsOldOracleJoinSyntax;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * <p>
 * 复写租户条件
 * 使用当前版本为3.1.2
 * </p>
 *
 * @author yuxiaobin
 * @date 2019/8/1
 */
public class MyTenantParser extends TenantSqlParser {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean doFilter(MetaObject metaObject, String sql) {
        // fix bug actable 更新表 语句错误
//        ((MybatisCachingExecutor)((PreparedStatementHandler)metaObject.getValue("delegate")).executor).getTransaction().getConnection().getCatalog()
        BaseStatementHandler delegate = (BaseStatementHandler) metaObject.getValue("delegate");
        Class<BaseStatementHandler> preparedStatementHandlerClass = BaseStatementHandler.class;
        try {
            Field executor = preparedStatementHandlerClass.getDeclaredField("executor");
            executor.setAccessible(true);
            Executor cachingExecutor = (Executor) executor.get(delegate);
            String databaseName = cachingExecutor.getTransaction().getConnection().getCatalog();
            if(databaseName.contains("encdata")){
                //老系统 租户不做处理
                return false;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(sql.toLowerCase().startsWith("alter")||sql.toLowerCase().startsWith("create")||sql.toLowerCase().startsWith("drop")){
            return false;
        }
        return true;
    }

    @Override
    protected void processPlainSelect(PlainSelect plainSelect, boolean addColumn) {
        final Expression tenantExpression = this.getTenantHandler().getTenantId(false);

        FromItem fromItem = plainSelect.getFromItem();
        if (fromItem instanceof Table) {
            Table fromTable = (Table) fromItem;
            if (!this.getTenantHandler().doTableFilter(fromTable.getName())&&((LongValue)tenantExpression).getValue()>0) {
                plainSelect.setWhere(builderExpression(plainSelect.getWhere(), fromTable));
                if (addColumn) {
                    plainSelect.getSelectItems().add(new SelectExpressionItem(
                            new Column(this.getTenantHandler().getTenantIdColumn())));
                }

            }
        } else {
            processFromItem(fromItem);
        }
        List<Join> joins = plainSelect.getJoins();
        if (joins != null && joins.size() > 0) {
            joins.forEach(j -> {
                processJoin(j);
                processFromItem(j.getRightItem());
            });
        }
    }

    @Override
    public void processUpdate(Update update) {
        final Table table = update.getTable();
        if (super.getTenantHandler().doTableFilter(table.getName())) {
            // 过滤退出执行
            return;
        }
        final Expression tenantExpression = this.getTenantHandler().getTenantId(false);
        if(((LongValue)tenantExpression).getValue()>0){
            update.setWhere(this.andExpression(table, update.getWhere()));
        }
    }

    @Override
    public void processDelete(Delete delete) {
        if (super.getTenantHandler().doTableFilter(delete.getTable().getName())) {
            // 过滤退出执行
            return;
        }
        final Expression tenantExpression = this.getTenantHandler().getTenantId(false);
        if(((LongValue)tenantExpression).getValue()>0){
            delete.setWhere(this.andExpression(delete.getTable(), delete.getWhere()));
        }
    }

    @Override
    public SqlInfo processParser(Statement statement) {
        if (statement instanceof Insert) {
            this.processInsert((Insert) statement);
        } else if (statement instanceof Select) {
            this.processSelectBody(((Select) statement).getSelectBody());
        } else if (statement instanceof Update) {
            this.processUpdate((Update) statement);
        } else if (statement instanceof Delete) {
            this.processDelete((Delete) statement);
        }
        logger.debug("parser sql: " + statement.toString());
        return SqlInfo.newInstance().setSql(statement.toString());
    }


    @Override
    protected Expression builderExpression(Expression currentExpression, Table table) {
        final Expression tenantExpression = this.getTenantHandler().getTenantId(false);
        if(((LongValue)tenantExpression).getValue()==-1) return currentExpression;

        Expression appendExpression;
        if (!(tenantExpression instanceof SupportsOldOracleJoinSyntax)) {
            appendExpression = new EqualsTo();
            ((EqualsTo) appendExpression).setLeftExpression(this.getAliasColumn(table));
            ((EqualsTo) appendExpression).setRightExpression(tenantExpression);
        } else {
            appendExpression = processTableAlias4CustomizedTenantIdExpression(tenantExpression, table);
        }
        if (currentExpression == null) {
            return appendExpression;
        }
        if (currentExpression instanceof BinaryExpression) {
            BinaryExpression binaryExpression = (BinaryExpression) currentExpression;
            doExpression(binaryExpression.getLeftExpression());
            doExpression(binaryExpression.getRightExpression());
        } else if (currentExpression instanceof InExpression) {
            InExpression inExp = (InExpression) currentExpression;
            ItemsList rightItems = inExp.getRightItemsList();
            if (rightItems instanceof SubSelect) {
                processSelectBody(((SubSelect) rightItems).getSelectBody());
            }
        }
        if (currentExpression instanceof OrExpression) {
            return new AndExpression(new Parenthesis(currentExpression), appendExpression);
        } else {
            return new AndExpression(currentExpression, appendExpression);
        }
    }


}
