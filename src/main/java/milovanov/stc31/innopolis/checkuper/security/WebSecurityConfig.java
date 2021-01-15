package milovanov.stc31.innopolis.checkuper.security;

import milovanov.stc31.innopolis.checkuper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    //Доступ разрешен всем пользователей
                    .antMatchers("/", "/resource/**", "/login**", "/registration", "/j_security_check").permitAll()
                    // доступ только для незарегистрированных пользователей
//                    .antMatchers("/registration").not().fullyAuthenticated()
                    // все остальные страницы требуют аутентификации
                    .anyRequest().authenticated();
//                    .and()
//                // настройка для входа в систему
//                .formLogin()
//                    .loginPage("/login")
//                    // перенаправление на главную страницу после успешного входа
//                    .defaultSuccessUrl("/")
//                    .permitAll()
//                    .and()
//                .logout()
//                    .permitAll()
//                    .logoutSuccessUrl("/");
    }

    @Autowired
    protected void configurePasswordEncoder(AuthenticationManagerBuilder auth
            , UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }
}
