package dev.ahmdaeyz.pinster.domain.common.enums

enum class UserCategory(val value: String) {
    ART("Art"),
    ARTIFICIAL_INTELLIGENCE("Artificial Intelligence"),
    AUTOMOBILE("Automobile"),
    ENVIRONMENT("Environment"),
    EDUCATION("Education"),
    FASHION("Fashion"),
    FINANCE("Finance"),
    FOOD("Food"),
    MOVIE("Movie"),
    SPORT("Sport"),
    TRAVEL("Travel"),
    TECHNOLOGY("Technology")
}

val categoryMapping = mapOf<UserCategory, NewsAPICategory>(
        UserCategory.AUTOMOBILE to NewsAPICategory.TECHNOLOGY,
        UserCategory.ARTIFICIAL_INTELLIGENCE to NewsAPICategory.TECHNOLOGY,
        UserCategory.TECHNOLOGY to NewsAPICategory.TECHNOLOGY,
        UserCategory.ART to NewsAPICategory.ENTERTAINMENT,
        UserCategory.MOVIE to NewsAPICategory.ENTERTAINMENT,
        UserCategory.TRAVEL to NewsAPICategory.ENTERTAINMENT,
        UserCategory.FASHION to NewsAPICategory.ENTERTAINMENT,
        UserCategory.FOOD to NewsAPICategory.HEALTH,
        UserCategory.ENVIRONMENT to NewsAPICategory.SCIENCE,
        UserCategory.FINANCE to NewsAPICategory.BUSINESS,
        UserCategory.SPORT to NewsAPICategory.SPORTS,
        UserCategory.EDUCATION to NewsAPICategory.SCIENCE
)