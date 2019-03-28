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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Controller
public class EditNotePageController {
	private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
	private final NoteRepository noteRepository;
	private final UserRepository userRepository;
	private final TagRepository tagRepository;

	@Autowired
	public EditNotePageController(NoteRepository notesRepository, UserRepository userRepository,
			TagRepository tagRepository) {
		this.noteRepository = notesRepository;
		this.userRepository = userRepository;
		this.tagRepository = tagRepository;
	}

	@RequestMapping(value = "/edit/{noteId:[\\d]+}", method = RequestMethod.GET)
	public String ModelAndView(@PathVariable int noteId,
			@RequestParam(value = "error", required = false, defaultValue = "") String error, Model model) {
		Optional<Note> noteOptional = noteRepository.findById(noteId);

		if (noteOptional.isPresent()) {
			Note note = noteOptional.get();
			System.out.println(note);
			EditNoteModel noteModel = new EditNoteModel(note.getId(), note.getDate(), note.getName(),
					note.getTag().getName(), note.getText());
			model.addAttribute("editNote", noteModel);

			return editNote(noteModel, model, error);
		} else {
			return "redirect:/404";
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addNewNote(@RequestParam(value = "tag", required = false, defaultValue = "") String tag,
			@RequestParam(value = "error", required = false, defaultValue = "") String error, Model model) {
		EditNoteModel emptyModel = new EditNoteModel();
		emptyModel.setTags(tag);
		return editNote(emptyModel, model, error);
	}

	private String editNote(EditNoteModel note, Model model, String error) {
		model.addAttribute("editNote", note);
		model.addAttribute("savePath", "/save_note");
		model.addAttribute("error", error);
		return "editNote";
	}

	@RequestMapping(value = "/save_note", method = RequestMethod.POST)
	public String saveNote(@ModelAttribute("note") EditNoteModel note, Principal principal, Model model)
			throws ParseException {
		System.out.println(note);

		User user = userRepository.findByUsername(principal.getName());
		try {
			Tag tags = tagRepository.findFirstByUserIDAndName(user.getId(), note.getTags());
			if (tags == null)
			tags = new Tag(null, user.getId(), note.getTags());

			Note newNote = new Note(note.isNew() ? null : note.getId(), note.getName(),
					dateFormatter.parse(note.getDate()), user.getId(), tags, note.getDescription());
			Note savedNote = noteRepository.save(newNote);
			System.out.println(savedNote);

			if (savedNote != null) {
				return "redirect:/?message=note+saved";
			} else {
				return "redirect:/add?error";
			}

		} catch (Exception e) {
			if (note.isNew()) {
				return "redirect:/add?error=error+creating+new+note";
			} else {
				return "redirect:/edit/" + note.getId() + "?error=error+updating+note";
			}
		}
	}
}
