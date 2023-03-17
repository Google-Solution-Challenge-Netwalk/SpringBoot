package gdsc.netwalk.controller.activity;

import gdsc.netwalk.dto.activity.request.ActivityListRequest;
import gdsc.netwalk.dto.activity.request.RegisterActivityRequest;
import gdsc.netwalk.dto.common.CustomResponse;
import gdsc.netwalk.service.activity.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/act")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @ResponseBody
    @PostMapping
    public ResponseEntity<CustomResponse> registerActivity(@RequestBody RegisterActivityRequest registerActivityRequest) {
        CustomResponse response = activityService.registerActivity(registerActivityRequest);
        return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/user")
    public ResponseEntity<CustomResponse> selectActivityByUser(@RequestBody ActivityListRequest activityListRequest) {
        CustomResponse response = activityService.selectActivityByUser(activityListRequest);
        return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
    }
}
