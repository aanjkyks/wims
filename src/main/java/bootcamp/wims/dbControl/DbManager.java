package bootcamp.wims.dbControl;

import bootcamp.wims.auth.model.User;
import bootcamp.wims.model.Note;
import bootcamp.wims.model.Tag;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class DbManager {

	public Note selectNoteByID(Integer userID, Integer id) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			em.getTransaction().begin();
			Query q = em.createNativeQuery("SELECT * FROM Notes n WHERE n.id = ? and n.userID = ?", Note.class);
			q.setParameter(1, id);
			q.setParameter(2, userID);
			Note note = (Note) q.getSingleResult();
			em.getTransaction().commit();
			em.clear();
			return note;
		} catch(Exception e) {
			em.getTransaction().rollback();
			em.clear();
			return new Note();
		}
	}

	public List<Note> selectNotesByTagName(Integer userID, String tagName) {			
		List<Note> noteList = new ArrayList<Note>();
		if (tagName.isEmpty() || tagName == null)
			return noteList;
		Tag tag = findTagByName(userID, tagName);
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			em.getTransaction().begin();
			Query q = em.createNativeQuery("SELECT * FROM Notes n WHERE n.userID = ? and n.tagID = ?", Note.class);
			q.setParameter(1, userID);
			q.setParameter(2, tag.getId());
			noteList = q.getResultList();
			em.getTransaction().commit();
			em.clear();
			return noteList;
		} catch(Exception e) {
			em.getTransaction().rollback();
			em.clear();
			return noteList;
		}
	}
	
	//TODO: make list sort before giving it out
	public List<String[]> countTagsUsage(Integer userID) {
		List<String[]> strList = new ArrayList<String[]>();
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			em.getTransaction().begin();
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
			em.getTransaction().commit();
			em.clear();
			return strList;
		} catch(Exception e) {
			em.getTransaction().rollback();
			em.clear();
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
				em.clear();
			} else {
				status = false;
				em.getTransaction().rollback();
				em.clear();
			}
		} catch(Exception e) {
			em.getTransaction().rollback();
			em.clear();
			return false;
		}
		return status;
	}


	public boolean updateNote(Note oldNote, String newNoteName, Date newNoteDate, String newText, String newTagName) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			boolean status;
			Query q;
			int st;
			int tagID = oldNote.getTag().getId();
			if(!oldNote.getTag().getName().equals(newTagName)) {
				createTag(oldNote.getUserID(), newTagName);
				Tag newTag = findTagByName(oldNote.getUserID(), newTagName);
				tagID = newTag.getId();
			}
			em.getTransaction().begin();
			q = em.createNativeQuery("UPDATE Notes SET name = ?, noteDate = ?, tagID = ?, text = ? where id = ?");
			q.setParameter(1, newNoteName);
			q.setParameter(2, newNoteDate);
			q.setParameter(3, tagID);
			q.setParameter(4, newText);
			q.setParameter(5, oldNote.getId());
			st = q.executeUpdate();
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
	
	public Tag findTagByName(Integer userID, String tagName) {
		if (tagName.isEmpty() || tagName == null)
			return new Tag();
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			em.getTransaction().begin();
			Tag tag = new Tag();
			Query q = em.createNativeQuery("SELECT * From Tags WHERE name = :name and userID = :userID",Tag.class);
			q.setParameter("name", tagName);
			q.setParameter("userID", userID);
			tag = (Tag) q.getSingleResult();
				em.getTransaction().commit();
				em.clear();
				return tag;
		} catch(Exception e) {
			em.getTransaction().rollback();
			em.clear();
			return new Tag();
			
		}
	}

	public boolean createTag(Integer userID,String tagName) {
		if (tagName.isEmpty() || tagName == null)
			return false;
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			Tag tag = findTagByName(userID, tagName);
			if (tag.getId() == null)
			{
				em.getTransaction().begin();
				Query q = em.createNativeQuery("INSERT INTO Tags (name, userID) VALUES (:tagName,:userID) ",Tag.class);
				q.setParameter("tagName", tagName);
				q.setParameter("userID", userID);
				int st = q.executeUpdate();
				if(st == 1) {
					em.getTransaction().commit();
					em.clear();
					return true;
				} else {
					em.getTransaction().rollback();
					em.clear();
					return false;
				}
			}
			else
			{
			return false;
			}
		} catch(Exception e) {
			e.printStackTrace();
			if (em.getTransaction().isActive())
			{
			em.getTransaction().rollback();
			em.clear();
			}
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
				em.clear();
			} else {
				status = false;
				em.getTransaction().rollback();
				em.clear();
			}
			return status;
		} catch(Exception e) {
			em.getTransaction().rollback();
			em.clear();
			return false;
		}
	}

	public boolean updateTag(Integer userID,Integer id, String tagName) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			boolean status = false;
			em.getTransaction().begin();
			Query q = em.createNativeQuery("UPDATE Tags SET name = :name where id = :tagID AND userID = :userID",Tag.class);
			q.setParameter("name", tagName);
			q.setParameter("tagID", id);
			q.setParameter("userID", userID);
			int st = q.executeUpdate();
			if(st == 1) {
				status = true;
				em.getTransaction().commit();
				em.clear();
			} else {
				status = false;
				em.getTransaction().rollback();
				em.clear();
			}
			return status;
		} catch(Exception e) {
			em.getTransaction().rollback();
			em.clear();
			return false;
		}
	}

	public Tag findTagByID(Integer userID, Integer id) {
		EntityManager em = PersistenceManager.INSTANCE.getEntityManager();
		try {
			em.getTransaction().begin();
			Tag tag = new Tag();
			Query q = em.createNativeQuery("SELECT * From Tags WHERE id = ? AND userID = ?", Tag.class);
			q.setParameter(1, id);
			q.setParameter(2, userID);
			tag = (Tag) q.getSingleResult();
			em.getTransaction().commit();
			em.clear();
			return tag;
		} catch(Exception e) {
			em.getTransaction().rollback();
			em.clear();
			return new Tag();
		}
	}
}
