package ss8.security.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ss8.security.model.entity.Role;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class JwtResponse {
    private String token;
    private String type;
    private String name;
    private String username;
    private boolean status;
    private List<String> roles = new ArrayList<>();
}
