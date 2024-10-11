from typing import List, Tuple

lines = open("source_file.json", "r", encoding='utf-8').read().splitlines()


def clear_line(line: str) -> List:
    return [line.strip().rstrip(",") for line in line.replace('"', '').split(":", 1)]


def parsing(txt: List[str], cnt: int) -> Tuple[dict, int]:
    data = {}
    while True:
        if "}" in txt[cnt][0]:
            break
        if "{" in txt[cnt][1]:
            data[txt[cnt][0]], cnt = parsing(txt, cnt + 1)
            continue
        data[txt[cnt][0]] = txt[cnt][1]
        cnt += 1
    cnt += 1
    return data, cnt


def file_write(string: str) -> None:
    with open('main_outpute_file.xml', 'a', encoding='utf-8') as f:
        f.write(string)


def create_file(elem: str, dictionary: dict) -> None:
    global tabs
    if type(dictionary[elem]) != dict:
        file_write("\t" * tabs + '<{}>{}</{}>\n'.format(elem, dictionary[elem], elem))
    else:
        file_write('\t' * tabs + '<{}>\n'.format(elem))
        tabs += 1
        [create_file(elem2, dictionary[elem]) for elem2 in dictionary[elem]]
        tabs -= 1
        file_write('\t' * tabs + '</{}>\n'.format(elem))


if __name__ == "__main__":
    a = [clear_line(line) for line in lines if clear_line(line)[0] != "{"]

    data, temp = parsing(a, 0)

    file_write('<?xml version="1.0" encoding="UTF-8" ?>\n')
    file_write("<root>\n")

    tabs = 1
    [create_file(elem, data) for elem in data]
    file_write("</root>")
