package budget.simple.budgetsimple_back_end.security_config;

import budget.simple.budgetsimple_back_end.filter.JWTAuthenticationFilter;
import budget.simple.budgetsimple_back_end.filter.JWTAuthorizationFilter;
import budget.simple.budgetsimple_back_end.logic.user.AuthenticationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationUserDetailsService authenticationUserDetailService;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override protected void configure(HttpSecurity http) throws Exception {
        JWTAuthenticationFilter customFilter = new JWTAuthenticationFilter(authenticationManager());
        customFilter.setFilterProcessesUrl("/user/login");
                http.
                        cors()
                        .and()
                        .csrf().disable().authorizeRequests()
                        .antMatchers(HttpMethod.POST, AuthenticationConfigConstants.SIGN_UP_URL).permitAll()
                        .antMatchers("/user/login").permitAll()
                        .antMatchers("/wallet/**").permitAll()
                        .antMatchers("/survey/**").permitAll()
                        .antMatchers("/surveySubmission/**").permitAll()
                        .antMatchers("/user/createUser").permitAll()
                        //.antMatchers("/user").hasAnyAuthority("ADMIN")
                        .antMatchers("/user").permitAll()
                        .antMatchers("/user/updateUser").hasAnyAuthority("USER","ADMIN")
                        .antMatchers("/user/**").hasAnyAuthority("USER","ADMIN")
                        .anyRequest().authenticated()
                        .and()
                        .addFilter(customFilter)
                        .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .logout()
                        .deleteCookies("auth")
                        .logoutUrl("/user/logout");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationUserDetailService).passwordEncoder(bCryptPasswordEncoder);
    }
}
