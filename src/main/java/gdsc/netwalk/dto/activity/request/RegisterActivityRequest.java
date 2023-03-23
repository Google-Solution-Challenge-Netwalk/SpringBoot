package gdsc.netwalk.dto.activity.request;

import gdsc.netwalk.common.vo.CustomList;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterActivityRequest {
    private int user_no;
    private CustomList<Integer> groups;
    private String act_st;
}