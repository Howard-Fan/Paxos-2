Introduction

You have been given a gift card that is about to expire and you want to buy gifts for 2 friends. You want to spend the whole gift card, or if that’s not an option as close to it as possible. You
have a list of sorted prices for a popular store that you know they both like to shop at. Your
challenge is to find two distinct items in the list whose sum is minimally under (or equal to) the
gift card balance.

The file contains two columns:

1. A unique identifier of the item. You can assume there are no duplicates.

2. The value of that item in dollars. It is always a positive integer that represents the price in
whole dollars (10 = $10.00 USD).

Write a program to find the best two items. It takes two inputs:

1. A filename with a list of sorted prices

2. The balance of your gift card

If no two items have a sum that is less than or equal to the balance on the gift card, print “Not
possible”. You don’t have to return every possible pair that is under the balance, just one such
pair.


Note: There may be many​ rows in the file, so be sure to optimize your solution to scale.

What is the big O notation for your program?
Assume we have I items, B budget, and P people. The program runs O(BPI^2)

Optional bonus​ (hard!): You are considering giving gifts to more people. Instead of choosing
exactly 2 items, allow for 1 or more gifts.

The program already taking into account of the bonus problem.
The question is a variation of the 0/1 Knapsack problem with a constrain on the number of items placed in the sack.
The approach is to use Dynamic Programming to achieve polynomial time complexity.
Since there is one extra constrain, the original 2D matrix is made into a 3D matrix to accommodate the variation.



