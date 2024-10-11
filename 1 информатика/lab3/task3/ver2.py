import re

GROUP = input()
peopleList = open("task3/tests/1.txt", 'r', encoding = 'utf-8').read().splitlines()    

def findPeople(array):
    for person in array:
        match = re.search(r"([А-Я])[а-я]*-?\1?[а-я]*\s\1.{2}.\s(.+)", person)
        if match is not None and GROUP == match.group(2):
            array.remove(person)
    return array

for person in findPeople(peopleList):
    print(person)