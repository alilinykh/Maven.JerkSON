package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;
import io.zipcoder.utils.match.Match;
import javafx.scene.paint.Material;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ItemParser {
    public List<Item> parseItemList(String valueToParse) {
        List<Item> rslt = new ArrayList<>();
        List<String> sepItems = new ArrayList<>();

        final String regex = "[^\\##]+";
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(valueToParse);

        sepItems.add(matcher.group(0));
        for (int i = 1; i < matcher.groupCount(); i++) {
            sepItems.add(matcher.group(i));
        }

//        String[] itemValues = valueToParse.split("(\\##(?!$))");
//        for (String s: itemValues
//             ) {
//            String[] pairs = s.split("(\\;(!?))");
//            for (String s1: pairs
//                 ) {
////                rslt.add(parseSingleItem(s1));
//            }
//        }





        return rslt;
    }

    public Item parseSingleItem(String singleItem) throws ItemParseException {
        List<String>keyValuePairs = new ArrayList<>();
        final String regex1 = "[^\\;]+";
        final Pattern pattern1 = Pattern.compile(regex1);

        Matcher matcher = pattern1.matcher(singleItem);
        while (matcher.find()) {
            keyValuePairs.add(matcher.group(0));
        }
        for (String s: keyValuePairs
             ) {
            final String reg = "[^\\W]* + ([\\w]*)$";
            final Pattern pa = Pattern.compile(reg);
            Matcher matcher1 = pa.matcher(s);
            while (matcher1.find()) {
                if (matcher1.group(0).toLowerCase().equals("name")) {
                    String name = matcher1.group(1);
                }
            }




        }

        return null;
    }
}
