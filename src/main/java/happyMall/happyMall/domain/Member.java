package happyMall.happyMall.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String loginId;

    private String password;

    private String name;

    private String birthday;

    private String emailAddress;

    private String userPhoneNumber;

    @Enumerated(EnumType.STRING)
    private Role grade;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "member")
    private Cart cart;

    @OneToMany(mappedBy = "member")
    private List<ProductOrder> productOrder = new ArrayList<>();

}
