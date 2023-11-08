package dat3.openai_demo.api;

import dat3.openai_demo.dtos.MyResponse;
import dat3.openai_demo.service.OpenAiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/story")
@CrossOrigin(origins = "*")
public class JokeController {

    private OpenAiService service;
    final static String SYSTEM_MESSAGE = "Du er en dygtig forfatter af godnathistorier til børn. Du skriver personlige historier baseret på børns interesser og de vigtige personer i deres liv, som venner og forældre. Dine historier er af høj kvalitet og skrives på dansk. Start historien midt i begivenhederne, hvor der sker noget spændende, og skab en uundgåelig vendepunkt og en fantastisk afslutning, der indeholder en vigtig lære for barnet. Brug følgende oplysninger om barnet til at opbygge historien:\n" +
            "\n" +
            "Barnets navn.\n" +
            "Alder.\n" +
            "Interesser.\n" +
            "Venner.\n" +
            "Forældre.\n" +
            "\n" +
            "\n" +
            "Lav 5 kapitler Hver kapitel skal fylde en halv normalside. Alle kapitler undtagen kapitel 5 skal ende med en \"cliff hanger\"\n" +
            "Kapitel 5 skal afslutte historien.\n" +
            "\n" +
            "Under hvert kapitel lav et engelsk text-to-image prompt så der kan generes et billed der passer til kapitlet. \n" +
            "Brug ikke egenavne fra historien, men bare forklar billedet. Formater historien sådan her: [CHAPTER NUMBER: ...] [CHAPTER-TITLE: ...] [CHAPTER-TEXT: ...] [CHAPTER-IMAGE-PROMPT: ...]";

    public JokeController(OpenAiService service) {
        this.service = service;
    }

    @GetMapping
    public MyResponse getJoke(@RequestParam String about) {

        return service.makeRequest(about, SYSTEM_MESSAGE);
    }
}
