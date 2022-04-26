package account.model;


import account.model.user.User;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;



@Getter
@Setter
@Entity
public class Payment{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDate period;

    @Column(nullable = false)
    private Long salary;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

}