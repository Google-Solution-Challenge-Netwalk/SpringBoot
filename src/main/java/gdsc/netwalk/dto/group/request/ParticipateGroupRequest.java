package gdsc.netwalk.dto.group.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ParticipateGroupRequest {
    private int user_no;
    private int group_no;
}
