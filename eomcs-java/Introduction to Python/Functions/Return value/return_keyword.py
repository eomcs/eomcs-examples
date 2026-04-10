def sum_two_numbers(a, b):
    return a + b  # Return result to the function caller


c = sum_two_numbers(3, 12)  # Assign result of function execution to variable 'c'


def fib(n):
    """This is documentation string for function. It'll be available by fib.__doc__()
    Return a list containing the Fibonacci series up to n."""
    result = []
    a = 1
    b = # Assign an initial value to the b variable
    while a < n:
        result.append(a)
        tmp_var = b
        # Update the b variable with a sum
        # Restore the old value of the b variable to the a variable from the temp
    # Here we need to return the result to the caller


if __name__ == '__main__':
    print(fib(10))
