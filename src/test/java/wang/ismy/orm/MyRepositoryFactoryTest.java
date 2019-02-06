package wang.ismy.orm;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import javax.sql.DataSource;

import static org.junit.Assert.*;

public class MyRepositoryFactoryTest {

    @Test
    public void getRepositoryAgent() {

        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setUrl("jdbc:mysql:///orm?serverTimezone=Hongkong");

        MyRepositoryFactory factory = new MyRepositoryFactory(dataSource);
        EntityRepository repository = factory.getRepositoryAgent(EntityRepository.class);

        assertEquals(1,repository.insert("kkk"));
        assertEquals(2,repository.selectAll().size());
    }
}