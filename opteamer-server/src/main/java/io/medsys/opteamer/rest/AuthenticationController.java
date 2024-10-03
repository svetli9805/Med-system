package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.AuthenticationRequestDTO;
import io.medsys.opteamer.dto.AuthenticationResponseDTO;
import io.medsys.opteamer.dto.TeamMemberDTO;
import io.medsys.opteamer.dto.UserRegistrationDTO;
import io.medsys.opteamer.services.UserRegistrationService;
import io.medsys.opteamer.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final UserDetailsService userDetailsService;
    private final UserRegistrationService userRegistrationService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(@Qualifier("OpteamerUserDetailsService") UserDetailsService userDetailsService, UserRegistrationService userRegistrationService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.userRegistrationService = userRegistrationService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<TeamMemberDTO> createTeamMember(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        userRegistrationService.createUser(userRegistrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.ok(new AuthenticationResponseDTO("Incorrect username or password", false, ""));
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequestDTO.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponseDTO("Authentication was successful", true, jwt));
    }
}
