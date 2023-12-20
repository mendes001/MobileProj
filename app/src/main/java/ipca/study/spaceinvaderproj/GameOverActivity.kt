package ipca.study.spaceinvaderproj

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ipca.study.spaceinvaderproj.databinding.ActivityGameOverBinding

class GameOverActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGameOverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = this.intent.getIntExtra("score", 0)
        //firebaser

        binding.buttonPlayAgain.setOnClickListener {
            val intent = Intent(this@GameOverActivity, KotlinInvadersActivity::class.java)
            startActivity(intent)
        }

        binding.buttonMenu.setOnClickListener {
            val intent = Intent(this@GameOverActivity, MainActivity::class.java)
            startActivity(intent)
        }



    }
}