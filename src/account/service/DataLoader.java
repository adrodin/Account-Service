package account.service;

import account.model.user.Group;
import account.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private GroupRepository groupRepository;

    @Autowired
    public DataLoader(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void run(ApplicationArguments args) {
        if(groupRepository.count() ==0 ) {

            groupRepository.save(Group.builder().code("ADMINISTRATOR").name("ROLE_ADMINISTRATOR").build());
            groupRepository.save(Group.builder().code("ACCOUNTANT").name("ROLE_ACCOUNTANT").build());
            groupRepository.save(Group.builder().code("USER").name("ROLE_USER").build());
        }

    }
}
