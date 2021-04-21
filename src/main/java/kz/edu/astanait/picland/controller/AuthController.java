package kz.edu.astanait.picland.controller;

import kz.edu.astanait.picland.model.Role;
import kz.edu.astanait.picland.model.User;
import kz.edu.astanait.picland.model.UserDetailsImpl;
import kz.edu.astanait.picland.payload.request.AuthRequest;
import kz.edu.astanait.picland.payload.request.RefreshTokenRequest;
import kz.edu.astanait.picland.payload.request.SignUpRequest;
import kz.edu.astanait.picland.payload.response.JwtResponse;
import kz.edu.astanait.picland.repository.RoleRepository;
import kz.edu.astanait.picland.repository.UserRepository;
import kz.edu.astanait.picland.security.jwt.JwtUtils;
import kz.edu.astanait.picland.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final RefreshTokenService refreshTokenService;

    private final JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@Valid @RequestBody AuthRequest request){
        Authentication authentication  = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//      List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(
                jwt, userDetails.getUsername(), refreshTokenService.generateRefreshToken().getToken()
        ));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request){
        boolean isValid = refreshTokenService.validateRefreshToken(request.getRefreshToken());
        if(isValid){
            String token = jwtUtils.generateTokenWithUsername(request.getUsername());
            return ResponseEntity.ok(new JwtResponse(token, request.getUsername(), request.getRefreshToken()));
        }else {
            return ResponseEntity.status(400).body("Invalid refresh token");
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("This username is already taken!");
        }

        User user = new User(signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                            .orElseThrow(() -> new RuntimeException("Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName("ROLE_USER")
                            .orElseThrow(() -> new RuntimeException("Role is not found."));
                    roles.add(userRole);
                }
            });
        }
        user.setEmail(signUpRequest.getEmail());
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("User successfully registered");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest request){
        refreshTokenService.deleteRefreshToken(request.getRefreshToken());
        return ResponseEntity.ok("Refresh token successfully deleted");
    }
}