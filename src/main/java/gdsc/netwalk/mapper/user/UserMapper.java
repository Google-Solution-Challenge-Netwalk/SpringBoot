package gdsc.netwalk.mapper.user;

import gdsc.netwalk.common.vo.CustomMap;
import gdsc.netwalk.domain.user.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    void registerUser(CustomMap param);
    User selectUserByEmail(String email);
}
