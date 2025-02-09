class Solution {
    public long maxScore(int[] pts, int M) {
        int n = pts.length;
        if (M < n) return 0;
        long lo = 1, hi = (long)1e18, ans = 0;
        while (lo <= hi) {
            long mid = lo + (hi - lo) / 2;
            long tot = 0, tr = 0, skip = 0;
            for (int i = 0; i < n && tot <= M; i++) {
                int p = pts[i];
                long need = (mid + p - 1L) / p;
                if (tr >= need) {
                    tr = 0;
                    skip++;
                } else {
                    long cur = tr * (long)p;
                    long ops = (mid - cur + p - 1L) / p;
                    tot += 2 * ops - 1 + skip;
                    tr = Math.max(ops - 1, 0);
                    skip = 0;
                }
            }
            if (tot <= M) { ans = mid; lo = mid + 1; }
            else hi = mid - 1;
        }
        return ans;
    }
}