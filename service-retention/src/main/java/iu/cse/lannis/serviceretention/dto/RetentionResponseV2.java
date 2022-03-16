package iu.cse.lannis.serviceretention.dto;

import iu.cse.lannis.serviceretention.entity.Retention;
import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class RetentionResponseV2 {
    private HttpStatus status;
    private String message;
    private Retention retention;
}
