# #인자 값으로 넘어오는 것 info, key, city, date
import sys, io

sys.stdout = io.TextIOWrapper(sys.stdout.detach(), encoding="utf-8")
sys.stderr = io.TextIOWrapper(sys.stderr.detach(), encoding="utf-8")

from googletrans import Translator
import json
from urllib.request import urlopen

data = sys.argv[1:]

# info = '1'
info = data[0]
city = data[1]
key = data[2]
date = data[3]

weather = ""

#오늘
if info == '1':
    # city = "인천"
    # key = "c9ceb2f1e71d36dc3dbb5522352381ee"
    # date = '2024-05-18'
    
    tr = Translator()
    result = tr.translate(city)
    city = result.text   
    
    url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +"&appid=" + key
    response = urlopen(url)
    json_api = response.read().decode('utf-8')
    json_file = json.loads(json_api)

    #지역, 날짜, 최저기온, 최고기온, 총강수량, 평균풍속, 평균운량
    weather_date = json_file['weather']
    stnNm = json_file['name']
    main = weather_date[0]['main'] #기본
    minTa = float(json_file['main']['temp_min']) -273.15
    maxTa = float(json_file['main']['temp_max']) -273.15
    json_str = json.dumps(data)
    if 'rain' not in json_str:
        sumRn = ""
    else:
        sumRn = json_file['rain']['1h'] #mm

    avgWs = json_file['wind']['speed'] #m/s
    avgTca = json_file['clouds']['all']

    tmp = (stnNm, date, round(minTa,1), round(maxTa,1), sumRn, avgWs, avgTca)
    
#과거
else:
    # date = "20240505"
    # city = "112"
    # key = ""

    url = "http://apis.data.go.kr/1360000/AsosDalyInfoService/getWthrDataList?serviceKey="+key
    param ="&dataType=JSON&numOfRows=10&pageNo=1&dataCd=ASOS&dateCd=DAY&startDt="+date+"&endDt="+date+"&stnIds="+city

    response = urlopen(url+param)
    json_api = response.read().decode('utf-8')
    json_file = json.loads(json_api)
    print(json_file)

    resultMsg = json_file['response']['header']['resultMsg']
    if resultMsg != 'NORMAL_SERVICE':
        print(resultMsg)
        exit(1) 

    weather_data = json_file['response']['body']['items']['item'][0]
    stnNm = weather_data['stnNm']
    date = weather_data['tm']
    minTa = round(float(weather_data['minTa']),1)
    maxTa = round(float(weather_data['maxTa']),1)
    sumRn = weather_data['sumRn']
    if sumRn == '':
        sumRn = 0
    else:
        sumRn = round(float(weather_data['sumRn']),1)
    avgWs = round(float(weather_data['avgWs']),1)
    avgTca = round(float(weather_data['avgTca']),1) * 10
    #지역, 날짜, 최저기온, 최고기온, 총강수량, 평균풍속, 평균운량
    tmp = (stnNm, date, minTa, maxTa, sumRn, avgWs, avgTca)
    
#지역, 날짜, 최저기온, 최고기온, 총강수량, 평균풍속, 평균운량
print(tmp)


if 10 <= avgTca <= 30:
    weather = "구름 조금"
elif 30 < avgTca <= 60:
    weather = "구름 많음"
elif 60 < avgTca:
    weather = "흐림"

if sumRn != "":
    if  0.5 < sumRn <= 0.8:
        weather = "소나기"
    elif 0.8 < sumRn < 4:
        weather = "비"
    elif 4 < sumRn :
        weather = "하루종일 비"

if weather=="":
    weather = "최고 " + str(round(maxTa,1)) +"도로 맑음"
    
print(weather)