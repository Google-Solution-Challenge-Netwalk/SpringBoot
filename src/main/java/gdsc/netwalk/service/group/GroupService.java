package gdsc.netwalk.service.group;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gdsc.netwalk.common.vo.CustomList;
import gdsc.netwalk.common.vo.CustomMap;
import gdsc.netwalk.dto.common.CustomResponse;
import gdsc.netwalk.dto.group.request.CategoryGroupListRequest;
import gdsc.netwalk.dto.group.request.ParticipateGroupRequest;
import gdsc.netwalk.dto.group.request.RegisterGroupRequest;
import gdsc.netwalk.mapper.group.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class GroupService {
    @Autowired
    GroupMapper groupMapper;

    /*
    * 그룹 등록
    * */
    @Transactional
    public CustomResponse registerGroup(RegisterGroupRequest request) {
        CustomResponse response = new CustomResponse();

        try {
            // [1] 그룹 등록
            // [1-1] param 생성(POJO object to Map)
            ObjectMapper mapper = new ObjectMapper();
            CustomMap param = mapper.convertValue(request, new TypeReference<CustomMap>() {});
            // [1-2] 그룹 정보 등록
            groupMapper.registerGroup(param);

            // [2] 그룹 생성자 그룹 참여 정보 등록
            param.set("user_no", param.getInt("create_user_no"));
            groupMapper.participateGroup(param);

            response.setStatus("SUCCESS");
            response.setMessage("그룹 등록 성공");
            
        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("그룹 등록 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }

    /*
    * 생성 그룹 조회
    * */
    public CustomResponse selectGroupListByCreateUserNo(HttpServletRequest request, int createUserNo) {
        CustomResponse response = new CustomResponse();

        try {
            // 로그인한 유저와 요청한 유저의 일치 여부 검사
            if((int) request.getSession().getAttribute("loginUser")  == createUserNo) {
                // [1] 생성 그룹 조회
                CustomList<CustomMap> list = groupMapper.selectGroupListByCreateUserNo(createUserNo);

                response.setObject(list);
                response.setStatus("SUCCESS");
                response.setMessage("생성 그룹 조회");
            }
            else {
                response.setStatus("FAIL");
                response.setMessage("접근 권한 없음");
            }

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("생성 그룹 조회 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }

    /*
     * 참여 그룹 조회
     * */
    public CustomResponse selectParticipateGroupList(int userNo) {
        CustomResponse response = new CustomResponse();

        try {
            // [1] 참여 그룹 조회
            CustomList<CustomMap> list = groupMapper.selectParticipateGroupList(userNo);
            response.setObject(list);
            response.setStatus("SUCCESS");
            response.setMessage("참여 그룹 조회");

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("참여 그룹 조회 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }

    /*
     * 그룹 참여 정보 등록
     * */
    @Transactional
    public CustomResponse registerParticipateGroup(ParticipateGroupRequest request) {
        CustomResponse response = new CustomResponse();

        try {
            // [1] 그룹 참여 정보 등록
            // [1-1] 그룹 참여 내역이 있는지 조회
            ObjectMapper mapper = new ObjectMapper();
            CustomMap param = mapper.convertValue(request, new TypeReference<CustomMap>() {});
            if(groupMapper.isExistParticipatedByUserNo(param) < 1) {

                groupMapper.participateGroup(param);

                response.setStatus("SUCCESS");
                response.setMessage("그룹 참여 성공");
            }
            else {
                response.setStatus("FAIL");
                response.setMessage("이미 참여된 그룹입니다.");
            }

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("그룹 참여 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }

    /*
     * 카테고리별 그룹 조회
     * */
    public CustomResponse selectCategoryGroup(CategoryGroupListRequest request) {
        CustomResponse response = new CustomResponse();

        try {
            // [1] 카테고리별 그룹 조회
            ObjectMapper mapper = new ObjectMapper();
            CustomMap param = mapper.convertValue(request, new TypeReference<CustomMap>() {});
            if("".equals(param.getString("category")) || param.getString("category") == null) {
                param.set("category", "");
            }
            System.out.println(param);

            CustomList<CustomMap> list =  groupMapper.selectCategoryGroup(param);
            response.setObject(list);
            response.setStatus("SUCCESS");
            response.setMessage("카테고리별 그룹 조회 성공");
            
        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("카테고리별 그룹 조회 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }

    /*
     * 그룹내 유저 목록 조회
     * */
    public CustomResponse selectGroupInUsers(int groupNo) {
        CustomResponse response = new CustomResponse();

        try {
            CustomList<CustomMap> list = groupMapper.selectGroupInUsers(groupNo);
            response.setObject(list);
            response.setStatus("SUCCESS");
            response.setMessage("그룹내 유저 목록 조회 성공");

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("그룹내 유저 목록 조회 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }
    
}
