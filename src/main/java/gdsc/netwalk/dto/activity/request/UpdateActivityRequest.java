package gdsc.netwalk.dto.activity.request;

import gdsc.netwalk.common.vo.CustomList;
import gdsc.netwalk.common.vo.CustomMap;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class UpdateActivityRequest {

    private int act_no;
    private int user_no;
    private CustomList<Integer> groups;
    private double total_act_distance;
    private double total_act_time;
    private int share_st;
    private String act_st;
    private CustomList<CustomMap> distances;
}