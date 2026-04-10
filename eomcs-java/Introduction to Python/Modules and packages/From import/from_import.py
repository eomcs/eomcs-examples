# Import class Calculator from another module
from my_module import hello as hey

print(hey("User"))

calc = 'Instantiate a calculator'  # Name `Calculator` used directly without prefix `calculator`
calc.add(2)
calc.multiply(100)
calc.divide(3)

print(calc.get_current())



