package dat3.openai_demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat3.openai_demo.dtos.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;



@Service
public class StoryService {


    @Value("${api.image-api-key}")
    private String API_KEY;

    private final static String url = "http://api.novita.ai/v2/txt2img";
    private final static String image_url = "http://api.novita.ai/v2/progress?task_id=";
    private final String negative_prompt = "nsfw,ng_deepnegative_v1_75t, badhandv4, (worst quality:2), (low quality:2), (normal quality:2), lowres, ((monochrome)), ((grayscale)), watermark";
    private final static String sampler_name = "DPM++ SDE Karras";
    private final static int batch_size = 1;
    private final static int n_iter = 1;
    private final static int steps = 20;
    private final static int cfg_scale = 7;
    private final static int seed = -1;
    private final static int height = 1024;
    private final static int width = 1024;
    private final String model_name = "sd_xl_base_1.0.safetensors";

    public ImageGeneratorResponse getTaskId(String prompt) {

        WebClient client = WebClient.create();

        Map<String, Object> body = new HashMap<>();

        body.put("prompt", prompt);
        body.put("negative_prompt", negative_prompt);
        body.put("sampler_name", sampler_name);
        body.put("batch_size", batch_size);
        body.put("n_iter", n_iter);
        body.put("steps", steps);
        body.put("cfg_scale", cfg_scale);
        body.put("seed", seed);
        body.put("height", height);
        body.put("width", width);
        body.put("model_name", model_name);

        ObjectMapper mapper = new ObjectMapper();

        String json = "";

        try {
            json = mapper.writeValueAsString(body);
            System.out.println(json);
            ImageGeneratorResponse response = client.post()
                    .uri(new URI(url))
                    .header("Authorization", "Bearer " + API_KEY)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(json))
                    .retrieve()
                    .bodyToMono(ImageGeneratorResponse.class)
                    .block();

            assert response != null;

            return new ImageGeneratorResponse(response.getData());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String getImage(String prompt){
        WebClient webClient = WebClient.create();
        ImageGeneratorResponse imageGeneratorResponse = getTaskId(prompt);
        String taskId = imageGeneratorResponse.getData().getTask_id();
        System.out.println(taskId);
        try {
            Thread.sleep(2000);
            ImageResponse response = webClient.get()
                    .uri(new URI(image_url+taskId))
                    .header("Authorization", "Bearer " + API_KEY)
                    .retrieve()
                    .bodyToMono(ImageResponse.class)
                    .block();
            while(response.getData().getProgress() == 0){
                Thread.sleep(1000);
                response = webClient.get()
                        .uri(new URI(image_url+taskId))
                        .header("Authorization", "Bearer " + API_KEY)
                        .retrieve()
                        .bodyToMono(ImageResponse.class)
                        .block();
            }
            System.out.println(response.getData().toString());
            return response.getData().getImgs()[0];
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

}
