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

    private int act_nol;
    private double total_act_distance;
    private double total_act_time;
    private CustomList<CustomMap> distances;
}
