package com.example.mdaraplication.patientmain.fragment.covid19_vaccine

enum class Vaccine(var vaccineName:String,var objectCode:Int,var ATCCode:String,var ingredient:List<String>,var excipient:List<String>){
    MODERNA("모더나코비드-19백신주",202104022, "J07BX88", arrayListOf("사스 코로나바이러스-2 스파이크 단백질 발현 메신저 리보핵산(숙주: DIG315, 벡터: PL-022856)"),
        arrayListOf("DSPC","아세트산나트륨삼수화물","콜레스테롤","트리스(히드록시메틸)아미노메탄","백당","PEG2000-DMG","SM-102","아세트산","주사용수","트리스염산염")),
    COMINATY("코미나티주(화이자)",202102048,"J07BX88", arrayListOf("사스 코로나바이러스-2 스파이크 단백질 발현 메신저 리보핵산(토지나메란)(숙주: DH10B, 벡터: pST4-1525)"),
        arrayListOf("((4-하이드록시부틸)아자네디일)비스(헥산-6,1-디일)비스(2-헥실디케노에이트)(에이엘씨-0315)","염화칼륨","인산수소나트륨이수화물","2-[(폴리에틸렌 글리콜)-2000]-N","N-디테트라데실아세트아미드(에이엘씨-0159)","염화나트륨","콜레스테롤","백당","주사용수","12-디스테아로일-sn-글리세로-3-포스포콜린(디에스피씨)","인산이수소칼륨")),
    YANSEN("코비드-19백신얀센주",202102967,"J07BX88", arrayListOf("재조합 코로나바이러스 스파이크 단백질 발현 아데노바이러스 벡터(숙주: PER.C6 TetR, 벡터: Ad26.COV2.S)"),
        arrayListOf("시트르산수화물","수산화나트륨","폴리소르베이트80","염화나트륨,시트르산삼나트륨이수화물","염산","주사용수","히드록시프로필베타-시클로덱스트린","에탄올")),
    AZ("한국아스트라제네카코비드-19백신주",202101249,"J07BX88", arrayListOf("재조합 코로나바이러스 스파이크 단백질 발현 아데노바이러스 벡터(숙주: T-RexTM-293, 벡터: ChAdOx1)"),
        arrayListOf("염화마그네슘수화물","폴리소르베이트80","염화나트륨","L-히스티딘","에데트산나트륨수화물","백당","주사용수","L-히스티딘염산염수화물","에탄올"))

}