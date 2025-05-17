package learn.springai.fluent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JokesController {

    private final ChatClient chatClient;


    public JokesController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("you are loud assitant that always responds in capital letters.")
                .build();
    }


    @GetMapping("/jokes-by-topic/{topic}")
    public String generateJokes(@PathVariable(value = "topic") String topic){
        return chatClient.prompt()
                .user(u -> u.text("Tell me a joke about {topic}")
                        .param("topic", topic))
                .call()
                .content();
    }


}
