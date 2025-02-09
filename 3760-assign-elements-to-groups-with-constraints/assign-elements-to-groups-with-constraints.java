class Solution {
    public int[] assignElements(int[] g, int[] e) {
        int n = g.length;
        int m = e.length;
        int[] ans = new int[n];
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < m; i++) {
            if(!map.containsKey(e[i])) {
                map.put(e[i], i);
            }
        }
        for(int i = 0; i < n; i++) {
            ans[i] = fun(g[i], map);
        }
        return ans;
    }
    static int fun(int num, HashMap<Integer, Integer> map) {
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i * i <= num; i++) {
            if(num % i == 0) {
                list.add(i);
                list.add(num / i);
            }
        }
        int index = 0;
        int min = Integer.MAX_VALUE;
        while(index < list.size()) {
            int fac = list.get(index);
            if(map.containsKey(fac)) {
                min = Math.min(min, map.get(fac));
            }
            index++;
        }
        if(min == Integer.MAX_VALUE) return -1;
        return min;
    }
}