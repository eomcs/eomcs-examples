# immutable 객체의 값 변경?
# - immutable 객체에 저장된 값은 변경할 수 없지만,
#   immutable 객체의 값이 참조하는 객체가 mutable 객체인 경우 변경할 수 있다.

# tuple
a = (
    'Hello',
    [10, 20, 30],
)

# immutable 객체에 저장된 값(객체 주소)은 변경할 수 없다.
# a[0] = 'World!'
# a[1] = [40, 50, 60]

# immutable에 저장된 객체 참조가 가리키는 것이 immutable인 경우
# a[0][0] = 'X' # 변경 불가!

# immutable에 저장된 객체 참조가 가리키는 것이 mutable 인 경우
a[1][2] = 55

print(a)