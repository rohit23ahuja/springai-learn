package learn.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;

public class SimplePromptController {

    private final ChatClient chatClient;

    public SimplePromptController(ChatClient.Builder builder){
        this.chatClient = builder.build();
    }
    public String simple(){

        return chatClient.prompt(new Prompt("Tell me a dad joke")).call().content();
    }

}
