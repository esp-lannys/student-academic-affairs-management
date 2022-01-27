package iu.cse.lannis.serviceretention.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
@Builder(toBuilder = true)
public class RetentionResponse {
    private final HttpStatus status;
    private final String message;
}
