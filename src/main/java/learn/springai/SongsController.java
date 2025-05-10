package learn.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
public class SongsController {
    private final ChatClient chatClient;

    public SongsController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @GetMapping("/songs")
    public List<String> getSongsByArtist(@RequestParam(value = "artist", defaultValue = "Sonu Nigam") String artist) {
        var message = """
                Please give me a list of top 10 songs for the artist {artist}. If you don't know, just say I don't know.
                {format}
                """;
        ListOutputConverter listOutputConverter = new ListOutputConverter(new DefaultConversionService());
        PromptTemplate promptTemplate = new PromptTemplate(message, Map.of("artist", artist, "format", listOutputConverter.getFormat()));
        Prompt prompt = promptTemplate.create();
        ChatClient.CallResponseSpec responseSpec = chatClient.prompt(prompt).call();
        return listOutputConverter.convert(responseSpec.content());
    }
}
