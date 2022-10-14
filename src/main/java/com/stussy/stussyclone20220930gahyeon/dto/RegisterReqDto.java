package com.stussy.stussyclone20220930gahyeon.dto;


import com.stussy.stussyclone20220930gahyeon.domain.User;
import com.stussy.stussyclone20220930gahyeon.dto.validation.ValidationGroups;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterReqDto {
    //한글자 이상 무조건 입력, 세글자 이상 안됨, 무조건 한글
    @Size(min = 1, max = 3, message = "이름은 3글자 까지만 입력가능합니다", groups = ValidationGroups.SizeGroup.class) //최소 1글자 최대 3글자
    @NotBlank(message = "이름은 비워둘 수 없습니다", groups = ValidationGroups.NotBlankGroup.class) //Null, 빈 문자열, 스페이스만 있는 문자열 불가
    @Pattern(regexp = "^[가-힇]*$", message = "한글만 입력 가능합니다", groups = ValidationGroups.PatternCheckGroup.class)
    private String lastName;

    @Size(min = 1, max = 2, message = "성은 2글자 까지만 입력가능합니다", groups = ValidationGroups.SizeGroup.class)
    @NotBlank(message = "성은 비워둘 수 없습니다", groups = ValidationGroups.NotBlankGroup.class)
    @Pattern(regexp = "^[가-힇]*$", message = "한글만 입력 가능합니다", groups = ValidationGroups.PatternCheckGroup.class)
    private String firstName;

    @NotBlank(message = "이메일은 비워 둘 수 없습니다", groups = ValidationGroups.NotBlankGroup.class)
    @Email
    private String email;

    @Size(min = 8, max = 16, message = "비밀번호는 8~16자까지 입력하여야 합니다", groups = ValidationGroups.SizeGroup.class)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*_])[a-zA-Z\\d-~!@#$%^&*_]*$",
            message = "비밀번호는 특수기호, 영문, 숫자를 모두 포함해야 합니다", groups = ValidationGroups.PatternCheckGroup.class)
    @NotBlank(message = "비밀번호는 비워 둘 수 없습니다", groups = ValidationGroups.NotBlankGroup.class)
    private String password;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(new BCryptPasswordEncoder().encode(password)) //패스워드 암호화
                .name(firstName + lastName)
                .role_id(1)
                .build();
    }
}
