package gdsc.netwalk.controller.activity;

import gdsc.netwalk.dto.activity.request.ActivityListRequest;
import gdsc.netwalk.dto.activity.request.RegisterActivityRequest;
import gdsc.netwalk.dto.activity.request.UpdateAcitivtyShareSTRequest;
import gdsc.netwalk.dto.activity.request.UpdateActivityRequest;
import gdsc.netwalk.dto.common.CustomResponse;
import gdsc.netwalk.service.activity.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public ResponseEntity<CustomResponse> selectAllActivity() {
        CustomResponse response = activityService.selectAllActivity();
        return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/update")
    public ResponseEntity<CustomResponse> updateActivity(@RequestBody UpdateActivityRequest updateActivityRequest) {
        CustomResponse response = activityService.updateActivity(updateActivityRequest);
        return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/user")
    public ResponseEntity<CustomResponse> selectActivityByUser(@RequestBody ActivityListRequest activityListRequest) {
        CustomResponse response = activityService.selectActivityByUser(activityListRequest);
        return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
    }

    @ResponseBody
    @GetMapping("/rank/{type}")
    public ResponseEntity<CustomResponse> selectRankingActivity(@PathVariable("type") String type) {
        CustomResponse response = activityService.selectRankingActivity(type);
        return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("shareSt/update")
    public ResponseEntity<CustomResponse> updateAcitivtyShareST(@RequestBody UpdateAcitivtyShareSTRequest updateAcitivtyShareSTRequest) {
        CustomResponse response = activityService.updateAcitivtyShareST(updateAcitivtyShareSTRequest);
        return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/trash/{act_no}")
    public ResponseEntity<CustomResponse> selectTrashByActivity(@PathVariable("act_no") int act_no) {
        CustomResponse response = activityService.selectTrashByActivity(act_no);
        return new ResponseEntity<CustomResponse>(response, HttpStatus.OK);
    }

}
