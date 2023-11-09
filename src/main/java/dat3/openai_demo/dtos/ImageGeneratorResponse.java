package dat3.openai_demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ImageGeneratorResponse {
    private final int code = 0;
    private final String msg = "";
    private Data data;

    public ImageGeneratorResponse(Data data) {
        this.data = data;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Data{
        private String task_id;
    }
}
