# WebCrawler

This program scans through a given webpage,  and display the top 'N' frequent words and word pairs (two words in the same order) along with their frequency.


Building the project
====================
Run below command to build :
```
mvn assembly:assembly -DdescriptorId=jar-with-dependencies
```


Running the project
===================
Below are the ways to run the program
1. Execute via interactive console input:
      ``` 
      java -jar web-crawler-1.0.jar 
      ```
2. Execute via line arguments:
      ```
      -> java -jar web-crawler-1.0.jar <url> <number>
            url                     : URL of the website [Eg: http(s)://www.google.com]
            number(Optional)        : Number of top frequent words/word pair, if not provided by default 10 is considered.
      ```
        
  
# Limitations:
1. Does not work with JavaScript loaded content of the page.
