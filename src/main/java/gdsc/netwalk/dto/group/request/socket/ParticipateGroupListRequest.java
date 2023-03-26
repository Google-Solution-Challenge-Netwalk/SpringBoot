package gdsc.netwalk.dto.group.request.socket;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ParticipateGroupListRequest {
    private int user_no;
    private int group_no;
}
