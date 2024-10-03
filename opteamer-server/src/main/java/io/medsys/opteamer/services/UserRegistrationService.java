package io.medsys.opteamer.services;

import io.medsys.opteamer.dto.UserRegistrationDTO;
import io.medsys.opteamer.model.Role;
import io.medsys.opteamer.model.User;
import io.medsys.opteamer.model.enums.ERole;
import io.medsys.opteamer.repositories.RoleRepository;
import io.medsys.opteamer.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserRegistrationDTO registrationDTO) {
        Role role = roleRepository.findByName(ERole.ROLE_USER).get();

        User user = new User(registrationDTO.getUsername(), registrationDTO.getEmail(),
                passwordEncoder.encode(registrationDTO.getPassword()), new HashSet<>(List.of(role)));

        userRepository.save(user);
    }
}
