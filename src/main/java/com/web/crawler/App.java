package com.web.crawler;

import com.web.crawler.wc.WordCounter;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.slf4j.LoggerFactory;

public class App {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        String url = "";
        int numberOfWords = 10;

        switch (args.length) {
            case 0:
                printProgramBrief(0);
                log.info("Please enter URL to scan (Eg: http(s)://www.site-to-visit.com/)");
                Scanner in = new Scanner(System.in);
                url = in.nextLine();
                log.info("Please enter value of 'N' (1-100)");
                try {
                    numberOfWords = in.nextInt();
                }catch (InputMismatchException e){
                    log.error("Please enter a valid number between 1-100");
                    return;
                }
                break;
            case 1:
                log.info("Taking default value for N ({})", numberOfWords);
                printProgramBrief(numberOfWords);
                url = args[0];
                break;
            case 2:
                url = args[0];
                try {
                    numberOfWords = Integer.parseInt(args[1]);
                    printProgramBrief(numberOfWords);
                } catch (NumberFormatException e) {
                    log.error("Please enter a valid number between 1-100");
                    return;
                }
                break;
            default:
                log.error("Allowed parameters are <url> <number(default-10)>");
                return;
        }

        if (WordCounter.parseAndCount(url, numberOfWords)) {
            log.info("Execution completed successfully!");
        } else {
            log.info("Error occurred while execution");
        }
    }

    public static void printProgramBrief(int n) {
        log.info(
            "This program scans through a given webpage, \nand display the top '{}' frequent words and word pairs (two words in the same order) along with their frequency.",
            n > 0 ? n : "N");
    }

}
