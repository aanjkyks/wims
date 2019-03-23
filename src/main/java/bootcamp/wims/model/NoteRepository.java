package bootcamp.wims.model;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface NoteRepository extends CrudRepository<Note, Integer> {
    List<Note> findAllByUserID(Integer id);

    @Transactional
    Integer deleteByIdAndUserID(Integer id, Integer userID);
}
