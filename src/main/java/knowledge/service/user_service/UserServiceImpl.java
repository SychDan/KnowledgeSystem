package knowledge.service.user_service;

import knowledge.dao.UserRepository;
import knowledge.model.Mail;
import knowledge.model.Surface;
import knowledge.model.User;
import knowledge.service.email_service.EmailService;
import knowledge.service.security_service.SecurityService;
import knowledge.service.surface_service.SurfaceService;

import freemarker.template.TemplateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Profile("springdatajpa")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SurfaceService surfaceService;

    @Override
    public List<User> listAll() {
        List<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add);
        return userList;
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void saveOrUpdate(User domainObject) {
        domainObject.setPassword(bCryptPasswordEncoder.encode(domainObject.getPassword()));
        userRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public void delete(User domainObject) {
        userRepository.delete(domainObject);
    }

    @Override
    public List<User> findAllBySurfaces(Surface surface) {
        return userRepository.findAllBySurfaces(surface);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getCurrentUser() {
        return findByUsername(securityService.findLoggedInUsername());
    }

    @Override
    public List<User> findAllWithoutUserByEmailLike(String emailLike) {
        emailLike += "%";
        return userRepository.findAllByIdNotAndEmailLikeOrderBySurname(getCurrentUser().getId(), emailLike);
    }

    @Override
    public void sendInvitationOn(String email, int surfaceId) {
        Mail mail = new Mail();
        mail.setTo(email);
        mail.setSubject("Приглашение");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("sender_surname", getCurrentUser().getSurname());
        model.put("email", email);
        model.put("surfaceId", surfaceId);
        model.put("surface_name", surfaceService.getById(surfaceId).getName());
        mail.setModel(model);

        try {
            emailService.sendSimpleMessage(mail);
        }
        catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User takeInvitationOn(String email, int surfaceId) {
        User user = new User();
        user.setEmail(email);
        user.addSurface(surfaceService.getById(surfaceId));
        return user;
    }

    @Override
    public void register(User userForm, HttpServletRequest request, HttpServletResponse response) {
        saveOrUpdate(userForm);
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        securityService.refreshCookie(request, response);
    }

    @Override
    public boolean isOwner(Surface surface) {
        User user = getCurrentUser();
        return user.getSurfaces().contains(surface);
    }

    @Override
    public boolean isAdmin(Surface surface) {
        User user = getCurrentUser();
        return surface.getAdminId() == user.getId();
    }
}