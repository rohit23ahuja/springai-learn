package learn.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {
    private final ChatClient chatClient;

    public BookController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/by-author")
    public Author getBooksByAuthor(@RequestParam(value="author", defaultValue = "Carl Newport") String author){
       String promptMessage = """
               Generate a list of books written by author {author}. If you aren't positive that a book belongs to this
               author please don't include it.
               {format}
               """;
        BeanOutputConverter<Author> authorBeanOutputConverter = new BeanOutputConverter<>(Author.class);
        String format = authorBeanOutputConverter.getFormat();
        Prompt prompt = new PromptTemplate(promptMessage).create(Map.of("author", author, "format", format));
        ChatClient.CallResponseSpec responseSpec = chatClient.prompt(prompt).call();
        return authorBeanOutputConverter.convert(responseSpec.content());
    }

    @GetMapping("/author/{author}")
    public Map<String, Object> getAuthorsSocialLinks(@PathVariable String author) {
        String promptMessage = """
                Generate a list of links for the author {author}. Include the authors name as the key and any social links as the object.
                {format}
                """;
        MapOutputConverter mapOutputConverter = new MapOutputConverter();
        String format = mapOutputConverter.getFormat();
        PromptTemplate promptTemplate = new PromptTemplate(promptMessage, Map.of("author", author, "format", format));
        Prompt prompt = promptTemplate.create();
        ChatClient.CallResponseSpec responseSpec = chatClient.prompt(prompt).call();
        return mapOutputConverter.convert(responseSpec.content());
    }
}
