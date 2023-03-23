package gdsc.netwalk.dto.group.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryGroupListRequest {
    private String category;
    private String act_st;
}
