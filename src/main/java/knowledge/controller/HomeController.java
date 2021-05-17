package knowledge.controller;

import knowledge.service.surface_service.SurfaceService;
import knowledge.service.user_service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @Autowired
    private SurfaceService surfaceService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String goToHomePage(Model model) {
        model.addAttribute("surfaceList", surfaceService.findAllByCurrentUser());
        model.addAttribute("user", userService.getCurrentUser());
        return "home";
    }
}
