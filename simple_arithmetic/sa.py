"""
Tired of summing up numbers by hand for your night job as an accountant, you are
writing your own calculator that allows you to type in a lot of numbers and sums
them for you. Naturally, your calculator begins with a sum of 0.

Every time you reach an input value of 0 output the sum so far, and reset the
sum to 0.
"""

#import sys, re
#import sys as s, re as r
from sys import stdin as s
from re import findall as f, split as sp

# Original
#print '\n'.join([str(ans) for ans in [sum(x) for x in [map(int, v) for v in [line.split() for line in re.findall('.*?0', ' '.join(re.split('\\s', ' '.join(sys.stdin.readlines()))))]]]])

# Better
#print '\n'.join([str(sum(map(int, line.split()))) for line in re.findall('.*?0', ' '.join(re.split('\\s', sys.stdin.read())))])

# Betterer
#print '\n'.join([str(sum(map(int, line.split()))) for line in r.findall('.*?0', ' '.join(s.stdin.read().split('\\s')))])

# Cheat on those imports!
print '\n'.join([str(sum(map(int, l.split()))) for l in f('.*?0', ' '.join(sp('\\s', s.read())))])
