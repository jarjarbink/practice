package practice;

import practice.processor.IProcessor;
import practice.util.MetaCharHelper;
import practice.validator.IValidator;

/**
 * Created by barry on 2017/11/2.
 */
public class StringAccumulator {

    public static final String PROTOCOL_PIPE = "|";
    public static final String PROTOCOL_PREFIX = "//";
    public static final String DEFAULT_DELIMITER = ",";
    private IValidator validator;
    private IProcessor processor;

    public StringAccumulator(IValidator validator, IProcessor processor){
        this.validator = validator;
        if(processor == null){
            throw new IllegalArgumentException("Processor can not be null!");
        }

        this.processor = processor;
    }

    public Integer add(final String numbers) throws Exception{

        String defaultLineBreakerDelimiter = "\\n";
        String textToProcess = numbers;
        String delimiters = defaultLineBreakerDelimiter;
        if(numbers.startsWith(PROTOCOL_PREFIX)){
            String[] split = numbers.split(defaultLineBreakerDelimiter, 2);
            String customizedDelimiter = split[0].substring(PROTOCOL_PREFIX.length());
            delimiters = delimiters + PROTOCOL_PIPE + MetaCharHelper.escapeForSplitDelimiter(customizedDelimiter);
            textToProcess = split[1];
        }else{
            delimiters = delimiters + PROTOCOL_PIPE + DEFAULT_DELIMITER;
        }
        if(this.validator != null) {
            this.validator.validate(textToProcess, delimiters);
        }

        return processor.sum(textToProcess, delimiters);
    }
}
