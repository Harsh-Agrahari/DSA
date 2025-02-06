class Solution {
    public int tupleSameProduct(int[] nums) {
        int numsLength = nums.length;
        Arrays.sort(nums);
        int totalNumberOfTuples = 0;
        for (int aIndex = 0; aIndex < numsLength; aIndex++) {
            for (int bIndex = numsLength - 1; bIndex >= aIndex + 1; bIndex--) {
                int product = nums[aIndex] * nums[bIndex];
                Set<Integer> possibleDValues = new HashSet<>();
                for (int cIndex = aIndex + 1; cIndex < bIndex; cIndex++) {
                    if (product % nums[cIndex] == 0) {
                        int dValue = product / nums[cIndex];
                        if (possibleDValues.contains(dValue)) {
                            totalNumberOfTuples += 8;
                        }
                        possibleDValues.add(nums[cIndex]);
                    }
                }
            }
        }

        return totalNumberOfTuples;
    }
}