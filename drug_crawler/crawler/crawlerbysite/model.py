from abc import *
import requests
from bs4 import BeautifulSoup
import sys, os
import chardet

from drug_crawler.crawler.crawlerbysite.crawlerError.crawler_error import *

sys.path.append(os.path.abspath(os.path.dirname(os.path.abspath(os.path.dirname(__file__)))))
from drug_crawler.crawler.crawlerbysite.crawlerError import *

# 크롤링의 최상위 클래스
# 자식 클래스의 생성자에서 크롤링 할 tag목록, url, service_key를 설정해준다.
# 크롤링 과정은 html 파일을 생성하는 _connect함수와 _parsing 함수로 이루어져있다.
class AbsCrawler(metaclass=ABCMeta):

    # 생성자
    def __init__(self, url, service_keyword, service_key, order, parsing_keyword):
        self._url = url
        self._service_keyword = service_keyword
        self._service_key = service_key
        self._order = order
        self._parsing_keyword = parsing_keyword

    # keyword는 크롤링 키워드 ex)edi_code, bar_code, item_seq
    # html 파일 생성에 실패했으면 connectError발생
    def _connect(self, keyword):
        if isinstance(keyword, int):
            keyword = str(keyword)
        print(keyword)
        api_url = self._url + self._service_keyword+self._service_key+self._order+keyword
        try:
            html = requests.get(api_url).text
        except Exception as ex:
            raise ConnectError()
        return html

    #html을 파싱하는 부분
    # 부모클래스의 private 인자인 _parsing_keyword별로 크롤링을 진행한다.
    # 파싱된 데이터를 dictionary에 담아서 return한다
    def _parsing(self, html):
        result = {}
        soup = BeautifulSoup(html, 'html.parser')
        for keyword in self._parsing_keyword:
            try:
                result[keyword] = soup.select(keyword)[0].text.strip()
            except:
                result[keyword] = -1
        return result

    def operating(self, keyword):
        html = self._connect(keyword=keyword)
        return self._parsing(html=html)

class NbDocDataParser(AbsCrawler):
    def __init__(self):
        # 부모생성자 호출
        AbsCrawler.__init__(self, 'http://apis.data.go.kr/1471057/MdcinPrductPrmisnInfoService/getMdcinPrductItem?',
                        'ServiceKey=',
                        '77RgKiN2sETIeIDL1Fxb6%2BKmIwYHwJQGlmN5R7EWj2rUaOjQttyC43NM7YTyTZ4P9pjOqndDENRB1FNuFBWeew%3D%3D',
                        "&pageNo=1&numOfRows=100&item_seq=",
                        [ 'NB_DOC_DATA'])

    # nb_doc_data는 별도의 파싱작업을 요구하기에 _parsing함수를 별도로 생성해준다.
    # 해당 클래스의 operating을 호출할 시 부모클래스의 _parsing이 호출되는게 아닌 NbDooDataParser의 _parsing 함수가 호출된다.
    def _parsing(self, html):
        soup = BeautifulSoup(html,"html.parser")
        nb_doc_data_list = soup.select("NB_DOC_DATA > DOC > SECTION > ARTICLE")
        result = ""
        for nb_doc_data in nb_doc_data_list:
            result += nb_doc_data.attrs["title"] + " \n "
            result += nb_doc_data.text.strip()+" \n "
        return result

class PublicDataPortal(AbsCrawler):

    def __init__(self):
        AbsCrawler.__init__(self,'http://apis.data.go.kr/1471057/MdcinPrductPrmisnInfoService/getMdcinPrductItem?',
                            'ServiceKey=',
                            '77RgKiN2sETIeIDL1Fxb6%2BKmIwYHwJQGlmN5R7EWj2rUaOjQttyC43NM7YTyTZ4P9pjOqndDENRB1FNuFBWeew%3D%3D',
                            "&pageNo=1&numOfRows=100&bar_code=",
                            ['item_seq', 'item_name', 'entp_name', 'class_no', 'bar_code', 'material_name', 'edi_code', 'main_item_ingr','ingr_name', 'nb_doc_data'])

