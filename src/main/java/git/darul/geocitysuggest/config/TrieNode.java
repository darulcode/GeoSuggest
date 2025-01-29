package git.darul.geocitysuggest.config;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    public final Map<Character, TrieNode> children = new HashMap<>();
    public boolean isEndOfWord = false;
    public String cityName;
}

