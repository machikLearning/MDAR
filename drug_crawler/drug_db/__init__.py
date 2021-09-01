from drug_crawler.drug_db import db_manager

# DB와 관련된 클래스들의 집합
# Controller는 manager를 private 변수로 갖고있는다
# Controller는 DB작업시 발생하는 예외들을 처리해주는 클래스
# manager는 db에 쿼리를 요청하는 클래스