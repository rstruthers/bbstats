package online.bbstats.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserAdminController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping("/admin/users")
    public String getHomePage() {
        LOGGER.debug("Getting user admin page");
        return "user_admin";
    }
}
