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

        String regex = "(.*?##)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(valueToParse);

        while (matcher.find()) {
            sepItems.add(matcher.group(1));
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
        Item rslt =null;
        String string = replaceWith(singleItem,"[:|*|^|%]","@");
        String nameValue = null;
        Double priceValue = 0.0;
        String typeValue = "";
        String expirationValue = "";
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
                        String value = getInfo(s);
                        if (value == null) {throw new ItemParseException();}
                        else {
                            priceValue = Double.parseDouble(value);
                        }
                        break;
                    }
                    case "type": {
                        typeValue = getInfo(s);
                        if (typeValue == null) {throw new ItemParseException();}
                        break;
                    }
                    case "expiration": {
                        String value = getInfo(s);
                        if (value == null) {throw new ItemParseException();}
                        else {
                            expirationValue = replaceWith(getInfo(s),"(##)","");
                        }
                        break;
                    }
                }
            }
            rslt = new Item(nameValue, priceValue, typeValue, expirationValue);
            if (rslt.getName() == null) { break;}
        }

        return rslt;
    }

    public static String replaceWith(String string, String regex, String replaceWith) {
        String r = regex; //"[:|*|^|%]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            string = matcher.replaceAll(replaceWith);
        }
        return string;
    }


    public static String getInfo(String string) {

        String reg = "@(.*)";
        Pattern pa = Pattern.compile(reg);
        Matcher matcher1 = pa.matcher(string);
        while (matcher1.find()) return matcher1.group(1).toLowerCase();
        return null;
    }
}
