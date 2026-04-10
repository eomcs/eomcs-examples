def print_table(height, length=3, symbol='++++'):
    for y in range(height):
        for x in range(length):
            print(f'|{symbol}', end='')
        print('|\n')


print_table(length=5, 5, '____')
