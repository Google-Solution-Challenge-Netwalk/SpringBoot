package gdsc.netwalk.domain.user;

import gdsc.netwalk.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    private int user_no;
    private String name;
    private String email;
    private String img_url;
}
