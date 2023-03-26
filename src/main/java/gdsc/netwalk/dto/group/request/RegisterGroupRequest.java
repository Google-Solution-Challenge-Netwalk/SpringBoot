package gdsc.netwalk.dto.group.request;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterGroupRequest {
    private int create_user_no;
    private String name;
    private int capacity;
    private String category;
}
