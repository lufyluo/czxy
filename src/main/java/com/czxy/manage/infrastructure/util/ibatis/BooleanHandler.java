package com.czxy.manage.infrastructure.util.ibatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-11-21 上午10:39
 */
public class BooleanHandler extends BaseTypeHandler<Boolean> {
    private static final String TYPE_NAME_INTEGER = "integer";

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Boolean o, JdbcType jdbcType) throws SQLException {
        if (o instanceof Boolean) {
            preparedStatement.setInt(i, getValue(o));
        }
    }

    @Override
    public Boolean getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.getBoolean(s);
    }

    @Override
    public Boolean getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getBoolean(i);
    }

    @Override
    public Boolean getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getBoolean(i);
    }

    private int getValue(Boolean aBoolean) {
        if (Boolean.TRUE.equals(aBoolean)) {
            return 1;
        }
        return 0;
    }

}
