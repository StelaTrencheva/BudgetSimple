package budget.simple.budgetsimple_back_end.security_config;

import budget.simple.budgetsimple_back_end.filter.JWTAuthenticationFilter;
import budget.simple.budgetsimple_back_end.filter.JWTAuthorizationFilter;
import budget.simple.budgetsimple_back_end.logic.user.AuthenticationUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
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
                .antMatchers("/user/me").permitAll()
                .antMatchers("/wallet/**").hasAnyAuthority("ADMIN","USER")
                .antMatchers("/user/**").hasAnyAuthority("ADMIN","USER")
                .antMatchers("/websocket/**").hasAnyAuthority("ADMIN","USER")
                .antMatchers("/survey/create").hasAnyAuthority("ADMIN")
                .antMatchers("/survey//getByTitle/{title}").hasAnyAuthority("ADMIN","USER")
                .antMatchers("/survey/submission/getAll").hasAnyAuthority("ADMIN")
                .antMatchers("/survey/submission/create").hasAnyAuthority("USER")
                .antMatchers("/survey/submission/{id}").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilter(customFilter)
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .deleteCookies("auth")
                .logoutUrl("/logout")
                .permitAll()
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK));

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationUserDetailService).passwordEncoder(bCryptPasswordEncoder);
    }
}
