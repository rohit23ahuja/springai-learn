package learn.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/olympic")
public class OlympicController {

    private final ChatClient chatClient;

    @Value("classpath:/prompts/olympic-sports.st")
    private Resource olympicSportsResource;

    @Value("classpath:/docs/2024-olympic-sports.txt")
    private Resource docsToStuffResource;

    public OlympicController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @GetMapping("/2024")
    public List<String> get2024OlympicSports(
            @RequestParam(value = "message", defaultValue = "What sports are being include in 2024 Summer Olympics?") String message,
            @RequestParam(value = "stuffit", defaultValue = "false") boolean stuffIt) {
        PromptTemplate promptTemplate = new PromptTemplate(olympicSportsResource);
        ListOutputConverter listOutputConverter = new ListOutputConverter(new DefaultConversionService());
        HashMap<String, Object> map = new HashMap<>();
        map.put("question", message);
        if (stuffIt){
            map.put("context", docsToStuffResource);
        } else {
            map.put("context", "");
        }
        map.put("format", listOutputConverter.getFormat());
        Prompt prompt = promptTemplate.create(map);
        return listOutputConverter.convert(chatClient.prompt(prompt).call().content());
    }
}
