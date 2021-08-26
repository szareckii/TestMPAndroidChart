package com.example.testmpandroidchart.source

data class Data(
    val type: TypeSensorEnum = TypeSensorEnum.TEMP,
    val labelPlace: String = "Гостиная",
    val temp: String = "25",
    val humidity: String = "85",
    val battery: Int = 5,
    val status: StatusSensorEnum? = null
)

