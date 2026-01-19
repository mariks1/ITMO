import re 

string = open("task1/tests/1.txt", 'r', encoding = 'utf-8').read()
def countEmojis(string):
    count = re.findall(r'\:\-\\', string)
    if len(count) < 1:
        return 0
    if count:
        return len(count)


print(countEmojis(string))