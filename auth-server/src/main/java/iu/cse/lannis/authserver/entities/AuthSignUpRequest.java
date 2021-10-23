package iu.cse.lannis.authserver.entities;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthSignUpRequest {
    private String username;
    private String email;
    private String studentName;
    private String password;
    private String role;
}
