package gdsc.netwalk.mapper.activity;

import gdsc.netwalk.common.vo.CustomList;
import gdsc.netwalk.common.vo.CustomMap;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActivityMapper {
    void registerActivity(CustomMap param);
    void registerActivityDistance(CustomMap param);
    CustomMap selectActivityByUser(CustomMap param);
    CustomList<CustomMap> selectActivityDistanceByUser(CustomMap param);
}
