package gdsc.netwalk.gcs.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GCSUploadFileRequest {
    private int act_no;
    private String category;
    private String file;
}
