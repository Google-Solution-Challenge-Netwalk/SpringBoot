package gdsc.netwalk.dto.user.response;

import gdsc.netwalk.dto.common.CustomResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse extends CustomResponse{
    private int user_no;
}
