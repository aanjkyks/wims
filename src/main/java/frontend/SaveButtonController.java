package bootcamp.wims.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SaveButtonController {
	// Note note = new Note(111122);

	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public boolean updateNote() {
		System.out.println("hello");
		return false;
	}
	// note.updateNote();

}
