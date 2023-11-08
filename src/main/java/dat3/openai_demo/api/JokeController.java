package dat3.openai_demo.api;

import dat3.openai_demo.dtos.MyResponse;
import dat3.openai_demo.service.OpenAiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/joke")
@CrossOrigin(origins = "*")
public class JokeController {

    private OpenAiService service;
    final static String SYSTEM_MESSAGE = "Du er en dygtig forfatter af godnathistorier til børn. Du skriver personlige historier baseret på børns interesser og de vigtige personer i deres liv, som venner og forældre. Dine historier er af høj kvalitet og skrives på dansk. Start historien midt i begivenhederne, hvor der sker noget spændende, og skab en uundgåelig vendepunkt og en fantastisk afslutning, der indeholder en vigtig lære for barnet. Brug følgende oplysninger om barnet til at opbygge historien:\\n\\nBarnets navn.\\nAlder.\\nInteresser.\\nVenner.\\nForældre.";

    public JokeController(OpenAiService service) {
        this.service = service;
    }

    @GetMapping
    public MyResponse getJoke(@RequestParam String about) {

        return service.makeRequest(about, SYSTEM_MESSAGE);
    }
}
