package practice.processor;

import java.util.Arrays;

public class DefaultProcessor implements IProcessor{

    @Override
    public Integer sum(String numbers, String delimiter) {
        return Arrays.stream(numbers.split(delimiter)).
                filter((s -> ( s != null && !s.trim().isEmpty()))).
                map(Integer::parseInt).
                filter((i) -> (i<1000)).
                mapToInt(Integer::intValue).
                sum();
    }
}