#item_seq를 기준으로 크롤링할 때 사용된 크롤러
#NbDocDataParser와 작동원리가 같다
class ProductionCode(AbsCrawler):

    def __init__(self):
        AbsCrawler.__init__(self,'http://apis.data.go.kr/1471057/MdcinPrductPrmisnInfoService/getMdcinPrductItem?',
                            'ServiceKey=',
                            '77RgKiN2sETIeIDL1Fxb6%2BKmIwYHwJQGlmN5R7EWj2rUaOjQttyC43NM7YTyTZ4P9pjOqndDENRB1FNuFBWeew%3D%3D',
                            "&pageNo=1&numOfRows=100&item_seq=",
                            ['ITEM_SEQ', 'ITEM_NAME', 'ENTP_NAME', 'ITEM_PERMIT_DATE', 'CNSGN_MANUF',
                             'ETC_OTC_OCDE', 'CLASS_NO', 'CHART', 'BAR_CODE', 'MATERIAL_NAME', 'EE_DOC_ID', 'UD_DOC_ID',
                             'NB_DOC_ID', 'INSERT_FILE', 'STORAGE_METHOD', 'VALID_TERM', 'REEXAM_TARGET', 'REEXAM_DATE',
                             'PACK_UNIT', 'EDI_CODE', 'DOC_TEXT', 'PERMIT_KIND_NAME', 'ENTP_NO', 'MAKE_MATERIAL_FLAG',
                             'NEWDRUG_CLASS_NAME', 'INDUTY_TYPE', 'CANCEL_DATE', 'CANCEL_NAME', 'CHANGE_DATE', 'NARCOTIC_KIND_CODE',
                             'GBN_NAME', 'EE_DOC_DATA', 'UD_DOC_DATA', 'NB_DOC_DATA', 'PN_DOC_DATA', 'MAIN_ITEM_INGR', 'INGR_NAME'])

    def _parsing(self, html):
        result = {}
        soup = BeautifulSoup(html, 'html.parser')
        for keyword in self._parsing_keyword:
            try:
                result[keyword] = soup.select(keyword)[0].text.strip()
                if keyword == "NB_DOC_DATA":
                    nb_doc_data_list = soup.select("NB_DOC_DATA > DOC > SECTION > ARTICLE")
                    data = ""
                    for nb_doc_data in nb_doc_data_list:
                        data += nb_doc_data.attrs["title"] + " \n "
                        data += nb_doc_data.text.strip() + " \n "
                    result[keyword] = data
            except Exception as ex:
                result[keyword] = 'NULL'
        return result

class TableParser:
    def __init__(self):
        self._result = {}
        self.file_path = "./nb_doc_fail.txt"
        if os.path.exists(self.file_path):
            with open(self.file_path,"w", encoding="utf-8") as f:
                f.close()

    def operating(self, data):
        if "2. " in data[32]:
            return "true"
        else:
            with open(self.file_path,"a",encoding="utf-8") as f:
                f.write(str(data[0]) + " " + data[32]+"\n")
                f.close()
            return "false"
        # soup = BeautifulSoup(data[32], "html.parser")
        # trList = soup.select("tr")
        # adr = ""
        # # print(data)
        # # for i in range(1,len(trList)):
        # #     tdList = trList[i].select("td")
        # #     if len(tdList) == 3:
        # #         adr = tdList[0].text[1:len(tdList[0].text)-1].replace(" ", "")
        # #         body = tdList[2].text[1:len(tdList[2].text)-1]
        # #         if "(" in body:
        # #             body = body[:body.find("(")] + body[body.find("("):].replace(",","", 1)
        # #         self._result[adr] = body.split(",")
        # #     else:
        # #         body = tdList[1].text[1:len(tdList[1].text)-1]
        # #         if "(" in body:
        # #             body = body[:body.find("(")] + body[body.find("("):].replace(",","", 1)
        # #         self._result[adr] += body.split(",")
        # if len(trList) > 0:
        #     return trList[0].text
        # else:
        #     print(data[0])
        #     return "None"

class FileProductionCode(ProductionCode):

    def __init__(self):
        ProductionCode.__init__(self=self)

    def operating(self, file_path):
        html = self.openFile(file_path)
        return self._parsing(html)

    def openFile(self, file_path):
        result = ""
        with open(file_path, "r" ,encoding="utf-8") as f:
            result = f.read()
            f.close()
        return result

class SPAC:
    def __init__(self):
        target_url = "https://sheet2site-staging.herokuapp.com/api/v3/load_more.php?key=1F7gLiGZP_F4tZgQXgEhsHMqlgqdSds3vO0-4hoL6ROQ&g=1&e=1&e=1&page=2&is_filter_multi=true&template=TableTemplate&length=99"
        html = requests.get(target_url)
        soup = BeautifulSoup(html, "html.parser")
        nb_doc_data_list = soup.select("#example > tbody > tr")
        self.listRow = []
        for row in nb_doc_data_list:
            strRow = str(row)
            strRow = strRow.replace("<tr>","").replace("</tr>","").replace("<td>","").replace("</td>",",")
            self.listRow.append(strRow.split(","))

    def getListRow(self):
        return self.listRow

