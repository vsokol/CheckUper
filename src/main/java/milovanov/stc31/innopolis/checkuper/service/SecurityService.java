package milovanov.stc31.innopolis.checkuper.service;

import milovanov.stc31.innopolis.checkuper.pojo.ResultCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements ISecurityService {
    AuthenticationManager authManger;
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityService(AuthenticationManager authManger, UserDetailsServiceImpl userDetailsService) {
        this.authManger = authManger;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void autoLogin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authManger.authenticate(token);

        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }

    @Override
    public void reLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getName());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), userDetails.getAuthorities());

        authManger.authenticate(token);

        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
        }
    }

    @Override
    public ResultCheck checkPassword(String newPassword, String passwordConfirm) {
        // изменение пароля
        if (newPassword.isEmpty() || (passwordConfirm.isEmpty())) {
            return new ResultCheck(false, (newPassword.isEmpty() ? "passwordNewError" : "passwordConfirmError"), "Пароль не может быть пустым");
        }
        if (!newPassword.equals(passwordConfirm)) {
            return new ResultCheck(false, "passwordError", "Пароли не совпадают");
        }
        if ((newPassword.length() < 5)
                || (newPassword.length() > 256)) {
            return new ResultCheck(false, "passwordError", "Размер пароля должен находиться в диапазоне от 5 до 256");
        }
        return new ResultCheck(true);
    }
}
