package bootcamp.wims.frontend;

import bootcamp.wims.auth.model.User;
import bootcamp.wims.auth.service.SecurityService;
import bootcamp.wims.auth.service.UserService;
import bootcamp.wims.auth.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegistrationFormController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registrationForm(Model model) {
		model.addAttribute("user", new User());
		return "registrationForm";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
		userValidator.validate(userForm, bindingResult);

		if(bindingResult.hasErrors()) {
			return "registrationForm";
		}

		userService.save(userForm);

		securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

		return "redirect:/";
	}
}
