package wang.ismy.orm;


import javax.sql.DataSource;
import java.lang.reflect.Proxy;


public class MyRepositoryFactory {

    private DataSource dataSource;

    public MyRepositoryFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T>T getRepositoryAgent(Class type){
        if (!type.isInterface()){
            throw new IllegalArgumentException("类型必须为接口类型");
        }

        return (T) Proxy.newProxyInstance(type.getClassLoader(),new Class[]{type},new RepositoryInvocationHandler());

    }
}
