import functions.goodbye as bye
import functions.greeting.hello
from classes import calculator
# Import the `official` module here


print(functions.greeting.hello.hello('Susan'))
print('Say goodbye to Alex')

c = calculator.Calculator()
c.add(2)
c.multiply(10)
print(c.get_current())

print(official.hello('Sam'))
