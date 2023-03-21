package gdsc.netwalk.dto.activity.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterActivityRequest {
    private int user_no;
    private int group_no;
}