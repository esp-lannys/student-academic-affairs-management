package iu.cse.lannis.serviceretention.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class StudentDto {
    private Long id;
    private String studentName;
    private String username;
    private String email;
}
