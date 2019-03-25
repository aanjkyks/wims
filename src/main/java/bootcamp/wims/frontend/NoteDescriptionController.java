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

		return "noteDescriptionPage";
	}
}
