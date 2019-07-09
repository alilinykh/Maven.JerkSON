package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ItemParser {
    public List<Item> parseItemList(String valueToParse) {
        List<Item> rslt = new ArrayList<>();
        List<String> sepItems = new ArrayList<>();

        String regex = "[^##]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(valueToParse);

        while (matcher.find()) {
            sepItems.add(matcher.group(0));
        }
        for ( String s : sepItems
             ) {
            try {
                rslt.add(parseSingleItem(s));
            } catch (ItemParseException e) {
                e.printStackTrace();
            }
        }


        return rslt;
    }

    public Item parseSingleItem(String singleItem) throws ItemParseException {
        String string = replaceValuesToAt(singleItem);
        String nameValue = null;
        Double priceValue = null;
        String typeValue = null;
        String expirationValue = null;
        List<String> keyValuePairs = new ArrayList<>();

        String regex1 = "[^;]+";
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher = pattern1.matcher(string);
        while (matcher.find()) {
            keyValuePairs.add(matcher.group(0));
        }

        for (String s : keyValuePairs
        ) {
            String reg = "[^@]*";
            Pattern pa = Pattern.compile(reg);
            Matcher matcher1 = pa.matcher(s);

            while (matcher1.find()) {
                switch (matcher1.group(0).toLowerCase()) {
                    case "name": {
                        nameValue = getInfo(s);
                        if (nameValue == null) {throw new ItemParseException();}
                        break;
                    }
                    case "price": {
                        priceValue = Double.parseDouble(getInfo(s));
                        if (priceValue == null) {throw new ItemParseException();}
                        break;
                    }
                    case "type": {
                        typeValue = getInfo(s);
                        if (typeValue == null) {throw new ItemParseException();}
                        break;
                    }
                    case "expiration": {
                        expirationValue = getInfo(s);
//                        if (expirationValue.contains("##")
                        if (expirationValue == null) {throw new ItemParseException();}
                        break;
                    }
                }
            }
        }


        return new Item(nameValue, priceValue, typeValue, expirationValue);
    }

    public static String replaceValuesToAt(String string) {
        String regex = "[:|*|^|%]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            string = matcher.replaceAll("@");
        }
        return string;
    }


    public static String getInfo(String string) {

        final String reg = "@(.*)"; //  -----  ([\w]*)$
        final Pattern pa = Pattern.compile(reg);
        Matcher matcher1 = pa.matcher(string);
        while (matcher1.find()) return matcher1.group(1).toLowerCase();
        return null;
    }
//
//    public static Double getPrice(String string) {
//        final String reg = ""; //  -----  ([\w]*)$
//        final Pattern pa = Pattern.compile(reg);
//        Matcher matcher1 = pa.matcher(string);
//        while (matcher1.find()) return matcher1.group(0);
//        return null;
//    }
//
//    public static String getExpiration(String string) {
//        final String reg = "[\\w]*$"; //  -----  ([\w]*)$
//        final Pattern pa = Pattern.compile(reg);
//        Matcher matcher1 = pa.matcher(string);
//        while (matcher1.find()) return matcher1.group(0).toLowerCase();
//        return null;
//    }
}
