package budget.simple.budgetsimple_back_end.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthenticationUserDetailsService implements UserDetailsService {
        private final UserManager um;

        @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = um.getUser(username);
            if (user == null) {
                throw new UsernameNotFoundException(username);
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(), getAuthorities(String.valueOf(user.getRole())));
        }

        private Collection<? extends GrantedAuthority> getAuthorities(String role) {
            return Arrays.asList(new SimpleGrantedAuthority(role));
        }
}
