

def clear_line(line):
    return [line.strip().rstrip(",") for line in line.replace('"','').split(":", 1)]

def parsing(txt, cnt):
    data = {}
    while True:                                 # data = {}
        if "}" in txt[cnt][0]:
            break
        elif "{" in txt[cnt][1]:
            temp_data, temp_cnt = parsing(txt,cnt+1)
            data[txt[cnt][0]] = temp_data
            cnt = temp_cnt
        else:
            data[txt[cnt][0]] = txt[cnt][1]
            cnt+=1
    cnt+=1
    return data, cnt

lines = open("source_file.json", "r",encoding='utf-8').read().splitlines()

a = []
for line in lines:
    if clear_line(line)[0] != "{":
        a.append(clear_line(line))

data, temp = parsing(a,0)

def file_write(string):
    with open('additional5_outpute_file.wml', 'a',encoding='utf-8') as the_file:
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
file_write("<wml>\n")
file_write("\t" + "<card>\n")

tabs = 2
for elem in data:
    create_file(elem,data)
file_write("\t" + "</card>\n")
file_write("</wml>")


