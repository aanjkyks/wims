package bootcamp.wims.frontend;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bootcamp.wims.auth.model.User;
import bootcamp.wims.auth.repository.UserRepository;
import bootcamp.wims.model.Note;
import bootcamp.wims.model.NoteRepository;
import bootcamp.wims.model.Tag;
import bootcamp.wims.model.TagRepository;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class InsideTagPageController {
	private final UserRepository userRepository;
	private final NoteRepository noteRepository;
	private final TagRepository tagRepository;

	@Autowired
	public InsideTagPageController(UserRepository userRepository, NoteRepository noteRepository,
			TagRepository tagRepository) {
		this.userRepository = userRepository;
		this.noteRepository = noteRepository;
		this.tagRepository = tagRepository;
	}

	// @RequestMapping(value = "/inside", method = RequestMethod.GET)
	// public String homePage(@RequestParam(value = "name", required = false)
	// String name, HttpServletRequest request,
	// HttpServletResponse response, Model model) {
	// return "insideTagPageHTML";
	// }

	// List<Tag> allNotesByTags =
	// tagRepository.findByUserID(userRepository.findAllById(user.getID()));

	@RequestMapping(value = "/inside", method = GET)
	public String homePage(Principal principal, Authentication authentication, Model model) {
		System.out.println(principal);
		if (principal == null) {
			System.out.println(authentication);
			return "redirect:/welcome";
		}

		User user = userRepository.findByUsername(principal.getName());
		model.addAttribute("username", user.getUsername());
		model.addAttribute("userId", user.getId());
		
		List<Note> notes = noteRepository.findAllByUserID(user.getId());
		List<Tag> tags = tagRepository.findByUserID(user.getId());
		
		model.addAttribute("notes", notes);
		model.addAttribute("tags", tags);
		return "insideTagPageHTML";
	}
}
