package front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
public class MainPageController {

	@RequestMapping(value = "/", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String homePage(@RequestParam(value = "name", required = false) String name, HttpServletRequest request,
			HttpServletResponse response) {
		StringBuilder sb = new StringBuilder();
		sb.append("<a href='/mainPage'>Submit<a><br/>\n");

		// Following is also redundant because status is OK by default:
		response.setStatus(HttpServletResponse.SC_OK);
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println("This is WIMS project!");

	}

}
