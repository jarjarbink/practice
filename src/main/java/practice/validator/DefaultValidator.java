package practice.validator;

public class DefaultValidator implements IValidator {

    @Override
    public void validate(String numbers, String delimiter) throws Exception {

        if(numbers != null && !numbers.trim().isEmpty()){
            boolean endWithBreak = numbers.endsWith("\n");
            if(endWithBreak){
                throw new RuntimeException("Ending with line break is not allowed.");
            }
        }
    }
}
