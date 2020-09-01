package com.web.crawler.wc;

import com.web.crawler.model.WordNode;
import com.web.crawler.utils.HtmlUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;

public class WordCounter {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(WordCounter.class);
    private static Map<String, WordNode> wordFreqMap;
    private static Map<String, WordNode> wordPairMap;
    private static int wordCount = 0;
    private static int n = 10;

    public static Map<String, WordNode> getWordFreqMap() {
        return wordFreqMap;
    }

    public static Map<String, WordNode> getWordPairMap() {
        return wordPairMap;
    }

    public static boolean parseAndCount(String url, int numberOfWords) {
        n = numberOfWords;

        if(!validateMinMaxOfN(numberOfWords)){
            log.error("Please enter a valid number between 1-100");
            return false;
        }

        if (!HtmlUtil.isValidUrl(url)) {
            log.error(
                "Invalid URL '{}', please enter valid URL (Eg- http(s)://www.site-to-visi.com/)",
                url);
            return false;
        }

        String[] words;
        try {
            words = HtmlUtil.parseWebPageToWordArray(url);
        } catch (IOException e) {
            log.error("Error accessing page at '{}'", url, e);
            return false;
        }

        findNFreqElements(words);
        printSummary(url);

        return true;
    }

    public static void findNFreqElements(String[] words) {
        wordFreqMap = new HashMap();
        wordPairMap = new HashMap();

        String prevWord = null;
        String tempKey;

        WordNode temp;
        boolean endOfSentence;
        for (String word : words) {
            word = word.replaceAll(" ", "");
            if (word.equals("")) {
                continue;
            }

            wordCount++;
            endOfSentence = false;

            if (word.endsWith(".")) {
                endOfSentence = true;
                word = word.substring(0, word.length() - 1);
            }

            //Update frequencies against the word in a map
            if (wordFreqMap.containsKey(word)) {
                temp = wordFreqMap.get(word);
                temp.setFrequency(temp.getFrequency() + 1);
                wordFreqMap.put(word, temp);
            } else {
                temp = new WordNode(word);
                wordFreqMap.put(word, temp);
            }

            //Update frequencies against the word pair in a map
            if (wordPairMap.size() == 0 && prevWord == null) {
                prevWord = word;
            } else {
                //Again start from the first word of the sentence
                if (prevWord.equals("")) {
                    prevWord = word;
                    continue;
                }

                //Creating unique key with combination of word pairs
                tempKey = prevWord + "," + word;
                if (wordPairMap.containsKey(tempKey)) {
                    temp = wordPairMap.get(tempKey);
                    temp.setFrequency(temp.getFrequency() + 1);
                    wordPairMap.put(tempKey, temp);
                } else {
                    temp = new WordNode(tempKey);
                    wordPairMap.put(tempKey, temp);
                }
                prevWord = word;

                //Re-initialize if it's end of sentence
                if (endOfSentence) {
                    prevWord = "";
                }
            }
        }
    }

    public static void printSummary(String url) {
        log.info("{} most frequent words in webpage '{}'", n, url);
        for (WordNode node : getNFreqElements(wordFreqMap)) {
            log.info("{} : {}", node.getWord(), node.getFrequency());
        }

        log.info("---------------------------------------------------------------");

        log.info("{} most frequent word pair present in webpage '{}'", n, url);
        for (WordNode node : getNFreqElements(wordPairMap)) {
            log.info("{} : {}", node.getWord(), node.getFrequency());
        }

        log.info("--------------------------Summary------------------------------");
        log.info("Total words present : {}", wordCount);
        log.info("Total unique words found : {}", wordFreqMap.size());
        log.info("Total word pairs found : {}", wordPairMap.size());
        log.info("---------------------------------------------------------------");
    }

    public static List<WordNode> getNFreqElements(Map<String, WordNode> wordMap) {
        Comparator<WordNode> sortByFreq = (c1, c2) -> {
            int result = Integer.compare(c2.getFrequency(), c1.getFrequency());
            return result == 0
                ? c1.getWord().compareTo(c2.getWord()) : result;
        };

        int i = 0;
        List<WordNode> tempList = wordMap.values().stream()
            .sorted(sortByFreq).collect(Collectors.toList());
        List<WordNode> tempList1 = new ArrayList<>();

        for (WordNode node : tempList) {
            tempList1.add(node);
            i++;
            if (i >= n) {
                break;
            }
        }

        return tempList1;
    }

    public static boolean validateMinMaxOfN(int numberOfWords){
        return (numberOfWords>0 && numberOfWords<=100);
    }
}
