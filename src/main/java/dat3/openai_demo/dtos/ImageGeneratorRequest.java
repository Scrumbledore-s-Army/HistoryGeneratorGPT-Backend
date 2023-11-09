package dat3.openai_demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageGeneratorRequest {
    private String prompt;
    private String negative_prompt;
    private String sampler_name;
    private int batch_size;
    private int n_iter;
    private int steps;
    private int cfg_scale;
    private int seed;
    private int height;
    private int width;
    private String model_name;


    public ImageGeneratorRequest(String prompt) {
        this.prompt = prompt;
    }
}
