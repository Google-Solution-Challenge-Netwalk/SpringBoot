package gdsc.netwalk.gcs.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
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

            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder(bucketName, uuid)
                            .setContentType(ext)
                            .build()
            );

            System.out.println(uuid);

            // [2] img url Local DB 저장
            String default_url = "https://storage.cloud.google.com/";
            String img_url = default_url + bucketName + "/" + uuid;

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
}
