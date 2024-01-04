package ipca.study.spaceinvaderproj

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ipca.study.spaceinvaderproj.databinding.ActivityGameOverBinding

class GameOverActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGameOverBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameOverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val score = this.intent.getIntExtra("score", 0)

        val userEmail = auth.currentUser?.email

        if (userEmail != null) {
            val scoreData = hashMapOf(
                "score" to score
            )

            firestore
                .collection("scores")
                .document(userEmail)
                .set(scoreData)
                .addOnSuccessListener { documentReference ->
                    println("suc")
                }
                .addOnFailureListener{e ->
                    println("fail ${e.message}")
                }
        }



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