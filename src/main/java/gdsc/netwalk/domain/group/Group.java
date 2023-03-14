package gdsc.netwalk.domain.group;

import gdsc.netwalk.common.domain.BaseEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Group extends BaseEntity {
    private int create_user_no;
    private String name;
    private int capacity;
    private int Participant;
    private String category;
}
