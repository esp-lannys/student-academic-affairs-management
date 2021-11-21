package iu.cse.lannis.serviceretention.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRetentionDto {
    private Long studentId;
    private String note;
    private String studentName;
    private String reason;
}
