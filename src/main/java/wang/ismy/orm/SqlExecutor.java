package wang.ismy.orm;

import wang.ismy.orm.enums.DatabaseOperationType;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlExecutor {

    private DataSource dataSource;

    public SqlExecutor(DataSource dataSource) {

        this.dataSource = dataSource;
    }


    public int update(String sql, Object[] args, Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject(i + 1, args[i]);
        }
        return preparedStatement.executeUpdate();
    }

    public List query(String sql, Object[] args, Class entityType, Connection connection) throws SQLException, IllegalAccessException, InstantiationException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (args != null){
            for (int i = 1; i <= args.length; i++) {
                preparedStatement.setObject(i, args[i - 1]);
            }
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        Field[] fields = entityType.getDeclaredFields();
        List ret = new ArrayList();
        while (resultSet.next()) {
            Object entity = entityType.newInstance();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                String column = metaData.getColumnName(i + 1);
                for (var j : fields) {
                    if (j.getName().equals(column)) {
                        j.setAccessible(true);
                        j.set(entity, resultSet.getObject(column));
                        break;
                    }
                }
            }
            ret.add(entity);
        }
        return ret;
    }


    public Object execute(String sql, Object[] args, Class entityType, Class returnType, DatabaseOperationType operationType) {
        Connection connection = null;
        int updateRet = -1;
        List queryRet = null;
        try {

            connection = dataSource.getConnection();
            if (operationType.equals(DatabaseOperationType.SELECT)) {
                queryRet = query(sql, args, entityType, connection);
                if (returnType.equals(entityType)) {
                    if (queryRet.size() > 0){
                        return queryRet.get(0);
                    }else{
                        return null;
                    }
                } else if (returnType.equals(List.class)) {
                    return queryRet;
                }
            } else {
                updateRet = update(sql, args, connection);
                return updateRet;
            }
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (operationType.equals(DatabaseOperationType.SELECT)){
            return queryRet;
        }else{
            return updateRet;
        }
    }
}
