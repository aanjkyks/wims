package bootcamp.wims.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainPageController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String homePage(@RequestParam(value = "name", required = false) String name, HttpServletRequest request,
	                       HttpServletResponse response, Model model) {
		model.addAttribute("name", name);
		return "mainPage";
	}

}
