package learn.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }

    @GetMapping("/story")
    public String generateStory(@RequestParam(value="prompt", defaultValue = "Tell me an story in 200 words about hindu god shiva.") String prompt){
        return chatClient.prompt(prompt).call().content();
    }
}
