package com.example.plusteamproject.domain.report.entity;

public enum ReportType {
	FRAUD, // 사기
	DIFFERENT, // 제품설명과 실물이 다름
	DUPLICATE, // 모조품 issue
	SPAM, // 스팸성 제품
	INAPPROPRIATE_CONTENT,// 부적절한 내용 (예: 음란물, 폭력 등)
	WRONG_CATEGORY, // 잘못된 카테고리 등록
	PROHIBITED_ITEM, // 금지 품목 (예: 불법 약물, 무기 등)
	EXPIRED_OR_SPOILED, // 유통기한 지난 상품, 변질된 상품
	COPYRIGHT_INFRINGEMENT, // 저작권 침해
	PRICE_GOUGING, // 가격 부당 책정
	OTHER // 기타
}
