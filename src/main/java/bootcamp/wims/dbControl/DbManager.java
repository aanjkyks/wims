package bootcamp.wims.dbControl;

import bootcamp.wims.model.Note;
import bootcamp.wims.model.Tag;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;


@Service
public class DbManager {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("note");
	public static  EntityManager getEntityManager(){
		return emf.createEntityManager();
	}
	public List<Note> searchNote(String searchTerm) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEntityManager());
		
		QueryBuilder qb = fullTextEntityManager
							.getSearchFactory()
							.buildQueryBuilder()
							.forEntity(Note.class)
							.get();

		org.apache.lucene.search.Query luceneQuery = qb
								.keyword()
								.fuzzy()
								.withEditDistanceUpTo(1)
								.withPrefixLength(1)
								.onFields("name")
								.matching(searchTerm)
								.createQuery();

        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Note.class);
		List<Note> notes = null;
		
        try {
            notes = jpaQuery.getResultList();
        } catch (NoResultException nre) {

        }

        return notes;
	}


	public Note selectNoteByID(Integer userID, Integer id) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			Query q = em.createNativeQuery("SELECT * FROM Notes n WHERE n.id = ? and n.userID = ?", Note.class);
			q.setParameter(1, id);
			q.setParameter(2, userID);
			Note note = (Note) q.getSingleResult();
			return note;
		} catch(Exception e) {
			return new Note();
		}
	}

	public List<Note> selectNotesByTagName(Integer userID, String tagName) {

		List<Note> noteList = new ArrayList<Note>();
		Tag tag = findTagByName(tagName);
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			Query q = em.createNativeQuery("SELECT * FROM Notes n WHERE n.userID = ? and n.tagID = ?", Note.class);
			q.setParameter(1, userID);
			q.setParameter(2, tag.getId());
			noteList = q.getResultList();
			return noteList;
		} catch(Exception e) {
			return noteList;
		}
	}

	public List<String[]> countTagsUsage(Integer userID) {
		List<String[]> strList = new ArrayList<String[]>();
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			Query q =
					em.createNativeQuery("select DISTINCT(t.name), count(n.tagID) from database_test.Notes as n, " +
							"database_test.Tags as t where n.userID = ? and t.id = n.tagID group by n.tagID");
			q.setParameter(1, userID);
			List<Object[]> obj = q.getResultList();
			Iterator it = obj.iterator();
			while(it.hasNext()) {
				Object[] line = (Object[]) it.next();
				String[] strArray = new String[2];
				strArray[0] = line[0].toString();
				strArray[1] = line[1].toString();
				strList.add(strArray);
			}
			return strList;
		} catch(Exception e) {
			return strList;
		}
	}

	public boolean deleteNote(Integer noteID, Integer userID) {
		boolean status = false;
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			Query q;
			int st;
			em.getTransaction().begin();
			q = em.createNativeQuery("delete from Notes where id = ? and userID = ?");
			q.setParameter(1, noteID);
			q.setParameter(2, userID);
			st = q.executeUpdate();
			if(st == 1) {
				status = true;
				em.getTransaction().commit();
			} else {
				status = false;
				em.getTransaction().rollback();
			}
		} catch(Exception e) {
			return false;
		}
		return status;
	}
	public boolean insertNote(Integer userID, String noteName, Date noteDate, String text, String tagName) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			Tag tag = findTagByName(tagName);
			if(tag.getName() == null) {
				boolean tempStatus = createTag(tagName);
				if(tempStatus) tag = findTagByName(tagName);
				else return false;
			}
			boolean status = createNote(noteName, noteDate, userID, tag.getId(), text);
			if(status) return true;
			else return false;
		} catch(Exception e) {
			return false;
		}
	}

	public Tag findTagByName(String tagName) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			Tag tag = new Tag();
			Query q = em.createNativeQuery("SELECT * From Tags WHERE name = ?", Tag.class);
			q.setParameter(1, tagName);
			tag = (Tag) q.getSingleResult();
			return tag;
		} catch(Exception e) {
			return new Tag();
		}
	}

	public boolean createTag(String tagName) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			boolean status;
			em.getTransaction().begin();
			Query q = em.createNativeQuery("INSERT INTO Tags (name) VALUES (?) ");
			q.setParameter(1, tagName);
			int st = q.executeUpdate();
			if(st == 1) {
				status = true;
				em.getTransaction().commit();
			} else {
				status = false;
				em.getTransaction().rollback();
			}
			return status;
		} catch(Exception e) {
			return false;
		}
	}

	public boolean createNote(String name, Date noteDate, Integer userID, Integer tagID, String text) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			boolean status;
			em.getTransaction().begin();
			Query q = em.createNativeQuery("INSERT INTO Notes (name,noteDate,userID,tagID,text) VALUES (?,?,?,?,?)");
			q.setParameter(1, name);
			q.setParameter(2, noteDate);
			q.setParameter(3, userID);
			q.setParameter(4, tagID);
			q.setParameter(5, text);
			int st = q.executeUpdate();
			if(st == 1) {
				status = true;
				em.getTransaction().commit();
			} else {
				status = false;
				em.getTransaction().rollback();
			}
			return status;
		} catch(Exception e) {
			return false;
		}
	}

	public boolean updateTag(Integer id, String tagName) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			boolean status;
			em.getTransaction().begin();
			Query q = em.createNativeQuery("UPDATE Tag SET name = ? where id = ?");
			q.setParameter(1, tagName);
			q.setParameter(2, id);
			int st = q.executeUpdate();
			if(st == 1) {
				status = true;
				em.getTransaction().commit();
			} else {
				status = false;
				em.getTransaction().rollback();
			}
			return status;
		} catch(Exception e) {
			return false;
		}
	}

	public Tag findTagByID(Integer id) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			Tag tag = new Tag();
			Query q = em.createNativeQuery("SELECT * From Tags WHERE id = ?", Tag.class);
			q.setParameter(1, id);
			tag = (Tag) q.getSingleResult();
			return tag;
		} catch(Exception e) {
			return new Tag();
		}
	}
}
