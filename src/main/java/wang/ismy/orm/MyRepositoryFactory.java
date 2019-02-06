package wang.ismy.orm;


import javax.sql.DataSource;
import java.lang.reflect.Proxy;


public class MyRepositoryFactory {

    private DataSource dataSource;

    private RepositoryInvocationHandler invocationHandler;
    public MyRepositoryFactory(DataSource dataSource) {
        this.dataSource = dataSource;
        invocationHandler = new RepositoryInvocationHandler(dataSource);
    }

    public <T>T getRepositoryAgent(Class<T> type){
        if (!type.isInterface()){
            throw new IllegalArgumentException("类型必须为接口类型");
        }
        return type.cast
                (Proxy.newProxyInstance(type.getClassLoader(),
                        new Class[]{type},
                        invocationHandler)
                );
    }
}
