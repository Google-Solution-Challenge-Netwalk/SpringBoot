package gdsc.netwalk.controller.group;

import gdsc.netwalk.dto.common.CustomResponse;
import gdsc.netwalk.dto.group.request.CategoryGroupListRequest;
import gdsc.netwalk.dto.group.request.ParticipateGroupRequest;
import gdsc.netwalk.dto.group.request.RegisterGroupRequest;
import gdsc.netwalk.service.group.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/v1/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @ResponseBody
    @PostMapping
    public ResponseEntity<CustomResponse> registerGroup(HttpServletRequest request, @RequestBody RegisterGroupRequest registerGroupRequest) {
        CustomResponse response = groupService.registerGroup(registerGroupRequest);
        return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/me/{createUserNo}")
    public ResponseEntity<CustomResponse> selectGroupListByCreateUserNo(HttpServletRequest request, @PathVariable("createUserNo") int createUserNo) {
        CustomResponse response = groupService.selectGroupListByCreateUserNo(request, createUserNo);
        return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/part/{userNo}")
    public ResponseEntity<CustomResponse> selectParticipateGroupList(HttpServletRequest request, @PathVariable("userNo") int userNo) {
        CustomResponse response = groupService.selectParticipateGroupList(userNo);
        return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/part")
    public ResponseEntity<CustomResponse> registerParticipateGroup(HttpServletRequest request, @RequestBody ParticipateGroupRequest participateGroupRequest) {
        CustomResponse response = groupService.registerParticipateGroup(participateGroupRequest);
        return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/cate")
    public ResponseEntity<CustomResponse> selectCategoryGroup(HttpServletRequest request, @RequestBody CategoryGroupListRequest categoryGroupListRequest) {
        CustomResponse response = groupService.selectCategoryGroup(categoryGroupListRequest);
        return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/{groupNo}")
    public ResponseEntity<CustomResponse> selectGroupInUsers(@PathVariable("groupNo") int groupNo) {
        CustomResponse response = groupService.selectGroupInUsers(groupNo);
        return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
    }
}
