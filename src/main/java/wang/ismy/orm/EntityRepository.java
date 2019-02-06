package wang.ismy.orm;


import wang.ismy.orm.annotations.Delete;
import wang.ismy.orm.annotations.Insert;
import wang.ismy.orm.annotations.Select;
import wang.ismy.orm.annotations.Update;

import java.util.List;

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
