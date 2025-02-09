class Solution 
{
    public int[][] sortMatrix(int[][] grid) 
    {
        int n = grid.length;
        Map<Integer, List<Integer>> d = new HashMap<>();
        for (int i = 0; i < n; i++) 
        {
            for (int j = 0; j < n; j++) 
            {
                int key = i - j;
                d.putIfAbsent(key, new ArrayList<>());
                d.get(key).add(grid[i][j]);
            }
        }
        for (int key : d.keySet()) 
        {
            List<Integer> values = d.get(key);
            if (key >= 0) 
            {
                values.sort(Collections.reverseOrder()); 
            }
            else 
            {
                Collections.sort(values);
            }
        }
        for (int i = 0; i < n; i++) 
        {
            for (int j = 0; j < n; j++) 
            {
                grid[i][j] = d.get(i - j).remove(0);
            }
        }

        return grid;
    }
}