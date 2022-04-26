package account.service;

import account.dto.role.Operation;
import account.dto.role.RequestPutUserRoleDto;
import account.dto.user.RequestChangePasswordDto;
import account.dto.user.RequestCreateUserDto;
import account.dto.user.ResponseChangePasswordDto;
import account.dto.user.ResponseGetUserDto;
import account.exception.*;
import account.mapper.UserMapper;
import account.model.user.Group;
import account.model.user.User;
import account.repository.GroupRepository;
import account.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(s);
        if (user.isPresent()){
            return user.get();
        }else{
            throw new UsernameNotFoundException("Username[%s] not found");
        }
    }

    private Collection<GrantedAuthority> getAuthorities(User user){
        Set<Group> userGroups = user.getUserGroups();
        Collection<GrantedAuthority> authorities = new ArrayList<>(userGroups.size());
        for(Group userGroup : userGroups){
            authorities.add(new SimpleGrantedAuthority(userGroup.getCode().toUpperCase()));
        }

        return authorities;
    }

    public ResponseGetUserDto addUser(RequestCreateUserDto user){
        if(userRepository.existsByUsernameIgnoreCase(user.getEmail())){
            throw new UserAlreadyExistsException();
        }
        user.setEmail(user.getEmail().toLowerCase(Locale.ROOT));
        User newUser = UserMapper.requestCreateUserDtoUser(user,passwordEncoder.encode(user.getPassword()));
        updateUserGroup(newUser);
        userRepository.save(newUser);

        return UserMapper.userToGetUserDto(newUser);
    }

    public ResponseChangePasswordDto changePassword(RequestChangePasswordDto RCPD,User user){
        String password = RCPD.getPassword();

        if(passwordEncoder.matches(password,user.getPassword())){
            throw new SamePasswordException();
        }

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return ResponseChangePasswordDto.builder().email(user.getUsername()).build();

    }

    private void updateUserGroup(User user){
        if(userRepository.count() == 0){
            Group group = groupRepository.findByCode("ADMINISTRATOR");
            user.addUserGroup(group);
        }else {
            Group group = groupRepository.findByCode("USER");
            user.addUserGroup(group);
        }

    }


    public List<ResponseGetUserDto> getAllUsers(){
        return userRepository.findAllByOrderByIdAsc().stream()
                .map(UserMapper::userToGetUserDto)
                .collect(Collectors.toList());
    }

    public Map<String, String> deleteUser(String username){
        username = username.toLowerCase(Locale.ROOT);

        User user = userRepository.findByUsername(username).orElseThrow(UserNoExistsException::new);
        if(user.getUserGroups().stream().anyMatch(group -> Objects.equals(group.getCode(), "ADMINISTRATOR"))){
            throw new RemoveAdministratorRoleException();
        }

        userRepository.delete(user);
        return Map.of(
                "user", username,
                "status", "Deleted successfully!"
        );
    }


    public ResponseGetUserDto modifyUserRole(RequestPutUserRoleDto requestBody){
        User user = userRepository.findByUsernameIgnoreCase(requestBody.getUser()).orElseThrow(UserNoExistsException::new);
        if(requestBody.getOperation().equals(Operation.REMOVE)){
            removeRole(user, requestBody.getRole());
        }else{
            addRole(user, requestBody.getRole());
        }

        return UserMapper.userToGetUserDto(user);
    }

    private void removeRole(User user,String role){
        Group group = groupRepository.findByCode(role);
        if(group == null) {
            throw new RoleNoExistsException();
        }
        if(group.getCode().equals("ADMINISTRATOR")){
            throw new RemoveAdministratorRoleException();
        }
        if(user.getUserGroups().stream().noneMatch(g -> Objects.equals(g.getCode(), role))){
            throw new NoRoleException();
        }

        if(user.getUserGroups().size() < 2){
            throw new NotEnoughRolesException();
        }
        user.removeUserGroup(group);
        userRepository.save(user);
    }
    private void addRole(User user,String role){
        if(user.getUserGroups().stream().anyMatch(group -> Objects.equals(group.getCode(), "ADMINISTRATOR"))){
            throw new BadRoleException();
        }

        Group group = groupRepository.findByCode(role);
        if(group == null) {
            throw new RoleNoExistsException();
        }
        if(group.getCode().equals("ADMINISTRATOR")){
            throw new BadRoleException();
        }

        user.addUserGroup(group);
        userRepository.save(user);
    }
}