package com.web.crawler.model;

public class WordNode {

    private String word;
    private int frequency;

    public WordNode() {
        this.word = "";
        this.frequency = 0;
    }

    public WordNode(String word) {
        this.word = word;
        this.frequency = 1;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

}
