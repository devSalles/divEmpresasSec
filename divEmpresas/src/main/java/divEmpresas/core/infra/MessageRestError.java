package divEmpresas.core.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MessageRestError {

    private HttpStatus status;
    private String message;
    private LocalDateTime timeStamp;

    private Map<String,String>fieldErrors = new HashMap<>();

    public MessageRestError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }

    public MessageRestError(HttpStatus status, String message, Map<String, String> fieldErrors) {
        this.status = status;
        this.message = message;
        this.timeStamp = LocalDateTime.now();
        this.fieldErrors = fieldErrors;
    }
}
