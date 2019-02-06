package wang.ismy.orm;

import wang.ismy.orm.annotations.Delete;
import wang.ismy.orm.annotations.Insert;
import wang.ismy.orm.annotations.Select;
import wang.ismy.orm.annotations.Update;
import wang.ismy.orm.enums.DatabaseOperationType;

import javax.sql.DataSource;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RepositoryInvocationHandler implements InvocationHandler {

    private DataSource dataSource;

    private SqlExecutor sqlExecutor;
    public RepositoryInvocationHandler(DataSource dataSource) {
        this.dataSource = dataSource;
        sqlExecutor = new SqlExecutor(dataSource);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Annotation[] annotations = method.getAnnotations();

        DatabaseOperationType databaseOperationType = null;
        Class returnType = method.getReturnType();
        String sql = null;
        Class entityType = null;
        for (var a : annotations){

            if (a instanceof Select){
                databaseOperationType = DatabaseOperationType.SELECT;
                sql = ((Select) a).value();
                entityType = ((Select) a).entity();
                break;
            }

            if (a instanceof Update){
                databaseOperationType = DatabaseOperationType.UPDATE;
                sql = ((Update) a).value();
                break;
            }

            if (a instanceof Delete){
                databaseOperationType = DatabaseOperationType.DELETE;
                sql = ((Delete) a).value();
                break;
            }

            if (a instanceof Insert){
                databaseOperationType = DatabaseOperationType.INSERT;
                sql = ((Insert) a).value();
                entityType = ((Insert) a).entity();
                break;
            }
        }

        return sqlExecutor.execute(sql,args,entityType,returnType,databaseOperationType);

    }
}
