import random
import threading
import time

stream = []
count = 10


def generate(value):
    print(value)
    stream.append(random.randint(0, 1000))
    value -= 1
    if value == 0:
        t.cancel()




t = threading.Timer(0.2, generate, [count])
t.start()


time.sleep(0.2 * count + 2)

map1 = {}

for i in stream:
    map1[i] = map1.get(i, 0) + 1

print(map1)
