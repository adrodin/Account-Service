package account.controller;

import account.dto.user.RequestChangePasswordDto;
import account.dto.user.RequestCreateUserDto;
import account.model.user.User;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("signup")
    public ResponseEntity<?> create(@Valid @RequestBody RequestCreateUserDto user){
        return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(user));
    }


    @PostMapping("changepass")
    public ResponseEntity<?> changePass(@AuthenticationPrincipal User user, @RequestBody @Valid RequestChangePasswordDto RCPD){
        return ResponseEntity.status(HttpStatus.OK).body(userService.changePassword(RCPD, user));
    }

}
