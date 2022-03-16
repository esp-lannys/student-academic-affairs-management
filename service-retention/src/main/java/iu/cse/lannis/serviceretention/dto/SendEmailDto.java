package iu.cse.lannis.serviceretention.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder(toBuilder = true)
public class SendEmailDto {
    private Long id;
    private String studentName;
    private String email;
}
