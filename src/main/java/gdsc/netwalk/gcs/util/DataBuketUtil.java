package gdsc.netwalk.gcs.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import gdsc.netwalk.gcs.dto.FileDto;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Component
public class DataBuketUtil {

    @Value("${cloud.gcp.credentials.location}")
    private String gcpConfigFile;

    @Value("${cloud.gcp.project-id}")
    private String gcpProjectId;

    @Value("${cloud.gcp.bucket-id}")
    private String gcpBucketId;

    public FileDto uploadFile(MultipartFile multipartFile, String fileName, String contentType) throws Exception {

        try{

            byte[] fileData = FileUtils.readFileToByteArray(convertFile(multipartFile));

            InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();

            StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream)).build();

            Storage storage = options.getService();
            Bucket bucket = storage.get(gcpBucketId,Storage.BucketGetOption.fields());

            RandomString id = new RandomString();
            Blob blob = bucket.create( "/" + fileName + "-" + id.nextString() + checkFileExtension(fileName), fileData, contentType);

            if(blob != null){
                return new FileDto(blob.getName(), blob.getMediaLink());
            }

        }catch (Exception e){
            System.out.println("An error occurred while storing data to GCS");
        }
        System.out.println("An error occurred while storing data to GCS");
        throw new Exception();
    }

    private File convertFile(MultipartFile file) throws Exception {

        try{
            if(file.getOriginalFilename() == null){
                System.out.println("Original file name is null");
            }
            File convertedFile = new File(file.getOriginalFilename());
            FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write(file.getBytes());
            outputStream.close();

            return convertedFile;

        } catch (Exception e){
            System.out.println("An error has occurred while converting the file");
        }
        throw new Exception();
    }

    private String checkFileExtension(String fileName) throws Exception {
        if(fileName != null && fileName.contains(".")){
            String[] extensionList = {".png", ".jpeg", ".pdf", ".doc", ".mp3"};

            for(String extension: extensionList) {
                if (fileName.endsWith(extension)) {
                    return extension;
                }
            }
        }
        throw new Exception();
    }
}