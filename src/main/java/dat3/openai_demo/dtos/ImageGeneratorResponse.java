package dat3.openai_demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class ImageGeneratorResponse {
    private final int code = 0;
    private final String msg = "";
    private String data;

    public ImageGeneratorResponse(String data) {
        this.data = data;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class Data{
        private int task_id;
    }
}
