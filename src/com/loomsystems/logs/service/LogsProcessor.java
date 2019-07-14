package com.loomsystems.logs.service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LogsProcessor implements ILogsProcessor {
    private static final int INDEX_OF_DATE = Integer.valueOf(0);
    private static final int INDEX_OF_TIME = Integer.valueOf(1);
    private static final int INDEX_OF_NAME = Integer.valueOf(2);
    private static final int INDEX_OF_PATTERN_BEGINNING = Integer.valueOf(3);
    private static final int ARRAY_LENGTH = Integer.valueOf(4);
    private static final String DATE_REGEX = "(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[012])-((19|20)\\d\\d)";
    private static final String TIME_REGEX = "([2][0-3]|[0-1][0-9]|[1-9]):[0-5][0-9]:([0-5][0-9]|[6][0])";

    private Map<String, ArrayList<String[]>> patternMap = new HashMap<>();
    private List<String> wrongFormatlines = new ArrayList<>();
    private Set<String> nameSet = new HashSet<>();
    private List<String> resultList = new ArrayList<>();

    @Override
    public void processLine(String logLine) {
        String[] parts = logLine.split(" +", 4);
        if (isNotValidLine(parts)) {
            return;
        }
        patternMap.computeIfAbsent(parts[INDEX_OF_PATTERN_BEGINNING], arraylistString->new ArrayList<String[]>())
                .add(Arrays.copyOfRange(parts, INDEX_OF_DATE, INDEX_OF_PATTERN_BEGINNING));
    }

    private boolean isNotValidLine(String[] parts) {
        if (parts.length == ARRAY_LENGTH && Pattern.matches(DATE_REGEX, parts[INDEX_OF_DATE]) && Pattern.matches(TIME_REGEX,
                parts[INDEX_OF_TIME])) {
            return false;
        }
        saveWrongLine(parts);
        return true;
    }

    private void saveWrongLine(String[] parts) {
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(parts).forEach(word -> stringBuilder.append(word).append(" "));
        wrongFormatlines.add(stringBuilder.toString().trim());
    }

    @Override
    public List<String> getResults() {
        patternMap.entrySet().forEach(patternWithEvents -> getFormattedStringsFromMap(patternWithEvents));
        return resultList;
    }

    private void getFormattedStringsFromMap(Map.Entry<String, ArrayList<String[]>> patternWithEvents) {
        nameSet.clear();
        patternWithEvents.getValue().stream()
                .forEach(events -> {
                    StringBuilder stringBuilder = new StringBuilder();
                    nameSet.add(events[INDEX_OF_NAME]);
                    resultList.add(stringBuilder.append(events[INDEX_OF_DATE]).append(" ")
                            .append(events[INDEX_OF_TIME]).append(" ").append(events[INDEX_OF_NAME])
                            .append(" ") + patternWithEvents.getKey());
                });
        resultList.add("The pattern is: [X] " + patternWithEvents.getKey());
        resultList.add("The changing word was: " + nameSet.stream()
                .collect(Collectors.joining(", ")));
        resultList.add("\n");
    }

    @Override
    public List<String> getWrongFormatLines() {
        return wrongFormatlines;
    }

    public Map<String, ArrayList<String[]>> getPatternMap() {
        return patternMap;
    }
}
