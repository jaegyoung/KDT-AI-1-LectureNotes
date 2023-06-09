import pandas as pd

일부_고객_대출_정보 = pd.read_excel('chunk_155001-155746.xlsx')

print(일부_고객_대출_정보.head(10))

print("30개 뿌리기")
print(일부_고객_대출_정보.head(30))

total_row_column_info = 일부_고객_대출_정보.shape
print(total_row_column_info)

일부_고객_대출_정보 = 일부_고객_대출_정보.drop('식별구분', axis=1)
print(일부_고객_대출_정보.head(10))

온라인_햇살론_일부_고객_대출_정보 = 일부_고객_대출_정보[일부_고객_대출_정보['GOODS_CD'].astype(str) == '760143']
tmp = 온라인_햇살론_일부_고객_대출_정보

# -999가 존재하면 전반적으로 만땅을 못받나 ?
# 모호성이 존재한다면 -999가 존재하는 모든 데이터를 밀어버리자.
# 데이터가 신뢰할 수 없기 때문에 밀어버리고 신뢰할 수 있는 정보를 바탕으로 정보를 구축해야 함
이상_데이터_제거후_온라인_햇살론_일부_고객_대출_정보 = tmp[~((tmp == -999) | (tmp == 999999991)).any(axis=1)]

# 실제 대출한 금액, 신용점수, 대출 상품, 직위,직군, 미상환 금액, 미상환 기관 개수
# 사채 미상환 건수, 신용 카드 한도 소진율, 현금 서비스 한도 소진율, 은행 업권 제외 미상환 건수, 저축은행업권 미상환 대출금액
# 최근 5년내 해제된 대출 중 양도해지 총 건수, 500만원미만 미상환 대출총금액, 1000만원미만 미상환 대출총금액, 상환 개월수
# 전은연 미상환 계좌별 은행제외단위조합제외 신차대출 및 예적금담보제외 총 건수(카드론인별기준)
# 최근 12개월내 대출 경험 총 건수(계좌상태 상각) <<--- 실제 영향을 주는 지 미지수임 (필요에 따라 데이터 배제가 필요함)
columns_to_keep = ['PRE_LMT', 'PRE_RT', 'GOODS_CD', 'RRC_CD', 'HAC_CD', 'LU0024101', 'LU0024201',
                   'LS0000180', 'CL0631905', 'CL0631906', 'L2A000105', 'L00080002',
                   'LA6000005', 'L22001800', 'L22002000', 'SALE_TRM',
                   'LC0099024', 'LA1200018']

불필요한_정보를_모두_제거한_온라인_햇살론_일부_고객_대출_정보 = 이상_데이터_제거후_온라인_햇살론_일부_고객_대출_정보[columns_to_keep]

print(불필요한_정보를_모두_제거한_온라인_햇살론_일부_고객_대출_정보)

불필요한_정보를_모두_제거한_온라인_햇살론_일부_고객_대출_정보.to_excel('온라인햇살론_분석정보.xlsx')


# 그 경우 활용할 수 있는 유효 데이터가 몇 개 존재하는가 ?
# 이를 토대로 Deep Learning 모델을 만든다.