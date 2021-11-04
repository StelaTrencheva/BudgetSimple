package budget.simple.budgetsimple_back_end.security_config;

import budget.simple.budgetsimple_back_end.filter.JWTAuthenticationFilter;
import budget.simple.budgetsimple_back_end.filter.JWTAuthorizationFilter;
import budget.simple.budgetsimple_back_end.logic.AuthenticationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationUserDetailsService authenticationUserDetailService;


    @Override protected void configure(HttpSecurity http) throws Exception {
        JWTAuthenticationFilter customFilter = new JWTAuthenticationFilter(authenticationManager());
        customFilter.setFilterProcessesUrl("/user/login");
                http
                        .cors()
                        .and()
                        .csrf().disable().authorizeRequests()
                        .antMatchers(HttpMethod.POST, AuthenticationConfigConstants.SIGN_UP_URL).permitAll()
                        .antMatchers("/user/login").permitAll()
                        .antMatchers("/user/createUser").permitAll()
                        .antMatchers("/user").hasAnyAuthority("ADMIN")
                        .antMatchers("/user/**").hasAnyAuthority("USER","ADMIN")
                        .antMatchers("/user/updateUser").hasAnyAuthority("USER","ADMIN")
                        .anyRequest().authenticated()
                        .and()
                        .addFilter(customFilter)
                        .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                        // this disables session creation on Spring Security
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationUserDetailService).passwordEncoder(bCryptPasswordEncoder);
    }
}
