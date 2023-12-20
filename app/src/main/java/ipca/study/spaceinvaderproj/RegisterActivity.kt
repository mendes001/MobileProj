package ipca.study.spaceinvaderproj

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import ipca.study.spaceinvaderproj.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegister.setOnClickListener {

            val email = binding.editTextTextEmailAddress2.text.toString()
            val password = binding.editTextTextPassword2.text.toString()
            val password2 = binding.editTextTextPassword3.text.toString()

            if (password != password2) {
                Toast.makeText(
                    baseContext,
                    "Passwords do not match",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }
            if (!password.isPasswordValid()){
                Toast.makeText(
                    baseContext,
                    "Password must have at least 6 chars.",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }
            if (!email.isEmailValid()){
                Toast.makeText(
                    baseContext,
                    "Introduce a valid email!",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){ task ->
                    if (task.isSuccessful) {
                        val intent = Intent (this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else{
                        Log.w(TAG, "Failure creating a new user!", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.${task.exception}",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }

    }

}