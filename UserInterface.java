package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class UserInterface {
    private final Scanner scanner;
    private final List<String> peopleList;
    private final String filename;
    private final Map<String, Set<Integer>> invertedIndex;

    public UserInterface(Scanner scanner, String filename) {
        this.scanner = scanner;
        this.filename = filename;
        this.peopleList = new ArrayList<>();
        this.invertedIndex = new HashMap<>();
    }

    public void boot() {
        List<String> peopleList = this.getPeopleList();

        while (true) {
            this.showMenu();
            int option = this.scanner.nextInt();
            this.scanner.nextLine(); // consume line

            if (option == 0) {
                System.out.println("\nBye!");
                break;
            }

            switch (option) {
                case 1 -> this.findPeople(peopleList);
                case 2 -> this.showAllPeople();
                default -> System.out.println("\nIncorrect option! Try again.");
            }
        }
    }

    private void showAllPeople() {
        System.out.println("\n=== List of people ===");
        for (String person : this.peopleList) {
            System.out.println(person);
        }
    }

    private void showMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }

    private List<String> getPeopleList() {
        File file = new File(this.filename);

        try (Scanner fileData = new Scanner(file)) {
            int lineIndex = 0;
            while (fileData.hasNextLine()) {
                String person = fileData.nextLine();
                if (person.isEmpty()) {
                    break;
                }

                this.peopleList.add(person);
                this.indexWords(person, lineIndex);
                lineIndex++;
            }

            return this.peopleList;
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return new ArrayList<>();
        }
    }

    private void indexWords(String line, int lineIndex) {
        String[] words = line.split(" ");
        for (String word : words) {
            word = word.toLowerCase().trim();
            this.invertedIndex.computeIfAbsent(word, k -> new HashSet<>()).add(lineIndex);
        }
    }

    private String getSearchTerm() {
        System.out.println("\nEnter a name or email to search all suitable people.");
        return this.scanner.nextLine();
    }

    private void findPeople(List<String> peopleList) {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategy = this.scanner.nextLine();

        String searchTerm = this.getSearchTerm();
        Set<Integer> foundLines;

        switch (strategy.toUpperCase()) {
            case "ALL" -> foundLines = this.findLinesContainingAllWords(searchTerm);
            case "ANY" -> foundLines = this.findLinesContainingAnyWord(searchTerm);
            case "NONE" -> foundLines = this.findLinesNotContainingAnyWord(searchTerm);
            default -> {
                System.out.println("Invalid strategy!");
                return;
            }
        }

        if (!foundLines.isEmpty()) {
            for (int lineIndex : foundLines) {
                System.out.println(peopleList.get(lineIndex));
            }

            return;
        }

        System.out.println("No matching people found.");
    }

    private Set<Integer> findLinesContainingAllWords(String searchTerm) {
        String[] words = searchTerm.toLowerCase().trim().split(" ");
        Set<Integer> foundLines = new HashSet<>(invertedIndex.getOrDefault(words[0], Collections.emptySet()));

        for (int i = 1; i < words.length; i++) {
            Set<Integer> linesWithWord = invertedIndex.getOrDefault(words[i], Collections.emptySet());
            foundLines.retainAll(linesWithWord);
        }

        return foundLines;
    }

    private Set<Integer> findLinesContainingAnyWord(String searchTerm) {
        String[] words = searchTerm.toLowerCase().trim().split(" ");
        Set<Integer> foundLines = new HashSet<>();

        for (String word : words) {
            Set<Integer> linesWithWord = invertedIndex.getOrDefault(word, Collections.emptySet());
            foundLines.addAll(linesWithWord);
        }

        return foundLines;
    }

    private Set<Integer> findLinesNotContainingAnyWord(String searchTerm) {
        Set<Integer> allLines = new HashSet<>();
        for (int i = 0; i < peopleList.size(); i++) {
            allLines.add(i);
        }

        Set<Integer> linesWithWords = findLinesContainingAnyWord(searchTerm);
        Set<Integer> linesWithoutWords = new HashSet<>(allLines);
        linesWithoutWords.removeAll(linesWithWords);

        return linesWithoutWords;
    }
}
