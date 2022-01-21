package iu.cse.lannis.serviceretention.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetentionRequestDto {
    private Long studentId;
    private String note;
    private String studentName;
    private String reason;
    private String username;
    private String email;
}
