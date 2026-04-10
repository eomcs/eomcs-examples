class City:
    # TODO: add a mutable class variable (all_cities list) to store all added city names

    def __init__(self, name, population, country):
        self.name = name
        self.population = population
        self.country = country
        # TODO: call add_city method to add the city name to the list upon object initialization

    def add_city(self):
        pass
        # TODO: implement a method that will append a city name to the all_cities list. Delete the pass statement!


if __name__ == '__main__':
    malaga = City('Malaga', 569005, 'Spain')
    boston = City('Boston', 689326, 'USA')
    beijing = City('Beijing', 21540000, 'China')

    print(malaga.all_cities)  # This should print "['Malaga', 'Boston', 'Beijing']".
