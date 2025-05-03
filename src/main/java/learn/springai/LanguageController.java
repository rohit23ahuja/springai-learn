package learn.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LanguageController {

    private final ChatClient chatClient;

    public LanguageController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @GetMapping("/chat")
    public String generateResponses(@RequestParam(value = "prompt", defaultValue = "hello") String prompt){
        SystemMessage systemMessage = new SystemMessage("""
        Your primary function is to respond to prompts that are provided in English language. 
        If the prompt contains any other words, say i dont know these words and list those words in the response""");
        UserMessage userMessage = new UserMessage(prompt);
        return chatClient.prompt(new Prompt(List.of(systemMessage, userMessage) )).call().content();
    }
}
