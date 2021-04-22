import random

arr = [random.randint(0, 1000) for i in range(1000000)]

map1 = {}
for i in arr:
    map1[i] = map1.get(i, 0) + 1

moment_0 = len(map1.keys())
moment_1 = sum(map1.values())
moment_2 = sum(i * i for i in map1.values())


val_1 = {}
val_2 = {}

while len(val_1) != 100:
    val_1[random.randint(0, 1000000)] = 0

while len(val_2) != 500:
    val_2[random.randint(0, 1000000)] = 0

for i in val_1.keys():
    for j in range(i, len(arr)):
        if arr[i] == arr[j]:
            val_1[i] = val_1.get(i, 0) + 1

for i in val_2.keys():
    for j in range(i, len(arr)):
        if arr[i] == arr[j]:
            val_2[i] = val_2.get(i, 0) + 1
moment_2_1 = (1000000 * (sum(i for i in val_1.values()) * 2 - 100)) / 100
moment_2_2 = (1000000 * (sum(i for i in val_2.values()) * 2 - 500)) / 500

print("moment_0: {}\n"
      "moment_1: {}\n"
      "moment2_not_ams: {}\n"
      "moment2_by_ams_100_values: {}\n"
      "moment2_by_ams_500_values: {}".format(moment_0, moment_1, moment_2, moment_2_1, moment_2_2))
