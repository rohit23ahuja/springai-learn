package learn.springai.fluent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.List;

@RestController
public class ActorFilmsController {

    private final ChatClient chatClient;

    public ActorFilmsController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("you are an helpful AI assistant answering questions about films and actors. do your best to provide full list of films.")
                .build();
    }


    @GetMapping(value = "/films-by-actor-string/{actor}")
    public String generateActorFilmographyString(@PathVariable(value = "actor") String actor) {
        return chatClient.prompt()
                .user(u -> u.text("Generate a filmography for actor {actor}")
                        .param("actor", actor))
                .call()
                .content();
    }

    @GetMapping(value = "/films-by-actor/{actor}")
    public ActorFilms generateActorFilmography(@PathVariable(value = "actor") String actor) {
        return chatClient.prompt()
                .user(u -> u.text("Generate a filmography for actor {actor}")
                        .param("actor", actor))
                .call()
                .entity(ActorFilms.class);
        //.content();
    }

    @GetMapping(value = "/films-list")
    public List<ActorFilms> generateFilmsList(){
        return chatClient.prompt()
                .user(u-> u.text("Generate a filmography for actors Shahrukh Khan, Aaamir Khan and Salman Khan."))
                .call()
                .entity(new ParameterizedTypeReference<List<ActorFilms>>() {});

    }
}
