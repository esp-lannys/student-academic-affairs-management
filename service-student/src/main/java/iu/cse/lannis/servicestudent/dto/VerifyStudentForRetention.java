package iu.cse.lannis.servicestudent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class VerifyStudentForRetention {
    private Long id;
    private String studentName;
    private String email;
}
