package gdsc.netwalk.service.user;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gdsc.netwalk.common.vo.CustomMap;
import gdsc.netwalk.domain.user.User;
import gdsc.netwalk.dto.common.CustomResponse;
import gdsc.netwalk.dto.user.request.LoginRequest;
import gdsc.netwalk.dto.user.response.LoginResponse;
import gdsc.netwalk.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    /*
    * 로그인
    * */
    @Transactional
    public LoginResponse login(LoginRequest request) {
         LoginResponse response = new LoginResponse();

        try {
            // [1] 회원정보 조회
            User user = userMapper.selectUserByEmail(request.getEmail());
            // [1-1] 회원 정보가 없을 경우 회원등록후 결과 반환
            if(user == null) {
                /*
                * 회원정보가 없을 경우 회원을 새로 등록 해야 한다는 논리 때문에
                * 로그인 불일치 로직을 짜는 데 문제가 있음.
                * 로그인 실패 가능성이 없으면 문제가 없긴함.
                * */
                // [2] DB param 세팅
                ObjectMapper mapper = new ObjectMapper();
                CustomMap param = mapper.convertValue(request, new TypeReference<CustomMap>() {});

                // [3] 회원 정보 등록
                userMapper.registerUser(param);
                response.setUser_no(param.getInt("user_no"));
            }
            // [1-2] 회원 정보가 있을 경우 결과 반환
            else {
                response.setUser_no(user.getUser_no());
            }
            response.setStatus("SUCCESS");
            response.setMessage("로그인 성공");

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("로그인 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }

    /*
    * 회원 프로필 조회
    * */
    @Transactional
    public CustomResponse selectUserProfile(int user_no) {
        LoginResponse response = new LoginResponse();

        try {
            // [1] 회원정보 조회
            CustomMap result = userMapper.selectUserProfile(user_no);
            response.setObject(result);
            response.setStatus("SUCCESS");
            response.setMessage("회원 프로필 조회 성공");

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("회원 프로필 조회 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }
}
