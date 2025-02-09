class Solution {
    public long countSubstrings(String s) {
        int n = s.length();
        long ans = 0;
        int[] P3 = new int[n], P7 = new int[n], P9 = new int[n];
        P3[0] = (s.charAt(0) - '0') % 3;
        P7[0] = (s.charAt(0) - '0') % 7;
        P9[0] = (s.charAt(0) - '0') % 9;
        for (int i = 1; i < n; i++) {
            int dig = s.charAt(i) - '0';
            P3[i] = (P3[i - 1] * 10 + dig) % 3;
            P7[i] = (P7[i - 1] * 10 + dig) % 7;
            P9[i] = (P9[i - 1] * 10 + dig) % 9;
        }
        long[] freq3 = new long[3], freq9 = new long[9];
        long[][] freq7 = new long[6][7];
        int[] inv7 = {1, 5, 4, 6, 2, 3};
        for (int j = 0; j < n; j++) {
            int d = s.charAt(j) - '0';
            if (d == 0) {
                // Skip 0 as last digit
            } else if (d == 1 || d == 2 || d == 5)
                ans += (j + 1);
            else if (d == 4) {
                if (j == 0) ans += 1;
                else {
                    int num = (s.charAt(j - 1) - '0') * 10 + d;
                    ans += (num % 4 == 0 ? (j + 1) : 1);
                }
            } else if (d == 8) {
                if (j == 0) ans += 1;
                else if (j == 1) {
                    int num = (s.charAt(0) - '0') * 10 + 8;
                    ans += (num % 8 == 0 ? 2 : 1);
                } else {
                    int num3 = (s.charAt(j - 2) - '0') * 100 + (s.charAt(j - 1) - '0') * 10 + 8;
                    int num2 = (s.charAt(j - 1) - '0') * 10 + 8;
                    ans += ((num3 % 8 == 0 ? (j - 1) : 0) + (num2 % 8 == 0 ? 1 : 0) + 1);
                }
            } else if (d == 3 || d == 6)
                ans += (P3[j] == 0 ? 1L : 0L) + freq3[P3[j]];
            else if (d == 7) {
                ans += (P7[j] == 0 ? 1L : 0L);
                for (int m = 0; m < 6; m++) {
                    int idx = ((j % 6) - m + 6) % 6;
                    int req = (P7[j] * inv7[m]) % 7;
                    ans += freq7[idx][req];
                }
            } else if (d == 9)
                ans += (P9[j] == 0 ? 1L : 0L) + freq9[P9[j]];
            freq3[P3[j]]++;
            freq7[j % 6][P7[j]]++;
            freq9[P9[j]]++;
        }
        return ans;
    }
}