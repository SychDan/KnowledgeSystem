package knowledge.controller;

import knowledge.model.Page;
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
public class PageController {

    @Autowired
    private PageService pageService;

    @Autowired
    private SurfaceService surfaceService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/surfaces/{surfaceId}/pages/new", method = RequestMethod.GET)
    public String addNewPage(@PathVariable int surfaceId, Model model) {
        Surface surface = surfaceService.getById(surfaceId);

        if (!userService.isOwner(surface) || (surface.getAccessType().equals("public") && !userService.isAdmin(surface)))
            return "pageNotFound";

        Page page = new Page();
        page.setCreatorName(userService.getCurrentUser().getSurname());
        model.addAttribute("page", page);
        model.addAttribute("surfaceList", surfaceService.findAllByCurrentUser());
        model.addAttribute("surfaceId", surfaceId);
        return "pageEditor"; // <--- Instead empty quotes, write suitable jsp filename;
    }

    @RequestMapping(value = "/surfaces/{surfaceId}/pages/addChild/{pageId}", method = RequestMethod.GET)
    public String addChild(@PathVariable int surfaceId, @PathVariable int pageId, Model model) {
        Surface surface = surfaceService.getById(surfaceId);

        if (!userService.isOwner(surface) || (surface.getAccessType().equals("public") && !userService.isAdmin(surface)))
            return "pageNotFound";

        model.addAttribute("page", pageService.getNewChildFor(pageId));
        model.addAttribute("surfaceId", surfaceId);
        return "pageEditor";
    }

    @RequestMapping(value = "/surfaces/{surfaceId}/pages/show/{pageId}", method = RequestMethod.GET)
    public String showPage(@PathVariable int surfaceId, @PathVariable int pageId, Model model) {
        Page page = pageService.getById(pageId);
        Surface surface = surfaceService.getById(surfaceId);

        if (!surface.getAccessType().equals("public") && !userService.isOwner(surface))
            return "pageNotFound";

        model.addAttribute("pageList", pageService.findAllBySurface(surface));
        model.addAttribute("page", page);
        model.addAttribute("surfaceList", surfaceService.findAllByCurrentUser());
        model.addAttribute("user", userService.getCurrentUser());
        return "showingPage";
    }

    @RequestMapping(value = "/surfaces/{surfaceId}/pages/delete/{pageId}", method = RequestMethod.GET)
    public String deletePage(@PathVariable int surfaceId, @PathVariable int pageId) {
        Surface surface = surfaceService.getById(surfaceId);

        if (!userService.isOwner(surface) || (surface.getAccessType().equals("public") && !userService.isAdmin(surface)))
            return "pageNotFound";

        pageService.delete(pageId);
        return "redirect:/surfaces/show/" + surfaceId;
    }


    @RequestMapping(value = "/surfaces/{surfaceId}/pages/save", method = RequestMethod.POST)
    public String savePage(@PathVariable int surfaceId, Page page) {
        pageService.setSurface(page, surfaceService.getById(surfaceId));
        return "redirect:/surfaces/" + surfaceId + "/pages/show/" + page.getId();
    }

    @RequestMapping(value = "/surfaces/{surfaceId}/pages/edit/{pageId}", method = RequestMethod.GET)
    public String editPage(@PathVariable int surfaceId, @PathVariable int pageId, Model model) {
        Surface surface = surfaceService.getById(surfaceId);

        if (!userService.isOwner(surface) || (surface.getAccessType().equals("public") && !userService.isAdmin(surface)))
            return "pageNotFound";

        model.addAttribute("page", pageService.getById(pageId));
        return "pageEditor";
    }


    @RequestMapping(value = "/surfaces/{surfaceId}/pages/chooseSurface/{pageId}", method = RequestMethod.GET)
    public String chooseSurfaceToParticipate(@PathVariable int surfaceId, @PathVariable int pageId, Model model) {
        Surface surface = surfaceService.getById(surfaceId);

        if (!userService.isOwner(surface) || (surface.getAccessType().equals("public") && !userService.isAdmin(surface)))
            return "pageNotFound";

        model.addAttribute("surfaceId", surfaceId);
        model.addAttribute("surfaceList", surfaceService.getSurfacesWithoutSurface(surface));
        model.addAttribute("pageId", pageId);
        return "chooseSurfaceToParticipate";
    }

    @RequestMapping(value = "/surfaces/participatePageToSurface", method = RequestMethod.GET)
    public String participateToSurface(HttpServletRequest request) {
        int surfaceId = Integer.parseInt(request.getParameter("surfaceId"));
        Surface surface = surfaceService.getById(surfaceId);

        if (!userService.isAdmin(surface))
            return "pageNotFound";

        int pageId = Integer.parseInt(request.getParameter("pageId"));
        Page page = pageService.getById(pageId);
        page.setAncestorId(0);
        page.setLevel(1);
        pageService.participateToSurface(surfaceId, pageId);
        return "redirect:/surfaces/show/" + surfaceId;
    }

    @RequestMapping(value = "/surfaces/{surfaceId}/pages/choosePageToParticipate/{pageId}", method = RequestMethod.GET)
    public String choosePageToParticipate(@PathVariable int surfaceId, @PathVariable int pageId, Model model) {
        Surface surface = surfaceService.getById(surfaceId);

        if (!userService.isOwner(surface) || (surface.getAccessType().equals("public") && !userService.isAdmin(surface)))
            return "pageNotFound";

        Page page = pageService.getById(pageId);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageList", pageService.findAllPagesBySurfaceWithoutPage(page, surface));
        return "choosePageToParticipate";
    }

    @RequestMapping(value = "/surfaces/{surfaceId}/participatePageToPage", method = RequestMethod.GET)
    public String participateToPage(@PathVariable int surfaceId, HttpServletRequest request) {
        Surface surface = surfaceService.getById(surfaceId);

        if (!userService.isOwner(surface) || (surface.getAccessType().equals("public") && !userService.isAdmin(surface)))
            return "pageNotFound";

        int pageId = Integer.parseInt(request.getParameter("page"));
        int toPageId = Integer.parseInt(request.getParameter("toPage"));
        pageService.participateToPage(pageId, toPageId);
        return "redirect:/surfaces/show/" + surfaceId;
    }
}