package gdsc.netwalk.service.activity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gdsc.netwalk.common.vo.CustomMap;
import gdsc.netwalk.dto.activity.request.RegisterActivityRequest;
import gdsc.netwalk.dto.common.CustomResponse;
import gdsc.netwalk.mapper.activity.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {
    @Autowired
    ActivityMapper activityMapper;

    /*
    * 활동 내역 등록
    * */
    public CustomResponse registerActivity(RegisterActivityRequest request) {
        CustomResponse response = new CustomResponse();
        try {
            ObjectMapper mapper = new ObjectMapper();
            CustomMap param = mapper.convertValue(request, new TypeReference<CustomMap>() {});

            // [1] 활동 상세 내역 등록
            activityMapper.registerActivity(param);

            System.out.println(param);

            // [2] 활동 이동 경로 등록
            activityMapper.registerActivityDistance(param);

            response.setStatus("SUCCESS");
            response.setMessage("활동 내역 등록 성공");

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("활동 내역 등 록 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }
}
