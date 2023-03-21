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
    private double total_act_distance;
    private double total_act_time;
    private int share_st;
    private CustomList<CustomMap> distances;
}
