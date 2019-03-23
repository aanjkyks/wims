package bootcamp.wims.frontend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.ui.ModelMap;


// this is for testing purpose only, when DB related classes will be available, should be deleted!
public class NoteVika {
	String name; 
	Date date;
	String tag; 
	String note;
	
	public NoteVika(String name, String tag, String note){
		this.name = name;
		this.tag = tag;
		this.note = note;
		
	}
	List<String> myList = new ArrayList<>();
	
	public List<String> populateFields(ModelMap model) {
		
		myList.add("note1");
		myList.add("note2");
		myList.add("note3");
		myList.add("note4");
		myList.add("note5");
		myList.add("note6");
		
	System.out.println(model.addAllAttributes(myList));
		return Arrays.asList("note1", "note2", "note3", "note4");
	}
	
	public List<String> getPopulateFields() {
		return myList;
	}
//	public static void main(String[] args) {
//		System.out.println("hello");
//	}
}
