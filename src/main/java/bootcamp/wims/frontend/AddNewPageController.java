package bootcamp.wims.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;


@Controller
public class AddNewPageController {

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String ModelAndView(HttpServletResponse response, Model model) {
		FormModel emptyModel = new FormModel();
		model.addAttribute("note", emptyModel);
		return "Test_addNewPage2";
	}

	@RequestMapping(value = "/add_save", method = RequestMethod.POST)
	public String homePage(@ModelAttribute("note") FormModel note, Model model) {
		System.out.println(note);
		return "Test_addNewPage2";
	}

	public static class FormModel {
		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTags() {
			return tags;
		}

		public void setTags(String tags) {
			this.tags = tags;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String date;
		public String name;
		public String tags;
		public String description;

		@Override
		public String toString() {
			return "FormModel{" + "date='" + date + '\'' + ", name='" + name + '\'' + ", tags='" + tags + '\'' + ", " + "description='" + description + '\'' + '}';
		}
	}
}
