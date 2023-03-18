package gdsc.netwalk.controller.user;

import gdsc.netwalk.dto.user.request.LoginRequest;
import gdsc.netwalk.dto.user.response.LoginResponse;
import gdsc.netwalk.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;
    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(HttpServletRequest request, @RequestBody LoginRequest loginRequest) {
        LoginResponse response = userService.login(loginRequest);
        // 로그인 성공시 세션 생성
        if(response.getStatus().equals("SUCCESS")) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", response.getUser_no());
        }
        return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
    }
}
