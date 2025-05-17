package learn.springai.stream;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class StreamController {
    private final ChatClient chatClient;


    public StreamController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/places-to-visit")
    public Flux<String> placesToVisit(){
        return chatClient.prompt()
                .user("I am visiting New Delhi next week. suggest me 10 places where i should go for eating food.")
                .stream()
                .content();

    }

//    @GetMapping("/places-to-visit")
//    public String placesToVisit(){
//        return chatClient.prompt()
//                .user("I am visiting New Delhi next week. suggest me 10 places where i should go for eating food.")
//                .call()
//                .content();
//
//    }
}
