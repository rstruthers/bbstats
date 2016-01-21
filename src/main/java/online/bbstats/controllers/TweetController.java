package online.bbstats.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import online.bbstats.domain.Tweet;
import online.bbstats.domain.User;

@Controller
public class TweetController {

	@RequestMapping("/tweethome")
	public String home() {
		return "searchPage";
	}

	@RequestMapping("/result")
	public String hello(@RequestParam(defaultValue = "masterSpringMVC4") String search, Model model) {
		model.addAttribute("search", search);
		model.addAttribute("tweet.user.name", "rumbles");

		User user = new User();
		user.setName("rumbles");
		List<Tweet> tweets = new ArrayList<Tweet>();
		tweets.add(new Tweet("hello", user));
		tweets.add(new Tweet("this is a tweet", user));
		model.addAttribute("tweets", tweets);
		return "resultPage";
	}
	
	@RequestMapping(value = "/postSearch", method = RequestMethod.POST)
	public String postSearch(HttpServletRequest request,
	    RedirectAttributes redirectAttributes) {
		  String search = request.getParameter("search");
	        if (search.toLowerCase().contains("struts")) {
	                redirectAttributes.addFlashAttribute("error", "Try using spring instead!");
	                return "redirect:/";
	        }
	        redirectAttributes.addAttribute("search", search);
	        return "redirect:result";
	}
}