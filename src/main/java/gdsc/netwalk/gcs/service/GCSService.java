package gdsc.netwalk.gcs.service;

import gdsc.netwalk.dto.common.CustomResponse;
import gdsc.netwalk.gcs.dto.FileDto;
import gdsc.netwalk.gcs.dto.GCSUploadFileRequest;
import gdsc.netwalk.gcs.util.DataBuketUtil;
import gdsc.netwalk.mapper.activity.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class GCSService {
    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private DataBuketUtil dataBuketUtil;

    @Transactional
    public CustomResponse uploadFile(MultipartFile file, GCSUploadFileRequest request) {
        CustomResponse response = new CustomResponse();
        try {

            String originalFileName = file.getOriginalFilename();
            if(originalFileName == null) {
                throw new Exception();
            }
            Path path = new File(originalFileName).toPath();

            String contentType = Files.probeContentType(path);
            FileDto fileDto = dataBuketUtil.uploadFile(file, originalFileName, contentType);

            response.setObject(fileDto);
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
