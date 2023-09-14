package ss8.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import ss8.security.model.dto.request.FormLoginDto;
import ss8.security.model.dto.request.FormRegisterDto;
import ss8.security.model.dto.response.JwtResponse;
import ss8.security.model.entity.Users;
import ss8.security.security.jwt.JwtProvider;
import ss8.security.security.principal.UserPrincipal;
import ss8.security.service.IUserService;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v4/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private IUserService userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("login success");
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody FormLoginDto loginDto) throws LoginException{
        Authentication authentication = null;
            try {
                // tạo đối tượng authentication thông qua username vs password
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getUsername(),
                                loginDto.getPassword()
                        ));
            } catch (AuthenticationException e){
                    throw new LoginException("Username or password is incorrect!");
            }
            // tạo token và trả về cho người dùng
            String token = jwtProvider.generateToken(authentication);
            // lấy ra user principal
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return new ResponseEntity<>(JwtResponse.builder()
                .token(token)
                .name(userPrincipal.getName())
                .username(userPrincipal.getUsername())
                .roles(roles)
                .type("Bearer")
                .status(userPrincipal.isStatus())
                .build(),HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody FormRegisterDto registerDto){
         Users user = userService.save(registerDto);
         return new ResponseEntity<>("register successful",HttpStatus.CREATED);
    }
}
