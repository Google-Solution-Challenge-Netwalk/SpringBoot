package gdsc.netwalk.dto.group.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GroupListByCreateUserRequest {
    private int create_user_no;
    private int group_no;
}
