package practice.util;

import java.util.Arrays;
import java.util.regex.Matcher;

/**
 * Created by barry on 2017/11/2.
 */
public final class MetaCharHelper {

    private final static String[] META_CHARS ={"\\\\","\\+","\\^","\\$","\\.","\\?","\\*","\\(","\\)","\\[","\\{"};

    public static String escapeForSplitDelimiter(final String splitMode){
        final String[] result = new String[1];
        result[0] = splitMode;
        if(splitMode != null && !splitMode.trim().isEmpty()){
            Arrays.stream(META_CHARS).forEach((s) -> {
                result[0] = result[0].replaceAll(s, Matcher.quoteReplacement(s));
            });
        }
        return result[0];
    }

}
