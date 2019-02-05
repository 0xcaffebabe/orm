package wang.ismy.orm;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyRepositoryFactoryTest {

    @Test
    public void getRepositoryAgent() {

        MyRepositoryFactory factory = new MyRepositoryFactory(null);
        EntityRepository m=factory.getRepositoryAgent(EntityRepository.class);
        m.sayHello();
    }
}