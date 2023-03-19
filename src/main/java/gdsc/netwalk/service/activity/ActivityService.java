package gdsc.netwalk.service.activity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gdsc.netwalk.common.vo.CustomList;
import gdsc.netwalk.common.vo.CustomMap;
import gdsc.netwalk.dto.activity.request.ActivityListRequest;
import gdsc.netwalk.dto.activity.request.RegisterActivityRequest;
import gdsc.netwalk.dto.common.CustomResponse;
import gdsc.netwalk.mapper.activity.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivityService {
    @Autowired
    ActivityMapper activityMapper;

    /*
    * 활동 내역 등록
    * */
    @Transactional
    public CustomResponse registerActivity(RegisterActivityRequest request) {
        CustomResponse response = new CustomResponse();
        try {
            ObjectMapper mapper = new ObjectMapper();
            CustomMap param = mapper.convertValue(request, new TypeReference<CustomMap>() {});

            // 혼자하는 플로깅 활동 기록을 등록할 때
            if(param.getInt("group_no") == 0) {
                param.set("group_no", null);
            }
            // [1] 활동 상세 내역 등록
            activityMapper.registerActivity(param);

            System.out.println(param);
            // [2] 활동 이동 경로 등록
            activityMapper.registerActivityDistance(param);

            response.setStatus("SUCCESS");
            response.setMessage("활동 내역 등록 성공");

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("활동 내역 등록 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }

    /*
     * 활동 내역 조회
     * */
    public CustomResponse selectActivityByUser(ActivityListRequest request) {
        CustomResponse response = new CustomResponse();
        try {
            ObjectMapper mapper = new ObjectMapper();
            CustomMap param = mapper.convertValue(request, new TypeReference<CustomMap>() {});

            // [1] 활동 상세 내역 조회
            CustomList<CustomMap> activities = activityMapper.selectActivityByUser(param);

            for(CustomMap activity : activities) {
                // [2] 활동 거리 내역 조회
                CustomList<CustomMap> distances = activityMapper.selectActivityDistanceByUser(activity.getInt("act_no"));
                activity.set("distances", distances);
            }

            response.setObject(activities);
            response.setStatus("SUCCESS");
            response.setMessage("활동 내역 조회 성공");

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("활동 내역 조회 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }

    /*
     * 플로깅 랭킹 정보 조회
     * */
    public CustomResponse selectRankingActivity(String type) {
        CustomResponse response = new CustomResponse();
        try {
            // [1] 플로깅 랭킹 정보 조회
            CustomList<CustomMap> activities = activityMapper.selectRankingActivity(type);

            response.setObject(activities);
            response.setStatus("SUCCESS");
            response.setMessage("플로깅 랭킹 정보 조회 성공");

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("플로깅 랭킹 정보 조회 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }
}
