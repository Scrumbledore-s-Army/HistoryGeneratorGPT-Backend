package dat3.openai_demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ImageResponse {
    private final int code = 0;
    private final String msg = "";
    private Data data;
    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class Data {
        private int status;
        private int progress;
        private int eta_relative;
        private String[] imgs;
        private String info;
        private String failed_reason;
        private String[] current_images;
        private String submit_time;
        private String execution_time;
        private String txt2img_time;
        private String finish_time;
    }
}
