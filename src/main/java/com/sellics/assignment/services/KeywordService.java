package com.sellics.assignment.services;

import com.sellics.assignment.clients.AmazonAutocompleteClient;
import com.sellics.assignment.dto.KeywordDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final AmazonAutocompleteClient amazonAutocompleteClient;

    private final HashMap<Integer, Integer> suggestionValueConfig = loadSuggestionValueConfig();

    /**
     * Estimate the keyword search volume in order to Amazon Autocomplete AJAX API.
     * @param keyword
     * @return KeywordDetailDTO with keyword and score.
     */
    public KeywordDetailDTO getKeywordSearchVolume(String keyword) {
        char[] characters = keyword.toCharArray();
        boolean keywordNotFounded = true;
        Integer characterIndex = 0;
        StringBuilder searchCriteria = new StringBuilder();
        Integer searchVolumeScore = 0;
        ArrayList<Integer> charactersWeight = calculateCharacterWeight(characters);

        while(keywordNotFounded) {
            if(characterIndex < characters.length) {
                searchCriteria.append(characters[characterIndex]);
                ArrayList<String> suggestions = amazonAutocompleteClient.searchByKeyword(searchCriteria.toString());
                Integer keywordPositionValue = getKeywordPositionValueFromSuggestions(suggestions, keyword, charactersWeight, characterIndex);

                if(keywordPositionValue > 0) {
                    keywordNotFounded = false;
                    searchVolumeScore = keywordPositionValue;
                }

                characterIndex++;

            } else {
                keywordNotFounded = false;
            }
        }

        return KeywordDetailDTO.builder().keyword(keyword).score(searchVolumeScore).build();
    }

    /**
     * Search for keyword in suggestions list. If keyword is present, calculate the score in order to the position within suggestions list.
     * If keyword is not present return -1.
     * @param suggestions
     * @param keyword
     * @param charactersWeight
     * @param characterIndex
     * @return Integer value with keyword score if it is present or -1 in case keyword is not present.
     */
    private Integer getKeywordPositionValueFromSuggestions(ArrayList<String> suggestions, String keyword, ArrayList<Integer> charactersWeight, Integer characterIndex) {
        Integer searchVolumeScore = -1;

        for(int suggestionIndex = 0; suggestionIndex < suggestions.size(); suggestionIndex++) {
            String suggestion = suggestions.get(suggestionIndex);

            if(suggestion.equalsIgnoreCase(keyword)) {
                Integer characterWeight = charactersWeight.get(characterIndex);
                Integer suggestionPositionValue = suggestionValueConfig.get(suggestionIndex);
                searchVolumeScore = (suggestionPositionValue * characterWeight.intValue()) / 100;
                break;
            }
        }

        return searchVolumeScore;
    }

    /**
     * Calculate the weight for each character in the keyword.
     * This value will be used to calculate the final score in base of the position of the exact keyword
     * inside the suggestion list.
     * @param characters
     * @return ArrayList with the weight of each character of the keyword
     */
    private ArrayList<Integer> calculateCharacterWeight(char[] characters) {
        ArrayList<Integer> characterWeights = new ArrayList<>();
        Integer totalValue = 100;
        Integer characterValue = totalValue / characters.length;

        for (int i = 0; i < characters.length; i++) {
            characterWeights.add(totalValue);
            totalValue = totalValue - characterValue;
        }

        return characterWeights;
    }

    /**
     * Load suggestions options values map configuration.
     * This configuration assign a specific value that represents a percentage for each suggestion result.
     * @return HashMap with configuration.
     */
    private final HashMap<Integer, Integer> loadSuggestionValueConfig() {
        HashMap<Integer, Integer> config = new HashMap<>();
        config.put(0, 100);
        config.put(1, 90);
        config.put(2, 80);
        config.put(3, 70);
        config.put(4, 60);
        config.put(5, 50);
        config.put(6, 40);
        config.put(7, 30);
        config.put(8, 20);
        config.put(9, 10);

        return config;
    }
}
