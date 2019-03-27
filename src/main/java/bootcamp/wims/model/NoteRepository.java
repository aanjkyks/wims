package bootcamp.wims.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface NoteRepository extends CrudRepository<Note, Integer> {
	List<Note> findAllByUserID(Integer id);

	@Query("select n from Note n where n.userID = ?1 and (n.name like %?2% or n.text like %?2%)")
	List<Note> findAllByUserIDAndNameContainingOrTextContaining(Integer userId, String nameSearch);

	@Transactional
	Integer deleteByIdAndUserID(Integer id, Integer userID);

	List<Note> findAllByUserIDAndTagName(Integer userId, String tagName);
}
