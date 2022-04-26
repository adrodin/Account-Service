package account.controller;


import account.dto.role.RequestPutUserRoleDto;
import account.model.user.User;
import account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("user")
    public ResponseEntity<?> get(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @DeleteMapping("user/{username}")
    public ResponseEntity<?> get(@AuthenticationPrincipal User user,@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(username));
    }

    @PutMapping("user/role")
    public ResponseEntity<?> modify(@RequestBody @Valid RequestPutUserRoleDto requestPutUserRoleDto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.modifyUserRole(requestPutUserRoleDto));
    }
}
