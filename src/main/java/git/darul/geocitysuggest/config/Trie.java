package git.darul.geocitysuggest.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String cityName) {
        TrieNode node = root;
        for (char ch : cityName.toLowerCase().toCharArray()) {
            node = node.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        node.isEndOfWord = true;
        node.cityName = cityName;
    }

    public List<String> searchByPrefix(String prefix) {
        TrieNode node = root;

        for (char ch : prefix.toLowerCase().toCharArray()) {
            node = node.children.get(ch);
            if (node == null) {
                return new ArrayList<>();
            }
        }

        return findCitiesWithPrefix(node, prefix);
    }

    private List<String> findCitiesWithPrefix(TrieNode node, String prefix) {
        List<String> cities = new ArrayList<>();

        if (node.cityName != null) {
            cities.add(node.cityName);
        }

        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            cities.addAll(findCitiesWithPrefix(entry.getValue(), prefix + entry.getKey()));
        }
        return cities;
    }
}
