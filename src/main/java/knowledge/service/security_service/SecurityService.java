package knowledge.service.security_service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);

    void refreshCookie(HttpServletRequest request, HttpServletResponse response);
}
