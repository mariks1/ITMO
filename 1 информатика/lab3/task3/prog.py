import re

GROUP = input()
peopleList = open("task3/tests/1.txt", 'r', encoding = 'utf-8').read().splitlines()    

def findPeople(array):
    for person in array:
        getPersonInfo = re.search(r'(.+) (\w+)\. (.+)', person) # w - все буквы, независимо от регистра, .+
        if not getPersonInfo:
            return 'Person not have current info'
        if '-' in getPersonInfo.group(1):
            doubleSurname = getPersonInfo.group(1).split('-')
            if doubleSurname[0][0] == doubleSurname[1][0] == getPersonInfo.group(2) == getPersonInfo.group(3) and GROUP == getPersonInfo.group(4):
                array.remove(person)
        else: 
            if getPersonInfo.group(1)[0] == getPersonInfo.group(2) == getPersonInfo.group(3) and getPersonInfo.group(4) == GROUP:
                array.remove(person)
    return array

for person in findPeople(peopleList):
    print(person)   




#array = list(sorted(set(array))) # list -> форматируем словарь в массив потому что set - возвращает словарь {1, 2 ,3}, а list -> [1,]