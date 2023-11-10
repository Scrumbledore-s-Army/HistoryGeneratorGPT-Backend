package dat3.openai_demo.api;

import dat3.openai_demo.service.ImageService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/image")
public class ImageController {
    private final int BUCKET_CAPACITY = 20;
    private final int REFILL_AMOUNT = 20;
    private final int REFILL_TIME = 2;
    private ImageService imageService;
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.classic(BUCKET_CAPACITY, Refill.greedy(REFILL_AMOUNT, Duration.ofMinutes(REFILL_TIME)));
        return Bucket.builder().addLimit(limit).build();
    }

    private Bucket getBucket(String key) {
        return buckets.computeIfAbsent(key, k -> createNewBucket());
    }

    @GetMapping("/generateimage")
    public String generateImage(@RequestParam String prompt, HttpServletRequest request){
        String ip = request.getRemoteAddr();
        Bucket bucket = getBucket(ip);
        if (!bucket.tryConsume(1)) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests, try again later");
        }
        return imageService.getImage(prompt);
    }

}
