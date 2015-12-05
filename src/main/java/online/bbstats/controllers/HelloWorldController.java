package online.bbstats.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

   @RequestMapping(value = "/hello", method = RequestMethod.GET)
   public ModelAndView hello() {
       ModelAndView mav = new ModelAndView();
       mav.setViewName("hello");
        
       String str = "Hello World!";
       mav.addObject("message", str);

       return mav;
   }
   
   @RequestMapping(value = "/", method = RequestMethod.GET)
   public ModelAndView welcome() {
       ModelAndView mav = new ModelAndView();
       mav.setViewName("welcome");
        

       return mav;
   }
   
   @RequestMapping(value = "/bbstats", method = RequestMethod.GET)
   public ModelAndView bbstats() {
       ModelAndView mav = new ModelAndView();
       mav.setViewName("bbstats");
        

       return mav;
   }
}
