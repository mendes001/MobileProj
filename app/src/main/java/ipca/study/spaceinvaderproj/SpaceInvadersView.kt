package ipca.study.spaceinvaderproj

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.RectF
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceView

class SpaceInvadersView(context: Context,
                        private val size: Point)
    : SurfaceView(context),
    Runnable {

    private val gameThread = Thread(this)

    private var playing = false

    private var paused = true

    private var canvas: Canvas = Canvas()
    private val paint: Paint = Paint()
    private val playerPaint: Paint = Paint()

    private var playerShip: PlayerShip = PlayerShip(context, size.x, size.y-100)

    private val invaders = ArrayList<Invader>()
    private var numInvaders = 0

    private var playerBullet = Bullet(size.y, 1300f, 40f)

    private val invadersBullets = ArrayList<Bullet>()
    private var nextBullet = 0
    private val maxInvaderBullets = 10

    private var score = 0

    private var waves = 1

    private var lives = 3


    private fun prepareLevel() {

        Invader.numberOfInvaders = 0
        numInvaders = 0
        for (column in 0..10) {
            for (row in 0..5) {
                invaders.add(Invader(context,
                    row,
                    column,
                    size.x,
                    size.y))

                numInvaders++
            }
        }

        for (i in 0 until maxInvaderBullets) {
            invadersBullets.add(Bullet(size.y))
        }
    }

    override fun run() {

        var fps: Long = 0

        while (playing) {

            val startFrameTime = System.currentTimeMillis()

            if (!paused) {
                update(fps)
            }

            draw()

            val timeThisFrame = System.currentTimeMillis() - startFrameTime
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame
            }

        }
    }

    private fun update(fps: Long) {

        playerShip.update(fps)

        var bumped = false

        var lost = false

        for (invader in invaders) {

            if (invader.isVisible) {

                invader.update()

                if (invader.takeAim(playerShip.position.left,
                        playerShip.width,
                        waves)) {

                    if (invadersBullets[nextBullet].shoot(invader.position.left
                                + invader.width / 2,
                            invader.position.top, playerBullet.down)) {

                        nextBullet++

                        if (nextBullet == maxInvaderBullets) {
                            nextBullet = 0
                        }
                    }
                }

                if (invader.position.left > size.x - invader.width
                    || invader.position.left < 0) {

                    bumped = true

                }
            }
        }

        if (playerBullet.isActive) {
            playerBullet.update(fps)
        }

        for (bullet in invadersBullets) {
            if (bullet.isActive) {
                bullet.update(fps)
            }
        }

        if (bumped) {

            for (invader in invaders) {
                invader.dropDownAndReverse(waves)
                if (invader.position.bottom >= size.y && invader.isVisible) {
                    lost = true
                }
            }
        }

        if (playerBullet.position.bottom < 0) {
            playerBullet.isActive =false
        }

        for (bullet in invadersBullets) {
            if (bullet.position.top > size.y) {
                bullet.isActive = false
            }
        }

        if (playerBullet.isActive) {
            for (invader in invaders) {
                if (invader.isVisible) {
                    if (RectF.intersects(playerBullet.position, invader.position)) {
                        invader.isVisible = false

                        playerBullet.isActive = false
                        Invader.numberOfInvaders --
                        score += 10

                        if (Invader.numberOfInvaders == 0) {
                            paused = true
                            lives ++
                            invaders.clear()
                            invadersBullets.clear()
                            prepareLevel()
                            waves ++
                            break
                        }

                        break
                    }
                }
            }
        }

        for (bullet in invadersBullets) {
            if (bullet.isActive) {
                if (RectF.intersects(playerShip.position, bullet.position)) {
                    bullet.isActive = false
                    lives --

                    if (lives == 0) {
                        lost = true
                        break
                    }
                }
            }
        }

        if (lost) {
            paused = true

            val intent = Intent(this.context, GameOverActivity::class.java)
            intent.putExtra("score", this.score)
            this.context.startActivity(intent)
        }
    }

    private fun draw() {

        if (holder.surface.isValid) {

            canvas = holder.lockCanvas()

            canvas.drawColor(Color.argb(255, 0, 0, 0))

            paint.color = Color.argb(255, 255, 60, 0)
            playerPaint.color = Color.argb(255, 0, 120, 255)

            canvas.drawBitmap(playerShip.bitmap, playerShip.position.left,
                playerShip.position.top
                , playerPaint)

            for (invader in invaders) {
                if (invader.isVisible) {
                    canvas.drawBitmap(Invader.bitmap,
                        invader.position.left,
                        invader.position.top,
                        paint)
                }
            }

            if (playerBullet.isActive) {
                canvas.drawRect(playerBullet.position, playerPaint)
            }

            for (bullet in invadersBullets) {
                if (bullet.isActive) {
                    canvas.drawRect(bullet.position, paint)
                }
            }

            paint.color = Color.argb(255, 255, 255, 255)
            paint.textSize = 70f
            canvas.drawText("Score: $score   Lives: $lives Wave: " +
                    "$waves", 20f, 75f, paint)

            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun pause() {
        playing = false
        try {
            gameThread.join()
        } catch (e: InterruptedException) {
            Log.e("Error:", "joining thread")
        }

    }

    fun resume() {
        playing = true
        prepareLevel()
        gameThread.start()
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        val movArea = size.y - (size.y / 8)
        when(motionEvent.action){
            MotionEvent.ACTION_DOWN-> {
                paused = false

                if (motionEvent.y > movArea) {
                    if (motionEvent.x > size.x / 2) {
                        playerShip.moving = PlayerShip.right
                    } else {
                        playerShip.moving = PlayerShip.left
                    }

                }

                if (motionEvent.y < movArea) {

                    playerBullet.shoot(
                        playerShip.position.left + playerShip.width / 2f,
                        playerShip.position.top,
                        playerBullet.up)
                }
            }

            MotionEvent.ACTION_UP -> {
                if (motionEvent.y > movArea) {
                    playerShip.moving = PlayerShip.stopped
                }
            }

        }
        return true
    }

}