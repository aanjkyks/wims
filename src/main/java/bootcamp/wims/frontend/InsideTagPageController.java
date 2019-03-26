package bootcamp.wims.frontend;

import bootcamp.wims.auth.model.User;
import bootcamp.wims.auth.repository.UserRepository;
import bootcamp.wims.model.Note;
import bootcamp.wims.model.NoteRepository;
import bootcamp.wims.model.Tag;
import bootcamp.wims.model.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

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

    @RequestMapping(value = "/inside/{tagName}", method = GET)
    public String insideTagPage(@PathVariable("tagName") String tagName, Principal principal, Model model) {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userId", user.getId());

        List<Note> notes = noteRepository.findAllByUserIDAndTagName(user.getId(), tagName);
        Tag tags = tagRepository.findFirstByUserIDAndName(user.getId(), tagName);

        model.addAttribute("notes", notes);
        model.addAttribute("tag", tags);
        return "insideTagPageHTML";
    }
}
