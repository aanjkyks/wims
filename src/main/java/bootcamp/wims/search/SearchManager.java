package bootcamp.wims.search;

import java.util.List;

import org.hibernate.search.jpa.FullTextEntityManager;

import bootcamp.wims.model.Note;
import bootcamp.wims.dbControl.*;

public class SearchManager {
	public List<Note> searchNotes(String searchTerm) {
		DbManager mng = new DbManager();
		return mng.searchNote(searchTerm);
	}
}
