package com.example.testmpandroidchart.source

class DataModel {
    val data = listOf(
        SensorData(TypeSensorEnum.TEMP,"Гостиная", "25","85",4),
        SensorData(TypeSensorEnum.WATER,"Кухня", "23","64",5, StatusSensorEnum.ALERT),
        SensorData(TypeSensorEnum.WATER,"Спальня", "27","74",3, StatusSensorEnum.ENABLED),
        SensorData(TypeSensorEnum.TEMP,"Детская", "15","80",2),
        SensorData(TypeSensorEnum.TEMP,"Оранжерея", "28","71",4),
        SensorData(TypeSensorEnum.WATER,"Кабинет", "20","79",1, StatusSensorEnum.DISABLE)
    )
}

