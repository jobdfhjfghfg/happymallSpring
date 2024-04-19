package happyMall.happyMall.form;

import happyMall.happyMall.domain.Role;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberForm {

    private Long id;

    @NotEmpty(message = "회원 아이디는 필수입니다.")
    private String loginId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

    private String name;

    private String birthday;

    private String emailAddress;

    private String zipcode;

    private String fullAddr;

    private String extraAddr;

    private String userPhoneNumber;

    private Role grade;

}
