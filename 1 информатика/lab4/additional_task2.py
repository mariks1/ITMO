import re
import time

my_time = time.time()
op_bracket = re.compile(r"^\s*{")
def parse_opb(tmp):
    match = op_bracket.match(tmp)
    if match is not None:
        return False
    return True

stringWbracket = re.compile(r"^\s*\"(.+)\":\s*{")
string = re.compile(r"^\s*\"(.+)\":\s\"(.*)\"")
cl_bracket = re.compile(r"^\s*}")

def clear_line(line):
    return [line.strip().rstrip(",") for line in line.replace('"', '').split(":", 1)]

def parsing(txt, cnt):  
    data = {}
    while True:       
        if cl_bracket.match(txt[cnt]) is not None:
            break
        if stringWbracket.match(txt[cnt]) is not None:
            first = (stringWbracket.match(txt[cnt])).groups()
            temp_data, temp_cnt = parsing(txt,cnt+1)
            data[first[0]] = temp_data
            cnt = temp_cnt
        else:
            match = string.match(txt[cnt])
            if match:
                first, second = match.groups()
                data[first] = second
                cnt += 1
    cnt+=1
    return data, cnt

lines = open("source_file.json", "r",encoding='utf-8').read().splitlines()

a = []
for line in lines:
    if parse_opb(line):
        a.append((line))
data, tmp = parsing(a,0)

def file_write(string):
    with open('additional2_outpute_file.xml', 'a',encoding='utf-8') as the_file:
        the_file.write(string)

def create_file(elem,dictionary):
    global tabs
    if type(dictionary[elem]) != dict:
        file_write("\t" * tabs + '<{}>{}</{}>\n'.format(elem,dictionary[elem],elem))
    else:
        file_write('\t' * tabs + '<{}>\n'.format(elem))
        tabs += 1
        for elem2 in dictionary[elem]:
            create_file(elem2,dictionary[elem])
        tabs -= 1
        file_write('\t' * tabs + '</{}>\n'.format(elem))


file_write('<?xml version="1.0" encoding="UTF-8" ?>\n')
file_write("<root>\n")

tabs = 1
for elem in data:
    create_file(elem,data)
file_write("</root>")

my_time2 = time.time()
print((my_time2-my_time)*100)

