"""
Tired of summing up numbers by hand for your night job as an accountant, you are
writing your own calculator that allows you to type in a lot of numbers and sums
them for you. Naturally, your calculator begins with a sum of 0.
"""

#import sys, re
import sys as s, re as r

# Original
#print '\n'.join([str(ans) for ans in [sum(x) for x in [map(int, v) for v in [line.split() for line in re.findall('.*?0', ' '.join(re.split('\\s', ' '.join(sys.stdin.readlines()))))]]]])

# Better
#print '\n'.join([str(sum(map(int, line.split()))) for line in re.findall('.*?0', ' '.join(re.split('\\s', sys.stdin.read())))])

# Betterer
print '\n'.join([str(sum(map(int, line.split()))) for line in r.findall('.*?0', ' '.join(s.stdin.read().split('\\s')))])
