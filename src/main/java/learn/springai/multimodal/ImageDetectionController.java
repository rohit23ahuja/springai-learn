package learn.springai.multimodal;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageDetectionController {
    private final ChatClient chatClient;

    @Value("classpath:/images/diehard1.jpg")
    private Resource movieImage;

    @Value("classpath:/images/observability-setup.png")
    private Resource codeImage;


    public ImageDetectionController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/image-to-text")
    public String imageDescription(){
        return chatClient.prompt()
                .user(u -> u
                        .text("Can you please explain what you see in the following image?")
                        .media(MimeTypeUtils.IMAGE_JPEG, movieImage))
                .call().content();
    }

    @GetMapping("/code-explanation")
    public String codeExplanation(){
        return chatClient.prompt()
                .user(u -> u
                        .text("The following is a screenshot of some code. Can you do your best to describe it?")
                        .media(MimeTypeUtils.IMAGE_PNG, codeImage))
                .call().content();
    }

    @GetMapping("/image-to-code")
    public String imageToCode(){
        return chatClient.prompt()
                .user(u -> u
                        .text("The following is a screenshot of some code. Can you do your best to generate code that is in this screenshot?")
                        .media(MimeTypeUtils.IMAGE_PNG, codeImage))
                .call().content();
    }
}
