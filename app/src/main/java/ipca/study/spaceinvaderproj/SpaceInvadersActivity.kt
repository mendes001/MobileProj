package ipca.study.spaceinvaderproj

import android.app.Activity
import android.graphics.Point
import android.os.Bundle

class SpaceInvadersActivity : Activity() {

    private var spaceInvadersView: SpaceInvadersView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val display = windowManager.defaultDisplay

        val size = Point()
        display.getSize(size)

        spaceInvadersView = SpaceInvadersView(this, size)
        setContentView(spaceInvadersView)
    }

    override fun onResume() {
        super.onResume()

        spaceInvadersView?.resume()
    }

    override fun onPause() {
        super.onPause()

        spaceInvadersView?.pause()
    }

}