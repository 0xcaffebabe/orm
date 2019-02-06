# orm
2019-2-1

根据个人对ORM框架理解的一个简单实现
#
2019-2-6

###第一个应用:

定义接口:
```java
public interface EntityRepository {

    @Select(value = "SELECT * FROM entity",entity = Entity.class)
    List<Entity> selectAll();

    @Update(value = "UPDATE entity SET name = 'java' WHERE name = ?")
    int updateByName(String name);

    @Delete("DELETE FROM entity WHERE name = ?")
    int deleteByName(String name);

    @Insert(value = "INSERT INTO entity(name) VALUES(?)",entity = Entity.class)
    int insert(String name);
}
```

使用:
```java
public class MyRepositoryFactoryTest {

    @Test
    public void getRepositoryAgent() {
        // 设置数据源
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setUrl("jdbc:mysql:///orm?serverTimezone=Hongkong");

        // 创建仓库工厂
        MyRepositoryFactory factory = new MyRepositoryFactory(dataSource);
        // 通过工厂获取仓库代理类
        EntityRepository repository = factory.getRepositoryAgent(EntityRepository.class);
        
        assertEquals(1,repository.insert("kkk"));
        assertEquals(2,repository.selectAll().size());
    }
}
```

