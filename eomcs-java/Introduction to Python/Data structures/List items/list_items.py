animals = ["elephant", "lion", "tiger", "giraffe", "monkey", "dog"]  # Create a new list
print(animals)

animals[1:3] = ["cat"]    # Replace 2 items -- "lion" and "tiger" with one item -- "cat"
print(animals)

animals[1:3] = []     # Remove 2 items -- "cat" and "giraffe" from the list
print(animals)

# Make all animals elephants by replacing the last two items.
print(animals)
