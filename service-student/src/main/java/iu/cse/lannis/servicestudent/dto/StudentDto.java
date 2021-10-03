package iu.cse.lannis.servicestudent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentDto {

    private String studentName;
    private String username;
    private String password;
    private String email;

}
