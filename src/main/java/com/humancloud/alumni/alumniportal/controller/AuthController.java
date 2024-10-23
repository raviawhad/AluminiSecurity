package com.humancloud.alumni.alumniportal.controller;


import com.humancloud.alumni.alumniportal.Exception.EmailAlreadyPresentException;
import com.humancloud.alumni.alumniportal.Exception.UserException;
import com.humancloud.alumni.alumniportal.Model.User;
import com.humancloud.alumni.alumniportal.Repository.UserRepository;
import com.humancloud.alumni.alumniportal.Security.JwtProvider;
import com.humancloud.alumni.alumniportal.Service.CustomUserServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {

      @Autowired
     private UserRepository userRepository;
      @Autowired
      private PasswordEncoder passwordEncoder;
      @Autowired
      private CustomUserServiceIMPL customUserDetails;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new UserException("Email and Password cannot be null");
        }

        String email = user.getUsername();
        String password = user.getPassword();
        String fullName = user.getFullName();
        String role = user.getAuthorities().toString();
        String mobile = user.getMobile();

        User isEmailExist = userRepository.findByEmail(email);

        if (isEmailExist != null) {
            throw new EmailAlreadyPresentException("Email is already used with another account");
        }

        User createUser = new User();
        createUser.setEmail(email);
        createUser.setPassword(passwordEncoder.encode(password));
        createUser.setMobile(mobile);
        createUser.setRole(role);
        createUser.setFullName(fullName);

        User savedUser = userRepository.save(createUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);

        AuthResponse authResponce = new AuthResponse();
        authResponce.setJwt(token);
        authResponce.setMassage("Registered successfully");
        authResponce.setStatus(true);

        return new ResponseEntity<>(authResponce, HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) {
        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            throw new BadCredentialsException("Email and Password cannot be null");
        }

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();






        Authentication authentication = authenticateAction(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = JwtProvider.generateToken(authentication);

        AuthResponse authResponce = new AuthResponse();
        authResponce.setMassage("Login success");
        authResponce.setJwt(token);
        authResponce.setStatus(true);

        return new ResponseEntity<>(authResponce, HttpStatus.OK);
    }


    private Authentication authenticateAction(String username, String password) {
        UserDetails userDetails = this.customUserDetails.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}



