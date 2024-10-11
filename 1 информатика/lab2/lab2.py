import sys

x = input()
n = [int(a) for a in str(x)]
if len(n)!=7:
    print("Сообщение должно содержать 7 бит!")
    sys.exit(0)
f1 = ["001","010","011","100","101","110","111"]
f2 = ["r3","r2","i3","r1","i2","i1","i4"]
f3 = [3,1,5,0,4,2,6]
s1 = n[0]^n[2]^n[4]^n[6]
s2 = n[1]^n[2]^n[5]^n[6]
s3 = n[3]^n[4]^n[5]^n[6]
ans = str(s1)+str(s2)+str(s3)
if ans == "000":
    print(x)
    print("Ошибки в сообщении нет")
else:
    print(ans)
    for i in range(7):
        if ans == f1[i]:
            n[f3[i]] = 1 - n[f3[i]]
            result = ''.join(map(str,n))
            print("Правильное сообщение:",result)
            print("Ошибка в бите:",f2[i])


