class Calculator:
    current = 0

    def add(self, number):
        self.current += number

    def multiply(self, number):
        self.current *= number

    def exponentiate(self, power):
        base = # TODO: set the exponentiation `base`, it should be equal to the current value.
        for i in range(power - 1):
            # TODO: use the `multiply` method with the parameter `base` to calculate the result.

    def get_current(self):
        return self.current


calc = Calculator()  # Initialize calculator.
print(calc.get_current())  # Make sure the start value is 0.

calc.add(2)  # Add 2.
print(calc.get_current())  # Print the result, it should be 2.

calc.multiply(3)  # Multiply by 3.
print(calc.get_current())  # Print the result, it should be 6.

calc.exponentiate(3)  # Raise to the power of 3.
print(calc.get_current())  # Print the result, it should be 216.
