package ipca.study.spaceinvaderproj

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.RectF

class PlayerShip(context: Context,
                 private val screenX: Int,
                 screenY: Int) {

    var bitmap: Bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.playership)

    val width = screenX / 15f
    private val height = screenY / 25f

    val position = RectF(
        screenX / 2f,
        screenY-height,
        screenX/2 + width,
        screenY.toFloat())

    private val speed  = 450f

    companion object {

        const val stopped = 0
        const val left = 1
        const val right = 2
    }

    var moving = stopped

    init{

        bitmap = Bitmap.createScaledBitmap(bitmap,
            width.toInt() ,
            height.toInt() ,
            false)
    }

    fun update(fps: Long) {
        if (moving == left && position.left > 0) {
            position.left -= speed / fps
        }

        else if (moving == right && position.left < screenX - width) {
            position.left += speed / fps
        }

        position.right = position.left + width
    }

}