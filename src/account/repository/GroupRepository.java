package account.repository;


import account.model.user.Group;
import org.springframework.data.jpa.repository.JpaRepository;



public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByCode(String code);
    Group findByCodeOrderByCodeAsc(String code);
}

