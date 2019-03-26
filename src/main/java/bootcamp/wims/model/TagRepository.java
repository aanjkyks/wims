package bootcamp.wims.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Integer> {

    @Nullable
    Tag findByNameAndUserID(String tags, Integer userID);

    List<Tag> findByUserID(Integer userID);
}
