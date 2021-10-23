package iu.cse.lannis.authserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSignUpDto {
    private Long id;
    private String username;
    private String studentName;
    private String email;
    private String password;
}
