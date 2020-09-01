package com.web.crawler;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.web.crawler.model.WordNode;
import com.web.crawler.wc.WordCounter;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.LoggerFactory;

@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class AppTest
{

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AppTest.class);

    @Test
    public void failForInvalidURL()
    {
        assertFalse( WordCounter.parseAndCount("abcd",10));
        assertFalse( WordCounter.parseAndCount("http://abcd.abcd",10));
        log.info("Test case - 'failForInvalidURL' - Passed");
    }

    @Test
    public void passWordCounter()
    {
        WordCounter.findNFreqElements("A good example needs no explanation. Example needs to be self explanatory. Though it's good to have an explanation. Good day!".toLowerCase().split(" "));
        List<WordNode> wordMap = WordCounter
            .getNFreqElements(WordCounter.getWordFreqMap());
        List<WordNode> wordPairMap = WordCounter
            .getNFreqElements(WordCounter.getWordPairMap());

        assertTrue(wordMap.get(0).getWord().equalsIgnoreCase("good") && wordMap.get(0).getFrequency()==3);
        assertTrue(wordPairMap.get(0).getWord().equalsIgnoreCase("example,needs") && wordPairMap.get(0).getFrequency()==2);

        log.info("Test case - 'passWordCounter' - Passed");
    }

    @Test
    public void passWebPageParser()
    {
        assertTrue(WordCounter.parseAndCount("https://github.com/shashidharb06/WebCrawler/edit/master/README.md", 2));
        log.info("Test case - 'passWebPageParser' - Passed");
    }

    @Test
    public void failForNumberValidation()
    {
        assertFalse(WordCounter.parseAndCount("https://github.com/shashidharb06/WebCrawler/edit/master/README.md", -10));
        log.info("Test case - 'failForNumberValidation' - Passed");
    }

}
