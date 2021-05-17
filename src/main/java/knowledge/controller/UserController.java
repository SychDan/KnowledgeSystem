package knowledge.controller;

import knowledge.model.User;
import knowledge.service.user_service.UserService;
import knowledge.validator.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model, HttpServletRequest request) {
        model.addAttribute("userForm", new User());
        String email = request.getParameter("email");
        String surfaceId = request.getParameter("surfaceId");
        if (email != null && surfaceId != null) {
            model.addAttribute("userForm", userService.takeInvitationOn(email, Integer.parseInt(surfaceId)));
        }
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model, HttpServletResponse response, HttpServletRequest request) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.register(userForm, request, response);

        return "redirect:/";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = "/surfaces/{surfaceId}/sendInvitationOn", method = RequestMethod.GET)
    public String sendInvitation(@PathVariable int surfaceId, HttpServletRequest request) {
        String email = request.getParameter("email");
            userService.sendInvitationOn(email, surfaceId);

        return "redirect:/surfaces/" + surfaceId + "/findUser";
    }

}