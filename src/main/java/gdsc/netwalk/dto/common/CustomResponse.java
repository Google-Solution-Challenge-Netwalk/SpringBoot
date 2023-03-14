package gdsc.netwalk.dto.common;

import gdsc.netwalk.common.vo.CustomList;
import gdsc.netwalk.common.vo.CustomMap;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse {
    // HttpStatus
    private String status;
    // Http Default Message
    private String message;

    private Object object;
}