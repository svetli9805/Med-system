package io.medsys.opteamer.services;

import io.medsys.opteamer.model.OpteamerUserDetails;
import io.medsys.opteamer.model.User;
import io.medsys.opteamer.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("OpteamerUserDetailsService")
@RequiredArgsConstructor
public class OpteamerUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return OpteamerUserDetails.build(user);
    }
}
