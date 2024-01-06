package ipca.study.spaceinvaderproj

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import ipca.study.spaceinvaderproj.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPlay.setOnClickListener {
            val intent = Intent(this@MainActivity, SpaceInvadersActivity::class.java)
            startActivity(intent)
        }
        binding.buttonExit.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.buttonScore.setOnClickListener {
            val intent = Intent(this@MainActivity, ScoreboardActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        val userEmail = user?.email

        val textviewuser = findViewById<TextView>(R.id.textViewuser)

        textviewuser.text = userEmail

    }
}