package knowledge.controller;

import knowledge.model.Surface;
import knowledge.service.page_service.PageService;
import knowledge.service.surface_service.SurfaceService;
import knowledge.service.user_service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
public class SurfaceController {

    @Autowired
    private UserService userService;

    @Autowired
    private SurfaceService surfaceService;

    @Autowired
    private PageService pageService;


    @RequestMapping(value = "/surfaces", method = RequestMethod.GET)
    public String listOfSurfaces(Model model) {
        model.addAttribute("surfaceList", surfaceService.findAllByCurrentUser());
        return "surfaceList";   // <--- Instead empty quotes, write suitable jsp/html filename;
    }

    @RequestMapping(value = "/surfaces/new", method = RequestMethod.GET)
    public String addNewSurface(Model model) {
        Surface surface = surfaceService.getNewSurface();
        model.addAttribute("newSurface", surface);
        model.addAttribute("surfaceList", surfaceService.findAllByCurrentUser());
        return "surfaceEditor";  // <--- Instead empty quotes, write suitable jsp/html filename;
    }

    @RequestMapping(value = "/surfaces/delete/{surfaceId}", method = RequestMethod.GET)
    public String deleteSurface(@PathVariable int surfaceId) {
        Surface surface = surfaceService.getById(surfaceId);

        if (!userService.isAdmin(surface))
            return "pageNotFound";

        surfaceService.delete(surface);
        return "redirect:/surfaces";
    }

    @RequestMapping(value = "/surfaces/show/{surfaceId}", method = RequestMethod.GET)
    public String showSurface(@PathVariable int surfaceId, Model model) {
        Surface surface = surfaceService.getById(surfaceId);

        if (!surface.getAccessType().equals("public") && !userService.isOwner(surface)) {
            return "pageNotFound";
        }
        model.addAttribute("surface", surfaceService.getById(surfaceId));
        model.addAttribute("pageList", pageService.findAllBySurface(surface));
        model.addAttribute("surfaceList", surfaceService.findAllByCurrentUser());
        model.addAttribute("user", userService.getCurrentUser());
        model.addAttribute("users", userService.findAllBySurfaces(surface));
        return "showingSurface";
    }

    @RequestMapping(value = "/surfaces/save", method = RequestMethod.POST)
    public String saveSurface(Surface surface) {
        surfaceService.saveOrUpdate(surface);
        return "redirect:/surfaces/show/"+surface.getId();
    }

    @RequestMapping(value = "/surfaces/edit/{surfaceId}", method = RequestMethod.GET)
    public String editSurface(@PathVariable int surfaceId, Model model) {
        Surface surface = surfaceService.getById(surfaceId);

        if (!userService.isAdmin(surface))
            return "pageNotFound";

        model.addAttribute("newSurface", surface);
        return "surfaceEditor";
    }

    @RequestMapping(value = "/surfaces/{surfaceId}/findUser", method = RequestMethod.GET)
    public String findUserByEmail(HttpServletRequest request, Model model, @PathVariable int surfaceId) {
        Surface surface = surfaceService.getById(surfaceId);

        if (!userService.isAdmin(surface))
            return "pageNotFound";

        String email = request.getParameter("email");
        if (email != null) {
            model.addAttribute("foundUsers", userService.findAllWithoutUserByEmailLike(email));
            model.addAttribute("email", email);
        }
        model.addAttribute("surfaceId", surfaceId);
        model.addAttribute("surfaceList", surfaceService.findAllByCurrentUser());
        return "findUserByEmail";
    }

    @RequestMapping(value = "/surfaces/{surfaceId}/addUser/{userId}", method = RequestMethod.GET)
    public String addUser(@PathVariable int surfaceId, @PathVariable int userId) {
        Surface surface = surfaceService.getById(surfaceId);

        if (surface.getAccessType().equals("private") && !userService.isAdmin(surface))
            return "pageNotFound";

        surfaceService.addUser(surface, userService.getById(userId));
        return "redirect:/surfaces/show/" + surfaceId;
    }

    @RequestMapping(value = "/surfaces/public", method = RequestMethod.GET)
    public String getPublicSurfaces(Model model, HttpServletRequest request) {
        String word = request.getParameter("word");
        model.addAttribute("publicSurfacesByPopularity", surfaceService.findAllPublicSurfacesByPopularity());
        model.addAttribute("surfaceList", surfaceService.findAllByCurrentUser());
        model.addAttribute("foundPublicSurfacesByName", surfaceService.findAllPublicSurfacesByNameLike(word));
        model.addAttribute("word", word);
        return "publicSurfaces";
    }

    @RequestMapping(value = "/surfaces/{surfaceId}/findPages", method = RequestMethod.GET)
    public String findPages(@PathVariable int surfaceId, HttpServletRequest request, Model model) {
        Surface surface = surfaceService.getById(surfaceId);

        if (!surface.getAccessType().equals("public") && !userService.isOwner(surface))
            return "pageNotFound";

        String word = request.getParameter("word");
        model.addAttribute("foundPagesByContent", pageService.findAllBySurfaceAndContentLike(surface, word));
        model.addAttribute("foundPagesByName", pageService.findAllBySurfaceAndNameLike(surface, word));
        model.addAttribute("surface", surface);
        model.addAttribute("surfaceList", surfaceService.findAllByCurrentUser());
        model.addAttribute("word", word);
        return "foundPages";
    }
}