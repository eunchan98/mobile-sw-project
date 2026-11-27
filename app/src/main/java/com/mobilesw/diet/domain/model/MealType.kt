package com.mobilesw.diet.domain.model

/**
 * 끼니 종류. 분석 화면의 분류 기준이기도 하다.
 *
 * DB에는 enum 이름(name)이 문자열로 저장되므로, 순서나 이름을 바꾸면 기존 데이터와 맞지 않게 된다.
 * 시간으로 끼니를 추천하는 로직은 식사 기록 기능(팀원 1)에서 구현한다.
 */
enum class MealType(val label: String) {
    BREAKFAST("조식"),
    LUNCH("중식"),
    DINNER("석식"),
    SNACK("간식/음료"),
}
