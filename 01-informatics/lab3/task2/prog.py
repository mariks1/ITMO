import re
 
string = open("task2/tests/3.txt", "r", encoding = 'utf-8').read()

def isHaiku(string):
    string = string.lower()
    countStrings = string.split('/')
    if not countStrings or len(countStrings) != 3:
        return "Не хайку. Должно быть 3 строки." 
    
    arrayValue = []
    constArray = [5,7,5]
    
    for substring in countStrings:
        getVowels = re.findall('[аеиоуюяё]', substring)
        arrayValue.append(len(getVowels))
    if arrayValue == constArray:
        return 'Хайку!'
    else:
        return 'Не хайку.'

print(isHaiku(string))