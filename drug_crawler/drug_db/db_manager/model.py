import pymysql
from abc import *

from drug_crawler.drug_db.dberror.db_error import DBSQLError


class SingletonInstane:
  __instance = None

  @classmethod
  def __getInstance(cls):
    return cls.__instance

  @classmethod
  def instance(cls, *args, **kargs):
    cls.__instance = cls(*args, **kargs)
    cls.instance = cls.__getInstance
    return cls.__instance


class DBConnecter(SingletonInstane):

    def __init__(self):
        self.__host = "localhost"
        self.__user = "mdar_admin"
        self.__password = "mdar123"
        self.__db = "MDAR"
        self.__port = 3306
        self.__charset = "utf8"

    def create_execute_query(self,query):
        conn = self._connect()
        try:
            with conn.cursor() as cursor:
                cursor.execute(query)
            conn.commit()
        except Exception as ex:
            print(ex)

    def insert_query(self,query):
        conn = self._connect()
        try:
            with conn.cursor() as cursor:
                cursor.execute(query)
                conn.commit()
        except Exception as ex:
            conn.close()
            raise NameError("insert error")
        finally:
            conn.close()


    def select_query(self,query):
        conn = self._connect()
        result = -1
        try:
            with conn.cursor() as cursor:
                cursor.execute(query)
                result = cursor.fetchall()
        except Exception as ex:
            print(ex)
        finally:
            conn.close()
        return result

    def connect(self):
        return pymysql.connect(host=self.__host, port=self.__port, user=self.__user, password=self.__password, db=self.__db, charset=self.__charset)


class AbsDBManager(metaclass=ABCMeta):

    def __init__(self):
        self._db_connect = DBConnecter.instance()

    @abstractmethod
    def create_table(self, table_name):
        pass

    @abstractmethod
    def save_preprocessing_data(self,preprocessing_model):
        pass

    @abstractmethod
    def save_basic_drug(self, data):
        pass

    @abstractmethod
    def select_main_code(self,data):
        pass

    @abstractmethod
    def save_main_code(self,data):
        pass


