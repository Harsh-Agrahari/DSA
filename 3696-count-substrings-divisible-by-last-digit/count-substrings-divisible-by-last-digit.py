class Solution:
    def countSubstrings(self, s: str) -> int:
        always_valid = set("125")
        sum_valid = set("369")
        # The remaining digit (aside from 0) is '7'.
        
        n = len(s)
        res = 0
        
        # For the "sum" based cases (divisibility by 3 or 9), we maintain running sums
        # (Since 10 ≡ 1 mod 3 and mod 9, these are just the digit sums)
        sum3 = 0
        sum9 = 0
        
        # For the 7-case we keep a running prefix modulo 7.
        # (Note that 10 ≡ 3 mod 7 so we update prefix7 = (prefix7*10 + d) mod 7.)
        prefix7 = 0
        
        # Frequency dictionaries for prefix–sums:
        freq3 = defaultdict(int)
        freq9 = defaultdict(int)
        freq7 = defaultdict(int)
        
        # Initial “prefix” (for j = -1) is defined as 0.
        freq3[0] = 1
        freq9[0] = 1
        freq7[0] = 1  # norm[-1] is 0.
        
        # Precompute inverses for powers of 3 modulo 7.
        # Since 10 mod 7 = 3 and the powers of 3 modulo 7 cycle every 6:
        #   3^0 = 1, 3^1 = 3, 3^2 = 2, 3^3 = 6, 3^4 = 4, 3^5 = 5, 3^6 = 1, ...
        # Their inverses modulo 7 are:
        #   1 -> 1, 3 -> 5, 2 -> 4, 6 -> 6, 4 -> 2, 5 -> 3.
        inv_map = {0: 1, 1: 5, 2: 4, 3: 6, 4: 2, 5: 3}  # key: exponent mod 6
        
        for i, ch in enumerate(s):
            d = int(ch)
            # Update running prefix sums.
            sum3 = (sum3 + d) % 3
            sum9 = (sum9 + d) % 9
            prefix7 = (prefix7 * 10 + d) % 7
            # Compute normalized prefix value for mod7.
            # We need the inverse of 3^(i+1) mod 7.
            rem = (i + 1) % 6
            norm7 = (prefix7 * inv_map[rem]) % 7
            
            # Now count based on the last digit:
            if ch in always_valid:
                # For 1,2,5 every substring ending here is valid.
                count = i + 1
                res += count
            elif ch in sum_valid:
                # For 3 and 6, divisibility is determined by digit sum mod 3.
                # For 9, use mod 9.
                if ch in {'3', '6'}:
                    count = freq3[sum3]
                else:  # ch == '9'
                    count = freq9[sum9]
                res += count
            elif ch in {'4','8'}:
                # For 4 and 8, we only need to inspect the last 2 (or 3) digits.
                if ch == '4':
                    if i == 0:
                        count = 1
                    else:
                        # Check the two-digit number (s[i-1:i+1]).
                        two_digit = int(s[i-1:i+1])
                        if two_digit % 4 == 0:
                            # If the last two digits pass, then every substring ending at i
                            # of length ≥2 (i substrings) is valid, plus the single–digit substring.
                            count = i + 1
                        else:
                            count = 1
                    res += count
                else:  # ch == '8'
                    # Always count the single–digit substring.
                    count = 1
                    # For length 2:
                    if i >= 1:
                        two_digit = int(s[i-1:i+1])
                        if two_digit % 8 == 0:
                            count += 1
                    # For length ≥3: the divisibility depends only on the last 3 digits.
                    if i >= 2:
                        three_digit = int(s[i-2:i+1])
                        if three_digit % 8 == 0:
                            # All substrings ending at i with length ≥3 (there are i-1 such substrings).
                            count += (i - 1)
                    res += count
            else:
                # This branch is for the remaining digit: "7" (or "0")
                if ch == '0':
                    # Division by zero is not allowed.
                    count = 0
                else:  # ch == '7'
                    # Use the normalized prefix technique.
                    count = freq7[norm7]
                res += count
            
            # update all frequency dictionaries with the current prefix.
            freq3[sum3] += 1
            freq9[sum9] += 1
            freq7[norm7] += 1
            
        return res