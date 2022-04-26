package account.mapper;


import account.dto.user.RequestCreateUserDto;
import account.dto.user.ResponseGetUserDto;
import account.model.user.Group;
import account.model.user.User;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public final class UserMapper {



    public static ResponseGetUserDto userToGetUserDto(User user){

        return ResponseGetUserDto.builder()
                .id(user.getId())
                .email(user.getUsername())
                .lastname(user.getLastName())
                .name(user.getName())
                .roles(user.getUserGroups().stream()
                        .sorted(Comparator.comparing(Group::getCode))
                        .map(Group::getName)
                        .collect(Collectors.toList()))
                .build();
    }

    public static User requestCreateUserDtoUser(RequestCreateUserDto RCUser,String encodedPassword){
        User user = new User();
        user.setLastName(RCUser.getLastname());
        user.setName(RCUser.getName());
        user.setPassword(encodedPassword);
        user.setUsername(RCUser.getEmail());
        return user;
    }
}
