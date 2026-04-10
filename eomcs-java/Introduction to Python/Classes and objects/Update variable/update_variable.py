class Car:
    color = ""

    def description(self):  # we'll explain the self parameter later
        description_string = f"This is a {self.color} car."
        return description_string


car1 = Car()
car2 = ???

car1.color = "blue"
car2.color = ???

print('Call description() method of car1')
print('Call description() method of car2')
