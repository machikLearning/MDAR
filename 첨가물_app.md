## 첨가물 app 

1. 첨가물 DB 구축 관련

약물소스: director의 'drug_template.csv' 파일의 2열 '제품코드'가 openAPI에 보낼 수 있는 주소임.

url = 'http://apis.data.go.kr/1471057/MdcinPrductPrmisnInfoService/getMdcinPrductItem?ServiceKey='

Service_key = '77RgKiN2sETIeIDL1Fxb6%2BKmIwYHwJQGlmN5R7EWj2rUaOjQttyC43NM7YTyTZ4P9pjOqndDENRB1FNuFBWeew%3D%3D'

operation 상세매뉴얼 data.go.kr/cmm/cmm/fileDownload.do?atchField=FILE000000002267055@fileDetailSn=1

이전 작업 jupyter notebook 코드 중 첨가물 변수 정리
  

# data.go.kr openAPI 접속 URL+Key
url = 'http://apis.data.go.kr/1471057/MdcinPrductPrmisnInfoService/getMdcinPrductItem?ServiceKey='
Service_key = '77RgKiN2sETIeIDL1Fxb6%2BKmIwYHwJQGlmN5R7EWj2rUaOjQttyC43NM7YTyTZ4P9pjOqndDENRB1FNuFBWeew%3D%3D'

import pandas as pd

data = pd.read_excel("Drug_newdrug_ATCnull_201904.xlsx")
request_drugs = data.loc[:,'제품코드'].astype(str)

#data_set = ['item_seq', 'item_name', 'item_entp_name', 'item_class_no', 'item_bar_code', 'item_material_nam', 'item_edi_code', 'item_ingd', 'item_nb_doc_data']
drug_data = []

for request_drug in request_drugs:
    print("약물이름은... {}".format(request_drug))
    
    add = url+Service_key+"&bar_code="+request_drug+"&pageNo=1&numOfRows=100"

    req = requests.get(add)
    html = req.text
    
    try:
        soup = BeautifulSoup(html, 'html.parser')

        item_seq = soup.select('item_seq')[0].text.strip()
        item_name = soup.select('item_name')[0].text.strip()
        item_entp_name = soup.select('entp_name')[0].text.strip()
        item_class_no = soup.select('class_no')[0].text.strip()
        item_bar_code = soup.select('bar_code')[0].text.strip()
        item_material_nam = soup.select('material_nam')[0].text.strip()
        item_edi_code = soup.select('edi_code')[0].text.strip()
        item_ingd = soup.select('ingr_name')[0].text.strip()
#        item_nb_doc_data = soup.select('nb_doc_data')[0].text.strip()
  
#        drug_data.append((request_code, item_seq, item_name, item_entp_name, item_class_no, item_bar_code, item_material_nam, item_edi_code, item_ingd, item_nb_doc_data))
        drug_data.append((request_code, item_seq, item_name, item_entp_name, item_class_no, item_bar_code, 
                          item_material_nam, item_edi_code, item_ingd))

        try:
            cur.execute("INSERT INTO ingredients (request_code, item_seq, item_name, item_ingd) VALUES (%s, %s, %s, %s)", 
                        (request_drug, item_seq, item_name, item_ingd))
            print("postsql로 insert 성공", request_code, item_name, item_ingd) 
        except:
            print("postsql로 insert하지 못했습니다.")
        
#    except:
#        print("openAPI에서 자료를 추출하지 못했습니다.")
        
con.commit()
con.close()
2. app 기능 

환자가 알레르기가 있는 첨가물 정보를 선택하면, 특정 약물에 해당 첨가물이 들어있을 경우 이를 warning
이전 Smart_DUR app의 기능과 거의 유사 (Smart_DUR app은 ATC 코드를 기반으로 작동하지만, 첨가물 app은 좀 더 단순)
