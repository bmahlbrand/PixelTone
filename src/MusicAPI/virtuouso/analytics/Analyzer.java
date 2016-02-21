package MusicAPI.virtuouso.analytics;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by ben on 2/21/2016.
 */
public class Analyzer {

    public static int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    //how similar are these phrases?
    double cosineSimilarity(List<String> phrase1, List<String> phrase2) {
        double cos = 0.0f;

        return cos;
    }

    //phrase entropy
    static double entropy(List<String> phrase) {
        double entropy = 0;

        Map<String, Integer> freq = new TreeMap<>();

        for (String str : phrase)
            freq.put(str, 0);

        for (String str : phrase) {
            int count = freq.containsKey(str) ? freq.get(str) : 0;
            freq.put(str, count + 1);
        }

        for (String sequence : freq.keySet()) {
            Double frequency = (double) freq.get(sequence) / freq.size();
            entropy -= frequency * (Math.log(frequency) / Math.log(2));
        }

        return entropy;
    }

    //calculates entropy difference by performing this operation (like adding removing notes from a phrase)
    double informationGain() {
        return 0.0f;
    }

    //3 dimensional melodic motion, can get more precise with 5 -- essentially pointless beyond that iirc
    //for instance D C A A B == *UURD -- good for comparing the tonal similarity of phrases despite different keys or notes
    //use with levenshtein distance function, generate two parsons code strings and use that w/a max edit distance of
    //say 10, 25, depending on the lengths of the phrases could be much larger
    public static String parsonsCode(List<String> phrase) {
        boolean skip = true;
        StringBuilder str = new StringBuilder();
        int prevTone = -1;
        for (String n : phrase) {
            int curTone = noteIndex(n, true);

            if(skip) {

                skip = false;
                str.append("*");
//                int tone = n.getScaleDegree();

                prevTone = curTone;

            } else {

                if(curTone == prevTone)
                    str.append("R");
                else if(curTone > prevTone)
                    str.append("U");
                else
                    str.append("D");

                prevTone = curTone;
            }
        }

        return str.toString();
    }

    //hamming weight or 'naive phrase similarity'
    public static int levenshtein(String q, String db, int maxEdits){
        int n = db.length();
        int m = q.length();

        int currRow[] = new int[n + 1];
        int prevRow[] = new int[n + 1];
        int tmp[];

        int ret = 0;

        for (int i = 1; i <= m; i++) {
            tmp = currRow;
            currRow = prevRow;
            prevRow = tmp;

            int min = currRow[0] = i;
            for(int j = 1; j <= n; j++) {
                if(q.charAt(i - 1) == (db.charAt(j - 1)))
                    ret = prevRow[j - 1];
                else
                    ret = min(currRow[j - 1], prevRow[j - 1], prevRow[j] ) + 1;

                if(ret < min)
                    min = ret;

                currRow[j] = ret;
            }

            if(min > maxEdits)
                return -1;
        }

        return ret;
    }
}
