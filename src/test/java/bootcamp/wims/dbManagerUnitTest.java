//package bootcamp.wims;
//
//import bootcamp.wims.dbControl.DbManager;
//import bootcamp.wims.model.Note;
//import org.junit.*;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class dbManagerUnitTest {
//	static DbManager mng;
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database_test", "root",
//				"Student007");
//		Statement stmt = con.createStatement();
//
//		// create 3 new users
//		stmt.executeUpdate("INSERT INTO Users VALUES (100, 'Karlo', 123)");
//		stmt.executeUpdate("INSERT INTO Users VALUES (110, 'Frida', 123)");
//		stmt.executeUpdate("INSERT INTO Users VALUES (120, 'Ismal', 123)");
//
//		// create 3 new Tags
//		stmt.executeUpdate("INSERT INTO Tags VALUES (200, 'MakeLoveNotWar')");
//		stmt.executeUpdate("INSERT INTO Tags VALUES (210, 'Some strange stuff')");
//		stmt.executeUpdate("INSERT INTO Tags VALUES (220, 'Another day in big city')");
//
//		// create 3 Notes for new user 1 with different tags
//		stmt.executeUpdate("INSERT INTO Notes VALUES (300, 'NoteNumber1', '2018-10-10', 100, 200, 'some fun text " +
//				"here')");
//		stmt.executeUpdate("INSERT INTO Notes VALUES (310, 'NoteNumber2', '2018-11-11', 100, 210, 'some fun text " +
//				"here')");
//		stmt.executeUpdate("INSERT INTO Notes VALUES (320, 'NoteNumber3', '2018-12-12', 100, 220, 'some fun text " +
//				"here')");
//
//		// create 3 Notes for new user 2 with same tags
//		stmt.executeUpdate("INSERT INTO Notes VALUES (400, 'NoteNumber1', '2017-01-01', 110, 200, 'some other " +
//				"stuff')");
//		stmt.executeUpdate("INSERT INTO Notes VALUES (410, 'NoteNumber2', '2017-10-10', 110, 200, 'some other " +
//				"stuff')");
//		stmt.executeUpdate("INSERT INTO Notes VALUES (420, 'NoteNumber3', '2017-05-05', 110, 200, 'some other " +
//				"stuff')");
//
//		// create 6 Notes for new user 3 (all 3 tags, repeats twice)
//		stmt.executeUpdate("INSERT INTO Notes VALUES (500, 'NoteNumber1', '2018-10-10', 120, 200, 'some fun stuff')");
//		stmt.executeUpdate("INSERT INTO Notes VALUES (510, 'NoteNumber2', '2018-11-11', 120, 200, 'some  stuff')");
//		stmt.executeUpdate("INSERT INTO Notes VALUES (520, 'NoteNumber3', '2018-12-12', 120, 210, 'some fun stuff')");
//		stmt.executeUpdate("INSERT INTO Notes VALUES (530, 'NoteNumber4', '2017-01-01', 120, 210, ' fun stuff')");
//		stmt.executeUpdate("INSERT INTO Notes VALUES (540, 'NoteNumber5', '2017-10-10', 120, 220, 'some fun stuff')");
//		stmt.executeUpdate("INSERT INTO Notes VALUES (550, 'NoteNumber6', '2017-05-05', 120, 220, 'some fun ')");
//
//		stmt.close();
//		con.close();
//		// create database manager
//		mng = new DbManager();
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database_test", "root",
//				"Student007");
//		Statement stmt = con.createStatement();
//
//		// delete all notes that were created at test start
//		stmt.executeUpdate("delete from Notes where id = 300 or id = 310 or id = 320");
//		stmt.executeUpdate("delete from Notes where id = 400 or id = 410 or id = 420");
//		stmt.executeUpdate("delete from Notes where id = 500 or id = 510 or id = 520 or id = 530 or id = 540 or id = " +
//				"550");
//
//		//delete all tags that were created at test start
//		stmt.executeUpdate("delete from Tags where id = 200 or id = 210 or id = 220");
//
//		//delete all users that were created at test start
//		stmt.executeUpdate("delete from Users where id = 100 or id = 110 or id = 120");
//
//		stmt.close();
//		con.close();
//	}
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void selectNoteByID() {
//		Note note = new Note();
//		Note note2 = mng.selectNoteByID(100, 300);
//		assertNotEquals("selectNoteByID method does not work correctly.", note.getName(), note2.getName());
//		note.setName("NoteNumber1");
//		assertEquals("selectNoteByID method does not work correctly.", note.getName(), note2.getName());
//		note2 = mng.selectNoteByID(100, 100);
//		assertNotEquals("selectNoteByID method does not work correctly.", note.getName(), note2.getName());
//	}
//
//	@Test
//	public void selectNotesByTagName() {
//		Note note = new Note();
//		List<Note> noteList = new ArrayList<Note>();
//		noteList = mng.selectNotesByTagName(1, "test");
//		assertEquals("selectNoteByTagName does not work correctly.", 1, noteList.size());
//		assertNotEquals("selectNoteByTagName does not work correctly.", note, noteList.get(0));
//		note.setName("first note");
//		assertEquals("selectNoteByTagName does not work correctly.", note.getName(), noteList.get(0).getName());
//		noteList = mng.selectNotesByTagName(10, "hello");
//		assertNotEquals("selectNoteByTagName does not work correctly.", 1, noteList.size());
//		noteList = mng.selectNotesByTagName(2, "vacation");
//		assertEquals("selectNoteByTagName does not work correctly.", 2, noteList.size());
//		assertEquals("selectNoteByTagName does not work correctly.", "asdf", noteList.get(1).getName());
//	}
//
//	@Test
//	public void countTagsUsage() {
//		List<String[]> stringArray;
//		stringArray = mng.countTagsUsage(1);
//		for(String[] strArray : stringArray)
//			System.out.println(strArray[0] + " " + strArray[1]);
//	}
//
//	@Test
//	public void updateNote() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void deleteNote() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void insertNote() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void findUserByNameAndPswrd() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void findUserByName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void findTagByName() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void findTagByID() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void createTag() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void insertNewUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void createNote() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void updateTag() {
//		fail("Not yet implemented");
//	}
//}
