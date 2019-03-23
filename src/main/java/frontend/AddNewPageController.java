package bootcamp.wims.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class AddNewPageController {
	
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String homePage(@RequestParam(value = "name", required = false) String name, HttpServletRequest request,
	                       HttpServletResponse response, Model model) {
		model.addAttribute("name", "Vika1");
		model.addAttribute("name", "Vika2");
		model.addAttribute("name", "Vika3");
		model.addAttribute("name", "Vika4");
		model.addAttribute("name", "Vika5");
		return "addNewPage";
	}
	//static String enteredString;
	
//	@RequestMapping(value = "/add", method = RequestMethod.POST)
////	   public static String saveInfoFromInput() {
//	   public static void main (String[] args) {
//	      Scanner s = new Scanner(System.in);
//	      
//	      System.out.print("Enter first number: " + enteredString);
//	      
//	      enteredString = s.nextLine();
//	      
//	    
//	     // return enteredString;
//	   }
//	

//	@RequestMapping(value = "/add", method = RequestMethod.GET)
//    public String authenticateUser(@RequestParam("name") String name, @RequestParam("date") Date date, @RequestParam("tag") String tag, @RequestParam("description") String description, Model model) {
//        System.out.println("coming in controller    " +name +", "+ date + ", " + tag + ", " + description);  
//        model.addAttribute("message", "Hello Spring MVC Framework!");
//        
//        model.addAttribute(name, description);
//        return "success";
//    }

}
