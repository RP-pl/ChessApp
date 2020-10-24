file = open('/f.txt')
raw = file.read().replace("\n"," ").split(' ')
print(raw[:1000])
s = set()
for move in raw:
    if move not in s:
        s.add(move)
        print(len(s))
