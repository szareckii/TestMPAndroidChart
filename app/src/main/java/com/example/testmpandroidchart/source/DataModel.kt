package com.example.testmpandroidchart.source

class DataModel {
    val data = listOf(
        Data(TypeSensorEnum.TEMP,"Гостиная", "25","85",4),
        Data(TypeSensorEnum.WATER,"Кухня", "23","64",5, StatusSensorEnum.ALERT),
        Data(TypeSensorEnum.WATER,"Спальня", "27","74",3, StatusSensorEnum.ENABLED),
        Data(TypeSensorEnum.TEMP,"Детская", "15","80",2),
        Data(TypeSensorEnum.TEMP,"Кабинет", "28","71",4),
        Data(TypeSensorEnum.BOILER,"Кладовая", "16","59",status = StatusSensorEnum.ENABLED)
    )
}

