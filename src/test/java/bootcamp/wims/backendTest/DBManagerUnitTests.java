package bootcamp.wims.backendTest;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import bootcamp.wims.dbControl.DbManager;
import bootcamp.wims.model.Note;
import bootcamp.wims.model.Tag;

public class DBManagerUnitTests {
	static DbManager mng;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database_test", "root",
				"Student007");
		Statement stmt = con.createStatement();

		// create 3 new users
		stmt.executeUpdate("INSERT INTO Users (id, name, password) VALUES (100, 'Karlo', 123)");
		stmt.executeUpdate("INSERT INTO Users (id, name, password) VALUES (110, 'Frida', 123)");
		stmt.executeUpdate("INSERT INTO Users (id, name, password) VALUES (120, 'Ismal', 123)");

		// create 3 new Tags
		stmt.executeUpdate("INSERT INTO Tags (id, name, userID) VALUES (200, 'MakeLoveNotWar', 100)");
		stmt.executeUpdate("INSERT INTO Tags (id, name, userID) VALUES (201, 'MakeLoveNotWar', 110)");
		stmt.executeUpdate("INSERT INTO Tags (id, name, userID) VALUES (202, 'MakeLoveNotWar', 120)");
		
		stmt.executeUpdate("INSERT INTO Tags (id, name, userID) VALUES (210, 'Some strange stuff', 100)");
		stmt.executeUpdate("INSERT INTO Tags (id, name, userID) VALUES (211, 'Some strange stuff', 110)");
		stmt.executeUpdate("INSERT INTO Tags (id, name, userID) VALUES (212, 'Some strange stuff', 120)");
		
		stmt.executeUpdate("INSERT INTO Tags (id, name, userID) VALUES (220, 'Another day in big city', 100)");
		stmt.executeUpdate("INSERT INTO Tags (id, name, userID) VALUES (221, 'Another day in big city', 110)");
		stmt.executeUpdate("INSERT INTO Tags (id, name, userID) VALUES (222, 'Another day in big city', 120)");

		// create 3 Notes for new user 1 with different tags
		stmt.executeUpdate("INSERT INTO Notes (id, name, noteDate, userID, tagID, text) " +
		"VALUES (300, 'NoteNumber1', '2018-10-10', 100, 200, 'some fun text here')");
		stmt.executeUpdate("INSERT INTO Notes (id, name, noteDate, userID, tagID, text) " +
		"VALUES (310, 'NoteNumber2', '2018-11-11', 100, 210, 'some fun text here')");
		stmt.executeUpdate("INSERT INTO Notes (id, name, noteDate, userID, tagID, text) " +
		"VALUES (320, 'NoteNumber3', '2018-12-12', 100, 220, 'some fun text here')");

		// create 3 Notes for new user 2 with same tags
		stmt.executeUpdate("INSERT INTO Notes (id, name, noteDate, userID, tagID, text) " + 
		"VALUES (400, 'NoteNumber1', '2017-01-01', 110, 201, 'some other stuff')");
		stmt.executeUpdate("INSERT INTO Notes (id, name, noteDate, userID, tagID, text) " +
		"VALUES (410, 'NoteNumber2', '2017-10-10', 110, 201, 'some other stuff')");
		stmt.executeUpdate("INSERT INTO Notes (id, name, noteDate, userID, tagID, text) " +
		"VALUES (420, 'NoteNumber3', '2017-05-05', 110, 201, 'some other stuff')");

		// create 6 Notes for new user 3 (all 3 tags, repeats twice)
		stmt.executeUpdate("INSERT INTO Notes (id, name, noteDate, userID, tagID, text) " +
		"VALUES (500, 'NoteNumber1', '2018-10-10', 120, 202, 'some fun stuff')");
		stmt.executeUpdate("INSERT INTO Notes (id, name, noteDate, userID, tagID, text) " +
		"VALUES (510, 'NoteNumber2', '2018-11-11', 120, 202, 'some  stuff')");
		stmt.executeUpdate("INSERT INTO Notes (id, name, noteDate, userID, tagID, text) " +
		"VALUES (520, 'NoteNumber3', '2018-12-12', 120, 212, 'some fun stuff')");
		stmt.executeUpdate("INSERT INTO Notes (id, name, noteDate, userID, tagID, text) " +
		"VALUES (530, 'NoteNumber4', '2017-01-01', 120, 212, ' fun stuff')");
		stmt.executeUpdate("INSERT INTO Notes (id, name, noteDate, userID, tagID, text) " +
		"VALUES (540, 'NoteNumber5', '2017-10-10', 120, 222, 'some fun stuff')");
		stmt.executeUpdate("INSERT INTO Notes (id, name, noteDate, userID, tagID, text) " +
		"VALUES (550, 'NoteNumber6', '2017-05-05', 120, 222, 'some fun ')");

		stmt.close();
		con.close();
		// create database manager
		mng = new DbManager();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database_test", "root",
				"Student007");
		Statement stmt = con.createStatement();

		// delete all notes that were created at test start
		stmt.executeUpdate("delete from Notes where id = 300 or id = 310 or id = 320");
		stmt.executeUpdate("delete from Notes where id = 400 or id = 410 or id = 420");
		stmt.executeUpdate("delete from Notes where id = 500 or id = 510 or id = 520 or id = 530 or id = 540 or id = " +
				"550");

		//delete all tags that were created at test start
		stmt.executeUpdate("delete from Tags where id >= 200 or id = 210 AND id <= 220");

		//delete all users that were created at test start
		stmt.executeUpdate("delete from Users where id = 100 or id = 110 or id = 120");
		
		//stmt.executeUpdate("delete from Notes where name = 'asdf'");
		stmt.close();
		con.close();
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void selectNoteByID() {
		Note note = new Note();
		Note note2 = mng.selectNoteByID(100, 300);
		assertNotEquals("selectNoteByID method does not work correctly1.", note.getName(), note2.getName());
		note.setName("NoteNumber1");
		assertEquals("selectNoteByID method does not work correctly2.", note.getName(), note2.getName());
		note2 = mng.selectNoteByID(100, 100);
		assertNotEquals("selectNoteByID method does not work correctly3.", note.getName(), note2.getName());
	}
	
	@Test
	public void findTagByName() {
		Tag tag = new Tag();
		Tag tag2 = mng.findTagByName(100, "MakeLoveNotWar");
		assertNotEquals("findTagByName method does not work correctly1", tag.getName(),tag2.getName());
		tag.setName("MakeLoveNotWar");
		assertEquals("findTagByName method does not work correctly2", tag.getName(),tag2.getName());
		tag2 = mng.findTagByName(1, "lol");
		assertNotEquals("findTagByName method does not work correctly3", tag.getName(),tag2.getName());
		tag2 = mng.findTagByName(110, "MakeLoveNotWar");
		assertEquals("findTagByName method does not work correctl4", tag.getName(),tag2.getName());
		
	}


	@Test
	public void findTagByID() {
		Tag tag = new Tag();
		Tag tag2 = mng.findTagByID(100, 200);
		assertNotEquals("findTagByName method does not work correctly1", tag.getId(),tag2.getId());
		tag.setId(200);
		assertEquals("findTagByName method does not work correctly2", tag.getId(),tag2.getId());
		tag2 = mng.findTagByID(1, 111);
		assertNotEquals("findTagByName method does not work correctly3", tag.getId(),tag2.getId());
	}
	
	@Test
	public void createTag() {
		assertTrue("createTag method does not work correctly1", mng.createTag(150, "asdf"));
		assertFalse("createTag method does not work correctly2",mng.createTag(100, "MakeLoveNotWar"));
	}
	
	@Test
	public void updateTag() {
		boolean result = false;
		Tag tag = mng.findTagByID(110, 201);
		result = mng.updateTag(110, 201, "MakeWarNotLove");
		assertTrue("Bad true - " + tag.getName(),result);
		tag = mng.findTagByID(110, 201);
		assertEquals("updateTag method does not work correctly1", tag.getName(),"MakeWarNotLove");
		assertTrue("updateTag method does not work correctly1",mng.updateTag(110, 201, "MakeWarNotLove"));
		assertFalse("updateTag method does not work correctly2",mng.updateTag(110, 205, "MakeWarNotLove"));
		assertFalse("updateTag method does not work correctly2",mng.updateTag(110, 205, null));
		assertTrue("updateTag method does not work correctly1",mng.updateTag(110, 201, "MakeLoveNotWar"));
	}
	
	@Test
	public void countTagsUsage() {
		List<String[]> stringArray;
		stringArray = mng.countTagsUsage(110);
		assertEquals("countTagUsage method does not work correctly1`", "3",stringArray.get(0)[1]);
		assertEquals("countTagUsage method does not work correctly2`", "MakeLoveNotWar",stringArray.get(0)[0]);
		stringArray = mng.countTagsUsage(100);
		assertEquals("countTagUsage method does not work correctly3`", "1",stringArray.get(2)[1]);
		assertEquals("countTagUsage method does not work correctly4`", "Another day in big city",stringArray.get(2)[0]);
		}
	
	//TODO watch DbManager.java for more information
	@Test
	public void selectNotesByTagName() {
		List<Note> noteList = new ArrayList<Note>();
		noteList = mng.selectNotesByTagName(110, "MakeLoveNotWar");
		assertEquals("selectNoteByTagName does not work correctly1.", 3, noteList.size());
		assertNotEquals("selectNoteByTagName does not work correctly2.", "Another", noteList.get(0).getName());
		//assertEquals("selectNoteByTagName does not work correctly3.", "NoteNumber1", noteList.get(0).getName());
		noteList = mng.selectNotesByTagName(10, "hello");
		assertEquals("selectNoteByTagName does not work correctly4.", 0, noteList.size());
		noteList = mng.selectNotesByTagName(100, "MakeLoveNotWar");
		assertEquals("selectNoteByTagName does not work correctly5.", 1, noteList.size());
		assertEquals("selectNoteByTagName does not work correctly6.", "NoteNumber1", noteList.get(0).getName());
	}

	@Test
	public void createNote() {
		assertTrue("createNote method does not work 1",mng.createNote("asdf", new Date(2018/10/10), 120, 222, "some kind of text"));
		//assertFalse("createNote method does not work 2",mng.createNote(null, new Date(2018-10-10), 120, 222, null));
	}
	
	@Test
	public void updateNote() {
		List<Note> noteList = mng.selectNotesByTagName(120, "Another day in big city");
		assertTrue("updateNote method does not work 1",mng.updateNote(noteList.get(0), "fdsa", new Date(2017/10/10), "funny textttt", "funnyTag"));
		assertEquals("updateNote method does not work 2",mng.findTagByName(noteList.get(0).getUserID(),"funnyTag").getName(),"funnyTag");
	}

	@Test
	public void deleteNote() {
		List<Note> noteList = mng.selectNotesByTagName(120, "Another day in big city");
		assertTrue("deleteNote method does not work 1",mng.deleteNote(noteList.get(0).getId(), 120));
		assertFalse("deleteNote method does not work 2",mng.deleteNote(400, 400));
	}
}
