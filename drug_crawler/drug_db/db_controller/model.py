from abc import *
from ..save_model.model import *
from ..db_manager.model import *
from ..error_model.model import *


#DBManager를 생성해주는 최상위 Controller 클래스
#operating은 추상메소드로 AbsDataCollector를 상속받는 자식클래스에
# 자식클래스가 중점적으로 작업해야하는 내용을 입력하면 된다
class AbsDataCollector(metaclass=ABCMeta):
    def __init__(self):
        self._db_manager = DBManager()

    @abstractmethod
    def operating(self, data):
        pass

# item_seq를 이용하여 생성된 데이터와 관련된 Controller
class ProductionCodeController(AbsDataCollector):

    def select_drug_item_body_in_list(self,l):
        result = self._db_manager.select_drug_item_body_in_list(l)
        return result

    def select_drug_item_body_by_one(self, id):
        result = self._db_manager.select_drug_item_body_by_one(id)
        return result

    def not_atc_code(self):
        result = self._db_manager.not_atc_code()
        return result

    def select_drug_by_id(self, id):
        return self._db_manager.select_drug_by_id(id)

    def _select_drug_item(self, item_seq):
        return self._db_manager.select_drug_item(item_seq)

    def save_correct(self, data):
        drug_item_id = self._select_drug_item(data[0])
        if not drug_item_id == -1:
            self._db_manager.insert_correct(drug_item_id, data)

    #nb_doc_data의 파싱이 실패한 데이터를 저장하는 함수
    def insert_nb_doc_fail(self, item_seq, ex):
        self._db_manager.insert_nb_doc_fail(item_seq, ex)
    #item_seq 목록및 해당 약제를 DB에서 가져오는 함수
    def select_production_code(self):
        return self._db_manager.select_production_code()

    #item_seq 크롤링 데이터 저장함수
    def operating(self, data):
        print(data["ITEM_SEQ"])
        try:
            data["ITEM_SEQ"] = self._select_drug_item(data["ITEM_SEQ"])[0]
            productionCodeModel = ProductionCodeModel(data)
            self._db_manager.insert_model(productionCodeModel)
        except Exception as ex:
            print(ex)
    #크롤링 도중 에러가 발생한 약제의 item_seq를 저장하는 함수
    def save_fail(self, productionCode, caused):
        failModel = ProductionFail(productionCode, caused)
        self._db_manager.insert_model(failModel)

    #중복 item_seq를 제거하고 DB에 item_seq를 저장하는 함수
    def save_item_seq(self,productionCodeList):
        self._db_manager.save_item_seq(productionCodeList)

    #크롤링이 성공한 데이터를 갖고오는 함수
    def select_working(self):
        return self._db_manager.select_working()

    # 크롤링이 성공한 데이터를 범위를 지정하여 갖고오는 함수
    def select_success_item_seq(self, first, last):
        return self._db_manager.select_success_item_seq(first=first, last=last)

    #크롤링이 실패한 데이터를 갖고오는 함수
    def select_fail_item_seq(self):
        return self._db_manager.select_fail_item_seq()

    #nb_doc_data 엄데이트 함수
    def update_nb_doc_data(self, data, id):
        self._db_manager.update_nb_doc_data(data,id)


class CrawlingDataCollector(AbsDataCollector):
    def __init__(self):
        self._db_manager = DBManager()

    def operating(self, data):
        pass

    def get_code_data(self):
        return self._db_manager.select_all_main_code()

    def select_fail_caused_one(self):
        return self._db_manager.select_fail_caused_one()

    def select_public_or_fail(self, public_id):
        public = self._db_manager.select_public_portal(public_id)
        fail = self._db_manager.select_fail(public_id)
        result = None

        if (not public) and (not fail):
            result = self._db_manager.select_public_drug(public_id)

        return result

    def saveCrawlingData(self, codeId, crawlingData):
        crawlingModel = PublicCrawlingData(main_code_id=codeId , crawling_data=crawlingData)
        if crawlingModel.isFinishing():
            try:
                self._db_manager.insert_public_data(crawlingModel)
            except:
                # print("status : 3")
                self.saveFail(codeId = codeId, caused=3)
        else:
            # print("status : 1")
            self.saveFail(codeId=codeId, caused=1)

    def saveFail(self, codeId, caused):
        failData = Fail(codeId,caused)
        self._db_manager.insertFail(failData)



class AbsPreProcessingCollector(AbsDataCollector):
    def __init__(self):
        AbsDataCollector.__init__(self=self)
    @abstractmethod
    def operating(self, data):
        pass

class ExcelMainCodeCollector(AbsPreProcessingCollector):
    def __init__(self):
        AbsPreProcessingCollector.__init__(self=self)

    def operating(self, data):
        main_data = Main_Data(data)
        self._db_manager.save_main_code(main_data)

class ExcelPreProcessingCollector(AbsPreProcessingCollector):
    def __init__(self):
        AbsPreProcessingCollector.__init__(self=self)

    def operating(self, data):
        f_key = self._db_manager.select_main_code(data[0])
        data[0] = f_key[0]
        excel_data = Excel_Data(preprocessing_data=data)
        self._db_manager.insert_excel_data(excel_data)
        # del data[0]
        # data.append(f_key)
        # excel_data = Excel_Data(preprocessing_data=data)
        # self._db_manager.save_preprocessing_data(preprocessing_model=excel_data)

class ExcelBasicDrugCollector(AbsPreProcessingCollector):
    def __init__(self):
        AbsPreProcessingCollector.__init__(self=self)

    def operating(self, data):
        f_key = self._db_manager.select_basic_drug(data[0])
        data[0] = f_key[0]
        basic_data = BasicData(data)
        self._db_manager.save_basic_drug(basic_data)