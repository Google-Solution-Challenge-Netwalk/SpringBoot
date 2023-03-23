package gdsc.netwalk.service.activity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gdsc.netwalk.common.vo.CustomList;
import gdsc.netwalk.common.vo.CustomMap;
import gdsc.netwalk.dto.activity.request.ActivityListRequest;
import gdsc.netwalk.dto.activity.request.RegisterActivityRequest;
import gdsc.netwalk.dto.activity.request.UpdateActivityRequest;
import gdsc.netwalk.dto.common.CustomResponse;
import gdsc.netwalk.dto.activity.request.UpdateAcitivtyShareSTRequest;
import gdsc.netwalk.dto.user.response.LoginResponse;
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
            CustomMap param = new CustomMap();
            // [1] 활동 상세 내역 등록
            param.set("user_no", request.getUser_no());
            param.set("act_st", request.getAct_st());

            CustomList<Integer> act_no_list = new CustomList<Integer>();

            if(request.getGroups() != null) {
                for(int group_no : request.getGroups()) {
                    param.set("group_no", group_no);
                    activityMapper.registerActivity(param);
                    act_no_list.add(param.getInt("act_no"));
                }
            }
            else {
                activityMapper.registerActivity(param);
                act_no_list.add(param.getInt("act_no"));
            }

            response.setObject(act_no_list);
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
     * 활동 내역 수정
     * */
    @Transactional
    public CustomResponse updateActivity(UpdateActivityRequest request) {
        CustomResponse response = new CustomResponse();
        try {
            ObjectMapper mapper = new ObjectMapper();
            CustomMap param = mapper.convertValue(request, new TypeReference<CustomMap>() {});

            // [1] 활동 상세 내역 등록
            activityMapper.updateActivity(param);

            // [2] 활동 이동 경로 등록
            for(int act_no : request.getActivities()) {
                param.set("act_no", act_no);
                activityMapper.registerActivityDistance(param);
            }

            response.setStatus("SUCCESS");
            response.setMessage("활동 내역 수정 성공");

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("활동 내역 수정 실패");

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

    /*
     * 모든 사용자의 활동 기록 조회
     * */
    public CustomResponse selectAllActivity() {
        CustomResponse response = new CustomResponse();
        try {
            // [1] 모든 사용자의 활동 기록 조회
            CustomList<CustomMap> activities = activityMapper.selectAllActivity();

            for(CustomMap activity : activities) {
                // [2] 활동 거리 내역 조회
                CustomList<CustomMap> distances = activityMapper.selectActivityDistanceByUser(activity.getInt("act_no"));
                activity.set("distances", distances);
            }

            response.setObject(activities);
            response.setStatus("SUCCESS");
            response.setMessage("모든 사용자의 활동 기록 조회 성공");

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("모든 사용자의 활동 기록 조회 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }

    /*
     * 회원 플로깅 활동 정보 공유 상태 업데이트
     * */
    @Transactional
    public CustomResponse updateAcitivtyShareST(UpdateAcitivtyShareSTRequest request) {
        LoginResponse response = new LoginResponse();

        try {
            // [1] 회원정보 조회
            ObjectMapper mapper = new ObjectMapper();
            CustomMap param = mapper.convertValue(request, new TypeReference<CustomMap>() {});
            activityMapper.updateAcitivtyShareST(param);

            response.setStatus("SUCCESS");
            response.setMessage("회원 플로깅 활동 정보 공유 상태 업데이트 성공");

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("회원 플로깅 활동 정보 공유 상태 업데이트 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }

    /*
     * 플로깅 중 수집 쓰레기 목록 조회
     * */
    @Transactional
    public CustomResponse selectTrashByActivity(int act_no) {
        LoginResponse response = new LoginResponse();

        try {
            // [1] 플로깅 중 수집 쓰레기 목록 조회
            CustomList<CustomMap> trashes = activityMapper.selectTrashByActivity(act_no);

            response.setObject(trashes);
            response.setStatus("SUCCESS");
            response.setMessage("플로깅 중 수집 쓰레기 목록 조회 성공");

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("플로깅 중 수집 쓰레기 목록 조회 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }
}
