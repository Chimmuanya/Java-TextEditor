package editor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Findtext {
    void startFind(String text, String pattern);
    int find();
    int findNext();
    int findPrevious();
    public int getPatternLength();

}

class SimpleFind implements Findtext{
    static int counter = 0;
    List<Integer> indexes = new ArrayList<>();
    int patternLen;
    @Override
    public void startFind(String text, String pattern) {
        counter = 0;
        patternLen = pattern.length();
        String current = text;
        indexes.clear();

        int base = 0;
        while (current.contains(pattern)){
            int index = current.indexOf(pattern);
            indexes.add(index + base);
            base += index + patternLen;
            current = current.substring(index + patternLen);

        }


    }

    @Override
    public int find() {
        if (!indexes.isEmpty()){
            return indexes.get(0);
        }
        return -1;
    }


    public int findNext() {
        counter ++;
        if (!indexes.isEmpty()){
            if (counter < indexes.size()){
                return indexes.get(counter);
            } else if (counter == indexes.size()){
                counter = 0;
                return indexes.get(counter);
            }

        }
        return -1;
    }

    public int findPrevious() {
        counter--;
        if (!indexes.isEmpty()) {
            if (counter >= 0){
                return indexes.get(counter);
            } else if (counter < 0) {
                counter = indexes.size() -1;
                return indexes.get(counter);
            }

        }
        return -1;
    }

    @Override
    public int getPatternLength() {
        return patternLen;
    }

    
}

class RegexFind implements Findtext{
    static int counter = 1;
    List<int[]> indexes = new ArrayList<>();

    @Override
    public void startFind(String text, String regex) {
        counter = 0;
        Pattern pattern1 = Pattern.compile(regex);
        indexes.clear();
        int index;
        int regLength;
        Matcher matcher = pattern1.matcher(text);
        while (matcher.find()) {
            int[] regIndex = new int[3];
            index = matcher.start();
            regLength = matcher.group().length();
            regIndex[0] = index;
            regIndex[1] = regLength;
            indexes.add(regIndex);
        }

    }

    public int getPatternLength(){
        if (!indexes.isEmpty()){
            return indexes.get(counter)[1];
        }
        return 0;
    }

    public int find() {
        if (!indexes.isEmpty()){
            return indexes.get(0)[0];
        }
        return -1;
    }

    @Override
    public int findNext() {
        counter ++;
        if (!indexes.isEmpty()){
            if (counter < indexes.size()){
                return indexes.get(counter)[0];
            } else if (counter == indexes.size()){
                counter = 0;
                return indexes.get(counter)[0];
            }

        }
        return -1;
    }

    @Override
    public int findPrevious() {
        counter--;
        if (!indexes.isEmpty()) {
            if (counter >= 0){
                return indexes.get(counter)[0];
            } else if (counter < 0) {
                counter = indexes.size() -1;
                return indexes.get(counter)[0];
            }

        }
        return -1;
    }

    
}
