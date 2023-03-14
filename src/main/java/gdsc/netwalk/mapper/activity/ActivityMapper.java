package gdsc.netwalk.mapper.activity;

import gdsc.netwalk.common.vo.CustomMap;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActivityMapper {
    void registerActivity(CustomMap param);
    void registerActivityDistance(CustomMap param);
}
