package ipca.study.spaceinvaderproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ipca.study.spaceinvaderproj.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPlay.setOnClickListener {
            val intent = Intent(this@MainActivity, KotlinInvadersActivity::class.java)
            startActivity(intent)
        }
    }
}