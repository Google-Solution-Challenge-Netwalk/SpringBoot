package gdsc.netwalk.mapper.activity;

import gdsc.netwalk.common.vo.CustomList;
import gdsc.netwalk.common.vo.CustomMap;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActivityMapper {
    void registerActivity(CustomMap param);
    void registerGroupActivity(CustomMap param);
    int isParticipateInGroup(CustomMap param);
    void registerActivityDistance(CustomMap param);
    CustomList<CustomMap> selectActivityByUser(CustomMap param);
    CustomList<CustomMap> selectActivityDistanceByUser(int act_no);
    CustomList<CustomMap> selectRankingActivity(String type);
    void updateActivity(CustomMap param);
    CustomList<CustomMap> selectAllActivity();
    void updateAcitivtyShareST(CustomMap param);
    CustomList<CustomMap> selectTrashByActivity(int act_no);
    void registerActivityTrashImg(CustomMap param);
}
