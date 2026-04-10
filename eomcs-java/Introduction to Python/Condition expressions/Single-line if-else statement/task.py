from random import random  # Importing a pseudo-random number generator module

my_random_number = random() * 100

if my_random_number > 50:
    print(my_random_number)
else:
    print("Too small!")
