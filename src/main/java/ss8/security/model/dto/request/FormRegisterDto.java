package ss8.security.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormRegisterDto {
    private String name;
    private String username;
    private String password;
    private boolean status;
    private List<String> roles;
}
