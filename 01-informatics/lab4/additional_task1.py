from json2xml import json2xml
from json2xml.utils import readfromjson
xmlf = open('additional1_outpute_file.xml','w+',encoding='utf-8')
data = readfromjson("source_file.json")
print(data)
xmlf.write(json2xml.Json2xml(data,wrapper='root',attr_type=False).to_xml())
xmlf.close()


