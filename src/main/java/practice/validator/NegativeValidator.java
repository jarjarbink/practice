package practice.validator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NegativeValidator implements IValidator {

    private IValidator validator;

    public NegativeValidator(IValidator validtor){
        this.validator = validtor;
    }

    @Override
    public void validate(String numbers, String delimiter) throws Exception {

        if(this.validator != null){
            this.validator.validate(numbers, delimiter);
        }

        List<Integer> negativeNumbers = Arrays.stream(numbers.split(delimiter)).
                filter((s -> ( s != null && !s.trim().isEmpty()))).
                map(Integer::parseInt).
                filter((i) -> (i<0)).
                collect(Collectors.toList());
        if(negativeNumbers != null && negativeNumbers.size() > 0){
            throw new RuntimeException("negatives not allowed : " + negativeNumbers);
        }

    }
}
