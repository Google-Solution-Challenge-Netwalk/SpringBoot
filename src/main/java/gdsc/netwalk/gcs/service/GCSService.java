package gdsc.netwalk.gcs.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import gdsc.netwalk.common.vo.CustomMap;
import gdsc.netwalk.dto.common.CustomResponse;
import gdsc.netwalk.gcs.dto.GCSUploadFileRequest;
import gdsc.netwalk.mapper.activity.ActivityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class GCSService {
    @Autowired
    private ActivityMapper activityMapper;

    @Value("${spring.cloud.gcp.storage.credentials.location}")
    private String keyFileLocation;

    @Value("${spring.cloud.gcp.storage.project-id}")
    private String projectId;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    private final String FILE_STORE_LOCATION = "/Users/jeongminchang/Desktop/repo/2023-solution-challenge-netwalk/IMG/";
    private final String BASE_URL = "https://storage.cloud.google.com/";

    /*
    * GCS 파일 업로드
    * */
    @Transactional
    public CustomResponse uploadFile(MultipartFile file, GCSUploadFileRequest request) throws IOException {
        CustomResponse response = new CustomResponse();
        try {

            // [1] GCS file upload
            String uuid = UUID.randomUUID().toString();
            String ext = file.getContentType();

            InputStream keyFile = ResourceUtils.getURL(keyFileLocation).openStream();

            Storage storage = StorageOptions.newBuilder().setProjectId(projectId)
                    // Key 파일 수동 등록
                    .setCredentials(GoogleCredentials.fromStream(keyFile))
                    .build().getService();

            String fileName = UUID.randomUUID().toString() + "." + getFileExtension(file.getOriginalFilename());
            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
            Blob blob = storage.create(blobInfo, file.getBytes());

            // [2] img url Local DB 저장
            // [2-1] img local 저장
            String fileType = file.getContentType();
            long fileSize = file.getSize();

            File saveFile = new File(FILE_STORE_LOCATION + fileName);
            if(!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }

            file.transferTo(saveFile);

            // [2-2] img url local DB 저장
            String img_url = BASE_URL + bucketName + "/" +blob.getName();

            CustomMap param = new CustomMap();
            param.set("act_no", request.getAct_no());
            param.set("category", request.getCategory());
            param.set("img_url", img_url);
            activityMapper.registerActivityTrashImg(param);

            response.setStatus("SUCCESS");
            response.setMessage("GCS 파일 업로드 성공");

        } catch (Exception e) {
            response.setStatus("FAIL");
            response.setMessage("GCS 파일 업로드 실패");

            System.out.println("exception: " + e);
        }
        return response;
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
