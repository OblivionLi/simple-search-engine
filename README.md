# simple-search-engine
### The application is a straightforward search engine that looks for words, characters, and other data from a file. It employs an inverted index data structure to enhance its performance. The application showcases various algorithms for searching specific terms, utilizing three distinct strategies:
---
##### `ALL` -> If the strategy is ALL, the program should print lines containing all the words from the query.
##### `ANY` -> If the strategy is ANY, the program should print the lines containing at least one word from the query.
##### `NONE` -> If the strategy is NONE, the program should print lines that do not contain words from the query at all.
---
###### Example (`>` is user input):
---
```
=== Menu ===
1. Find a person
2. Print all persons
0. Exit
> 1

Select a matching strategy: ALL, ANY, NONE
> ANY

Enter a name or email to search all suitable people.
> Katie Erick QQQ

3 persons found:
Katie Jacobs
Erick Harrington harrington@gmail.com
Erick Burgess
```
