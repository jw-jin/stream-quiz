package com.mangkyu.stream.Quiz1;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Quiz1 {

    // 1.1 각 취미를 선호하는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz1() throws IOException {
        List<String[]> csvLines = readCsvLines();
        // System.out.println("csvLines[0] = " + Arrays.toString(csvLines.get(0)));
        // Map<String, Integer> hobbyCount = csvLines.stream()
        //     .flatMap(line -> Arrays.stream(line[1].replaceAll("\\s","").split(":")))
        //     .sorted()
        //     .collect(Collectors.groupingBy(
        //         Function.identity(),
        //         TreeMap::new,
        //         Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
        //     ));
        //
        //
        // System.out.println("hobbyCount = " + hobbyCount);


        // csvLines.stream()
        //     .forEach(csvLine -> {
        //         for (String line: csvLine) {
        //             System.out.println("line = " + line);
        //         }
        //     });
        return csvLines.stream()
            .flatMap(line -> Arrays.stream(line[1].replaceAll("\\s","").split(":")))
            .collect(Collectors.groupingBy(
                Function.identity(),
                TreeMap::new,
                Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
            ));
    }

    // 1.2 각 취미를 선호하는 정씨 성을 갖는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz2() throws IOException {
        List<String[]> csvLines = readCsvLines();

        return csvLines.stream()
            .filter(line -> line[0].startsWith("정"))
            .flatMap(line -> Arrays.stream(line[1].replaceAll("\\s","").split(":")))
            .collect(Collectors.groupingBy(
                Function.identity(),
                TreeMap::new,
                Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
            ));
    }

    // 1.3 소개 내용에 '좋아'가 몇번 등장하는지 계산하여라.
    public int quiz3() throws IOException {
        List<String[]> csvLines = readCsvLines();

        return csvLines.stream()
            .filter(line -> line[2].contains("좋아"))
            // (기존 스트링 길이 - 특정 문자열 지운 뒤 스트링 길이) / 특정 문자열 길이
            .mapToInt(line -> (line[2].length() - line[2].replace("좋아", "").length()) / 2)
            .sum();
    }

    private List<String[]> readCsvLines() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(getClass().getResource("/user.csv").getFile()));
        csvReader.readNext();
        return csvReader.readAll();
    }

}
