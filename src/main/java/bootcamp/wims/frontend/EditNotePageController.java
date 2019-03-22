package bootcamp.wims.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import dbControl.*;

@Controller
public class EditNotePageController {

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String homePage(@RequestParam(value = "name", required = false) String name, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		model.addAttribute("name", "Vika");
		return "editNotePageHTML";
	}
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public boolean updateNote(Integer userID, Integer noteID, NoteVika oldNote, String newNoteName, Date newNoteDate, String newText, String newTagName) {
		
		//NoteVika should be changed to Note, when merged!
		
		// dbManager dbm = new dbManager();
		// Note note = dbm.selectNoteByIdD(Integer userID, Integer noteID);
		// Tag noteTag = dbm.findTagByName(Integer noteID);
		// boolean updateStatus = dbm.updateNote(Note oldNote, String newNoteName,
		// Date newNoteDate, String newText, String newTagName);

		// if (updateStatus == true) {
		// system.out.println("Your note is successfully updated");
		// }
		return true;
	}
	
	

}
