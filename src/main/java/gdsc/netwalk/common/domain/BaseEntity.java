package gdsc.netwalk.common.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public abstract class BaseEntity {

    @CreatedDate
    private LocalDateTime reg_dt;
    @LastModifiedDate
    private LocalDateTime mod_dt;
}
