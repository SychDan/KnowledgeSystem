package knowledge.service.user_service;

import knowledge.model.Surface;
import knowledge.model.User;
import knowledge.service.CRUDService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService extends CRUDService<User> {

    List<User> findAllBySurfaces(Surface surface);

    User findByUsername(String username);

    User getCurrentUser();

    List<User> findAllWithoutUserByEmailLike(String emailLike);

    void sendInvitationOn(String email, int surfaceId);

    User takeInvitationOn(String email, int surfaceId);

    void register(User userForm, HttpServletRequest request, HttpServletResponse response);

    boolean isOwner(Surface surface);

    boolean isAdmin(Surface surface);
}