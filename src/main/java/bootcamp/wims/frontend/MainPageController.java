package bootcamp.wims.frontend;

import bootcamp.wims.auth.model.User;
import bootcamp.wims.auth.repository.UserRepository;
import bootcamp.wims.model.Note;
import bootcamp.wims.model.NoteRepository;
import bootcamp.wims.model.Tag;
import bootcamp.wims.model.TagRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class MainPageController {
	private final UserRepository userRepository;
	private final NoteRepository noteRepository;

	@Autowired
	public MainPageController(UserRepository userRepository, NoteRepository noteRepository) {
		this.userRepository = userRepository;
		this.noteRepository = noteRepository;
	}

	@RequestMapping(value = "/", method = GET)
	public String homePage(@RequestParam(value = "search", required = false, defaultValue = "") String search,
			@RequestParam(value = "message", required = false, defaultValue = "") String message, Principal principal,
			Authentication authentication, Model model) {
		System.out.println(principal);
		if (principal == null) {
			System.out.println(authentication);
			return "redirect:/welcome";
		}

		User user = userRepository.findByUsername(principal.getName());
		model.addAttribute("username", user.getUsername());
		model.addAttribute("userId", user.getId());

		List<Note> notes = noteRepository.findAllByUserIDAndNameContainingOrTextContaining(user.getId(), search);

		Set<String> tagNames = new HashSet<>();
		for (Note note : notes) {
			tagNames.add(note.getTag().getName());
		}

		model.addAttribute("mainPageSearch", search);
		model.addAttribute("message", message);
		model.addAttribute("notes", notes);
		model.addAttribute("tags", tagNames);
		return "mainPage";
	}

	// /delete_note?noteId=123333
	@RequestMapping(value = "/delete_note", method = GET)
	public String deleteNote(@RequestParam("noteId") Integer noteId, Principal principal) {
		User user = userRepository.findByUsername(principal.getName());
		noteRepository.deleteByIdAndUserID(noteId, user.getId());
		return "redirect:/";
	}
	
	@RequestMapping(value = "/error", method = GET)
	public String errorMessage(){
		return "redirect:/error";
	}

}
