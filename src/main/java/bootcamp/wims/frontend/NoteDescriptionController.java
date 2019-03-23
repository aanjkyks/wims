package bootcamp.wims.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.List;


@Controller
public class NoteDescriptionController {
	//		@Autowired
	//	private Object db;

	//NoteVika note = new NoteVika();
	
	@RequestMapping(value = "/note", method = RequestMethod.GET)
	public String noteDescriptionPage(String id, ModelMap model) {

		NoteVika note = new NoteVika("111", "fff", "dd" ); //db.getNote(id);
	
		//
		//		model.addAttribute("noteDescription", note.getDescription());
		
		return "noteDescriptionPage";
	}

//	private static class Note {
//		public String getDescription() {
//			return "description";
//		}
//	}

	@ModelAttribute("fields")
	public List<String> populateFields() {
		return Arrays.asList("hello", "bar", "blablabla", "blabla", "blabla");
		//return note.getPopulateFields();
	}
	
	@ModelAttribute("notes")
	public List<String> noteName() {
		return Arrays.asList("aaa", "ssss", "dddd", "fff", "gggg");
		//return note.getPopulateFields();
	}
	

}