class DBManager(AbsDBManager):

    def __init__(self):
        AbsDBManager.__init__(self = self)

    def select_drug_item_body_in_list(self,l):
        sql = "SELECT item_name, entp_name, cnsgn_manuf, etc_otc_ocde, class_no, chart, bar_code, material_name, ee_doc_id, ud_doc_id, nb_doc_id," \
              "insert_file, storage_method, valid_term, reexam_target, reexam_date, pack_unit, edi_code, doc_text, permit_kind_name, entp_no, make_material_flag, newdrug_class_name," \
              "induty_type, cancel_date, cancel_name, change_date, narcotic_kind_code, gbn_name, ee_doc_data, ud_doc_data, nb_doc_data,pn_doc_data,main_item_ingr, ingr_name,item_permit_date FROM drug_item_body WHERE item_seq IN (%s)"

        conn = self._db_connect.connect()
        with conn.cursor() as cursor:
            cursor.execute(sql,tuple(l))
            result = cursor.fetchall()
            conn.commit()
        return result

    def select_drug_item_body_by_one(self,id):
        sql = "SELECT item_name, entp_name FROM drug_item_body WHERE item_seq = " + id
        conn = self._db_connect.connect()
        result = -1;
        with conn.cursor() as cursor:
            cursor.execute(sql)
            result = cursor.fetchone()
            conn.close()
        return result

    def select_drug_by_id(self, id):
        sql = "SELECT item_seq FROM drug_item WHERE id=" +str(id)
        conn = self._db_connect.connect()
        result = -1
        with conn.cursor() as cursor:
            cursor.execute(sql)
            result = cursor.fetchone()
            conn.close()
        return result

    def not_atc_code(self):
        sql = " SELECT A.id,C.atc_name, D.item_seq, A.item_name, A.material_name, A.ingr_name, A.main_item_ingr," \
              "A.entp_name,A.item_permit_date,A.cnsgn_manuf,A.etc_otc_ocde,A.class_no,A.chart  FROM MDAR.drug_item_body A LEFT OUTER JOIN MDAR.drug_atc_mapper B ON A.id= B.drug_item_id LEFT OUTER JOIN MDAR.atc_code C ON B.atc_code_id = C.id LEFT OUTER JOIN MDAR.drug_item D ON A.item_seq = D.id"
        conn = self._db_connect.connect()
        result = -1
        with conn.cursor() as cursor:
            cursor.execute(sql)
            result = cursor.fetchall()
            conn.close()
        return result

    def insert_correct(self, drug_item_id, data):
        sql = 'INSERT INTO drug_item_body(item_seq,item_name, entp_name, cnsgn_manuf, etc_otc_ocde, class_no, chart, bar_code, material_name, ee_doc_id, ud_doc_id, nb_doc_id,' \
              'insert_file, storage_method, valid_term, reexam_target, reexam_date, pack_unit, edi_code, doc_text, permit_kind_name, entp_no, make_material_flag, newdrug_class_name,' \
              'induty_type, cancel_date, cancel_name, change_date, narcotic_kind_code, gbn_name, ee_doc_data, ud_doc_data, nb_doc_data,pn_doc_data,main_item_ingr, ingr_name,item_permit_date) VALUES('
        sql += str(drug_item_id[0])
        for i in range(1,len(data)):
            sql += ', "'+data[i].replace('"', "'")+'"'
        sql +=");"
        conn = self._db_connect.connect()
        with conn.cursor() as cursor:
            try:
                cursor.execute(sql)
                conn.commit()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()



    def select_drug_item(self, item_seq):
        sql = "SELECT id FROM drug_item WHERE item_seq = '" + item_seq + "';"
        conn = self._db_connect.connect()
        result = -1
        with conn.cursor() as cursor :
            try:
                cursor.execute(sql)
                result = cursor.fetchone();
            except Exception as ex:
                print(ex)
            finally:
                cursor.close()
        return result

    def insert_nb_doc_fail(self, id, ex):
        sql = "INSERT INTO nb_doc_fail(item_seq,caused) VALUES ('" + id+"' '"+ex +"')"
        conn = self._db_connect.connect()
        with conn.cursor() as cursor:
            try:
                cursor.execute(sql)
            except Exception as ex:
                print(ex)
            finally:
                conn.close()

    def select_production_code(self):
        sql = "SELECT item_seq FROM drug_item;"
        result = []
        conn = self._db_connect.connect()
        with conn.cursor() as cursor:
            try:
                cursor.execute(sql)
                result = cursor.fetchall()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()
        return result

    def save_item_seq(self, productionCodeLsit):
        sql = "INSERT INTO drug_item(item_seq) VALUES(%s)"
        conn = self._db_connect.connect()
        with conn.cursor() as cursor:
            try:
                cursor.executemany(sql, productionCodeLsit)
                conn.commit()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()

    def select_fail_item_seq(self):
        sql = "SELECT DISTINCT item_seq ,caused FROM production_code_fail;"
        result = -1
        conn = self._db_connect.connect()
        with conn.cursor() as cursor:
            try:
                cursor.execute(sql)
                result = cursor.fetchall()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()
        return result

    def select_success_item_seq(self, first, last):
        sql = "SELECT item_name, entp_name, cnsgn_manuf, etc_otc_ocde, class_no, chart, bar_code, material_name, ee_doc_id, ud_doc_id, nb_doc_id," \
              "insert_file, storage_method, valid_term, reexam_target, reexam_date, pack_unit, edi_code, doc_text, permit_kind_name, entp_no, make_material_flag, newdrug_class_name," \
              "induty_type, cancel_date, cancel_name, change_date, narcotic_kind_code, gbn_name, ee_doc_data, ud_doc_data, nb_doc_data,pn_doc_data,main_item_ingr, ingr_name,item_permit_date FROM drug_item_body WHERE id BETWEEN " + str(first) +" AND " + str(last) +";"
        result = -1;
        conn = self._db_connect.connect()
        with conn.cursor() as cursor:
            try:
                cursor.execute(sql)
                result = cursor.fetchall()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()
        return result

    def update_nb_doc_data(self,data, id):
        sql = "UPDATE drug_item_body SET nb_doc_data ='" + data + "' WHERE id = " + str(id) +";"
        conn = self._db_connect.connect()
        with conn.cursor() as cursor:
            try:
                cursor.execute(sql)
                conn.commit()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()


    def select_working(self):
        sql = "SELECT DISTINCT(item_seq_code.item_seq) FROM item_seq_code;"
        result = -1
        conn = self._db_connect.connect()
        with conn.cursor() as cursor:
            try:
                cursor.execute(sql)
                result = cursor.fetchall()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()
        return result

    def insert_model(self,model):
        sql = "INSERT INTO " + model.get_db_name() + "(" + model.get_column() + ") VALUES (" + model.get_value()+");"
        conn = self._db_connect.connect()
        try:
            with conn.cursor() as cursor:
                cursor.execute(sql)
                conn.commit()
                conn.close()
        except Exception as ex:
            print(ex)

    def create_table(self, table_name):
        sql = "CREATE TABLE " + table_name + " (" \
                                             "id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY," \
                                             "main_ingr_code VARCHAR(10) NOT NULL," \
                                             "production_code INT UNSIGNED NOT NULL," \
                                             "production_name TEXT NOT NULL," \
                                             "company VARCHAR(100) NOT NULL," \
                                             "name VARCHAR(30) NOT NULL," \
                                             "ingredient VARCHAR(30) NOT NULL," \
                                             "conc VARCHAR(15) NULL,"\
                                             "condition VARCHAR(20) NULL," \
                                             "form VARCHAR(2) NOT NULL," \
                                             "formulation VARCHAR(25) NOT NULL," \
                                             "amt_num FLOAT NOT NULL," \
                                             "amt_unit VARCHAR(10) NOT NULL);"
        self._db_connect.create_execute_query(sql)

    def save_preprocessing_data(self,preprocessing_model):
        sql = "INSERT INTO public_excel(main_ingr_code, production_code, company) VALUES("
        sql += preprocessing_model.get_data() + ");"
        self._db_connect.insert_query(query=sql)

    def save_basic_drug(self, data):
        conn = self._db_connect.connect()
        with conn.cursor() as cursor:
            try:
                data_sql = "INSERT INTO basic_drug(main_code_id, drug_name, ingredient, conc, drug_condition, form, formulation, amt_num, amt_unit) VALUES (" + data.get_data() + ");"
                cursor.execute(data_sql)
                conn.commit()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()

    def insert_excel_data(self, data):
        conn = self._db_connect.connect()
        sql = "INSERT INTO public_excel (main_code_id ,production_code, production_name, company) VALUES (" + data.get_data() + ");"
        with conn.cursor() as cursor:
            try:
                cursor.execute(sql)
                conn.commit()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()


    def select_all_main_code(self):
        sql = "SELECT id,production_code FROM public_excel;"
        conn = self._db_connect.connect()
        result = -1
        with conn.cursor() as cursor:
            try:
                cursor.execute(query=sql)
                result = cursor.fetchall()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()
        return result

    def select_main_code(self,main_ingr_code):
        sql = "SELECT id FROM main_code WHERE main_ingr_code = '" + main_ingr_code +"';"
        conn = self._db_connect.connect()
        result = -1
        with conn.cursor() as cursor:
            try:
                cursor.execute(query=sql)
                result = cursor.fetchone()
            except Exception as ex:
                print("expectation")
                print(ex)
            finally:
                conn.close()
        return result

    def insert_public_data(self, data):
        sql = "INSERT INTO public_data_portal" +data.get_data()
        conn = self._db_connect.connect()
        with conn.cursor() as cursor:
            try:
                cursor.execute(query=sql)
                conn.commit()
            except Exception as ex:
                print(ex)
                conn.close()
                raise Exception
            finally:
                conn.close()

    def select_fail_caused_one(self):
        sql = "SELECT excel.id, excel.production_code FROM public_excel AS excel INNER JOIN fail AS f ON excel.id = f.public_excel_id WHERE f.caused = 1;"
        conn = self._db_connect.connect()
        result = None
        with conn.cursor() as cursor:
            try:
                cursor.execute(sql)
                result = cursor.fetchall()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()
        return result

    def select_public_portal(self, public_id):
        sql = "SELECT (id) FROM public_data_portal WHERE public_excel_id = " + str(public_id)
        conn= self._db_connect.connect()
        result = None
        with conn.cursor() as cursor:
            try:
                cursor.execute(query= sql)
                result = cursor.fetchone()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()
        return result

    def select_fail(self,public_id):
        sql = "SELECT (id) FROM fail WHERE public_excel_id = " + str(public_id)
        conn = self._db_connect.connect()
        result = None
        with conn.cursor() as cursor:
            try:
                cursor.execute(query=sql)
                result = cursor.fetchone()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()
        return result

    def select_public_drug(self,public_id):
        sql = "SELECT (production_code) FROM public_excel WHERE id = " + str(public_id)
        conn = self._db_connect.connect()
        result = None
        with conn.cursor() as cursor:
            try:
                cursor.execute(query=sql)
                result = cursor.fetchone()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()
        return result

    def save_main_code(self,data):
        conn = self._db_connect.connect()
        with conn.cursor() as cursor:
            try:
                main_model_sql = "INSERT INTO main_code(main_ingr_code) VALUES ('" + data.get_data() + "');"
                cursor.execute(main_model_sql)
                conn.commit()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()

    def insertFail(self, data):
        sql = "INSERT INTO fail (public_excel_id, caused) VALUES ("+ data.get_data() + ");"
        conn = self._db_connect.connect()
        with conn.cursor() as cursor:
            try:
                cursor.execute(sql)
                conn.commit()
            except Exception as ex:
                print(ex)
            finally:
                conn.close()

