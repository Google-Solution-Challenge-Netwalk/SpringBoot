package gdsc.netwalk.dto.user.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest{
    private String name;
    private String email;

}
