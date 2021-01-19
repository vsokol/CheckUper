package milovanov.stc31.innopolis.checkuper.security;

import milovanov.stc31.innopolis.checkuper.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
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

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    //Доступ разрешен всем пользователей
                    .antMatchers("/", "/resource/**", "/welcome").permitAll()
                    // доступ только для незарегистрированных пользователей
                    .antMatchers("/registration").not().fullyAuthenticated()
                    // все остальные страницы требуют аутентификации
                    .anyRequest().authenticated()
                    .and()
                // настройка для входа в систему
                .formLogin()
                    .loginPage("/login")
                    // логин и пароль с формы логина
                    .usernameParameter("j_username")
                    .passwordParameter("j_password")
                    // перенаправление на главную страницу после успешного входа
                    .defaultSuccessUrl("/user/stats")
                    .permitAll()
                    .and()
                .logout()
                    .permitAll()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .invalidateHttpSession(true);
    }

    @Autowired
    protected void configurePasswordEncoder(AuthenticationManagerBuilder auth, UserDetailsServiceImpl userDetailsService) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
