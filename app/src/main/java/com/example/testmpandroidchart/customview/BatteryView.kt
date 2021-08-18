package com.example.testmpandroidchart.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.testmpandroidchart.R


class BatteryView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {

        private const val DEFAULT_BATTERY_COLOR = Color.GRAY
        private const val DEFAULT_BODY_BATTERY_COLOR = Color.GRAY
        // Отступ элементов
        private const val padding = 0f
        // Отступ элементов
        private const val outline = 4f
        // Отступ элементов у заряда
        private const val outlineLevel = 3f
        // Скругление углов
        private const val round = 5f
        // Скругление углов
        private const val roundBody = 3f
        // Ширина головы батареи
        private const val headWidth = 5
        private const val levelSizeBattery = 5
    }

    // Цвет батареи
    private var batteryColor = Color.GRAY

    // Цвет тела батареи
    private var bodyBatteryColor = Color.WHITE

    // Цвет уровня заряда
    private var levelColor = Color.GREEN

    // Уровень заряда
    private var levelBattery = 5

    // Изображение батареи
    private val batteryRectangle = RectF()

    // Изображение белого фона в батарее
    private val bodyBatteryRectangle = RectF()

    // Изображение уровня заряда
    private val levelRectangle = Rect()

    // Изображение головы батареи
    private val headRectangle = RectF()

    // "Краска" батареи
    private lateinit var batteryPaint: Paint

    // "Краска" батареи
    private lateinit var bodyBatteryPaint: Paint

    // "Краска" заряда
    private lateinit var levelPaint: Paint

    // Ширина элемента
    private var widthBattery = 0

    // Высота элемента
    private var heightBattery = 0

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BatteryView)
            batteryColor = typedArray.getColor(R.styleable.BatteryView_battery_color, DEFAULT_BATTERY_COLOR)
            levelColor = typedArray.getColor(R.styleable.BatteryView_level_color, DEFAULT_BODY_BATTERY_COLOR)
            levelBattery = typedArray.getInteger(R.styleable.BatteryView_level, 5);
            typedArray.recycle()
        }
        setup()
    }

    private fun setup() {
        batteryPaint = Paint()
        batteryPaint.color = batteryColor
        batteryPaint.style = Paint.Style.FILL
        bodyBatteryPaint = Paint()
        bodyBatteryPaint.color = bodyBatteryColor
        bodyBatteryPaint.style = Paint.Style.FILL
        levelPaint = Paint()
        levelPaint.color = levelColor
        levelPaint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // Получаем реальные ширину и высоту
        widthBattery = w - paddingLeft - paddingRight
        heightBattery = h - paddingTop - paddingBottom

        // Отрисовка контура батареи
        batteryRectangle.set(
            padding + headWidth,
            padding,
            widthBattery - padding,
            heightBattery - padding
        )

        // Отрисовка головы батареи
        headRectangle.set(
            padding,
           padding + outline + outlineLevel,
            padding + headWidth,
            heightBattery - padding - outline - outlineLevel
        )

        // Отрисовка тела батареи
        bodyBatteryRectangle.set(
             padding + outline + headWidth,
            padding + outline,
            widthBattery - padding - outline,
            heightBattery - padding - outline
        )

        levelRectangle.set(
            ((padding + outline + outlineLevel + headWidth +
                    (levelSizeBattery - levelBattery) * (widthBattery / (levelSizeBattery + 1))).toInt()),
            (padding + outline + outlineLevel).toInt(),
            (widthBattery - padding - outline - outlineLevel).toInt(),
            (heightBattery - padding - outline - outlineLevel).toInt()
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRoundRect(batteryRectangle, round, round, batteryPaint)
        canvas.drawRect(headRectangle, batteryPaint)
        canvas.drawRoundRect(bodyBatteryRectangle, roundBody, roundBody, bodyBatteryPaint)
        canvas.drawRect(levelRectangle, levelPaint)
    }

    fun setLevel(level: Int) {
        levelBattery = level
        invalidate()
    }
}