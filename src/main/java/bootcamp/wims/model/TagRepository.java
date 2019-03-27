package bootcamp.wims.model;

import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Integer> {

	Tag findFirstByUserIDAndName(Integer id, String name);
}
