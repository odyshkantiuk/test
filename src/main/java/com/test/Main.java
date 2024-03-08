package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // input file path
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file path: ");
        String filePath = scanner.nextLine();

        // check if the file exists
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("The file does not exist");
        }
        try {
            // read numbers from the file
            List<Integer> numbers = readNumbersFromFile(filePath);

            // check if the numbers exist
            if (numbers.isEmpty()) {
                System.out.println("The file is empty");
                return;
            }

            long startTime = System.currentTimeMillis();

            // finding the min and max number
            int max = Collections.max(numbers);
            int min = Collections.min(numbers);

            // finding the median
            double median = findMedian(numbers);

            // finding the arithmetic mean
            double mean = findArithmeticMean(numbers);

            // finding the largest sequence of numbers that increases
            List<Integer> increasingSequence = findLongestSequence(numbers, true);

            // finding the largest sequence of numbers that decreases
            List<Integer> decreasingSequence = findLongestSequence(numbers, false);

            long endTime = System.currentTimeMillis();
            double totalTime = (endTime - startTime) / 1000.0;

            System.out.println("Max number: " + max);
            System.out.println("Min number: " + min);
            System.out.println("Median: " + median);
            System.out.println("Arithmetic mean: " + mean);
            System.out.println("Largest increasing sequence: " + increasingSequence);
            System.out.println("Largest descending sequence: " + decreasingSequence);
            System.out.println("Total time: " + totalTime);
        } catch (IOException e) {
            System.out.println("Error reading from file - " + e.getMessage());
        }
    }

    public static List<Integer> readNumbersFromFile(String fileName) throws IOException {
        List<Integer> numbers = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = br.readLine()) != null) {
            try {
                int num = Integer.parseInt(line);
                numbers.add(num);
            } catch (NumberFormatException e) {
                System.out.println("An invalid number was found in the file - " + line);
            }
        }
        return numbers;
    }

    public static double findMedian(List<Integer> numbers) {
        double median;
        List<Integer> sortedNumbers = new ArrayList<>(numbers);
        Collections.sort(sortedNumbers);
        int size = sortedNumbers.size();
        if (size % 2 == 0) {
            median = (double) (sortedNumbers.get(size / 2) + sortedNumbers.get(size / 2 - 1)) / 2;
        } else {
            median = sortedNumbers.get(size / 2);
        }
        return median;
    }

    public static double findArithmeticMean(List<Integer> numbers) {
        double sum = 0;
        int size = numbers.size();
        for (int num : numbers) {
            sum += num;
        }
        return sum / size;
    }

    private static List<Integer> findLongestSequence(List<Integer> numbers, boolean increasing) {
        List<Integer> sequence = new ArrayList<>();
        List<Integer> longestSequence = new ArrayList<>();
        for (int i = 0; i < numbers.size() - 1; i++) {
            int current = numbers.get(i);
            int next = numbers.get(i + 1);
            boolean condition = increasing ? current < next : current > next;
            sequence.add(numbers.get(i));
            if (!condition) {
                if (sequence.size() > longestSequence.size()) {
                    longestSequence = new ArrayList<>(sequence);
                }
                sequence.clear();
            }
        }
        sequence.add(numbers.get(numbers.size() - 1));
        if (sequence.size() > longestSequence.size()) {
            longestSequence = new ArrayList<>(sequence);
        }
        return longestSequence;
    }
}