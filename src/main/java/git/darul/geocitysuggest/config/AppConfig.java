package git.darul.geocitysuggest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Trie trie() {
        return new Trie();
    }
}
