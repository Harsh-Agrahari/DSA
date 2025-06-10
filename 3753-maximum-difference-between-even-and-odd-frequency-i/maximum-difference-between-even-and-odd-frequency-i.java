import java.util.ArrayList;
import java.util.List;

class Solution {
    public int maxDifference(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }
        
        List<Integer> evens = new ArrayList<>();
        List<Integer> odds = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            if (freq[i] > 0) {
                if (freq[i] % 2 == 0) {
                    evens.add(freq[i]);
                } else {
                    odds.add(freq[i]);
                }
            }
        }
        
        int maxDiff = Integer.MIN_VALUE;
        for (int o : odds) {
            for (int e : evens) {
                int diff = o - e;
                if (diff > maxDiff) {
                    maxDiff = diff;
                }
            }
        }
        
        return maxDiff;
    }
}

