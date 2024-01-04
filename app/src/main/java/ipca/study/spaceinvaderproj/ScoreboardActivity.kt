package ipca.study.spaceinvaderproj

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ScoreboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    val adapter = ScoreAdapter()

    val scoreBoard : MutableList<Pair<String, Int>> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoreboard)
        val listView = findViewById<ListView>(R.id.ListViewScore)
        listView.adapter = adapter
        firestore = FirebaseFirestore.getInstance()

        firestore.collection("scores")
            .get()
            .addOnCompleteListener{
                for (scoreDoc in it.result) {
                    scoreBoard.add(Pair(scoreDoc.id, scoreDoc.getLong("score")!!.toInt()))
                }

                findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                listView.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
            }

    }
    inner class ScoreAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return scoreBoard.count()
        }

        override fun getItem(position: Int): Any {
            return scoreBoard[position]
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getView(position: Int, p1: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.activity_score_row, parent, false)
            val textEmail = rootView.findViewById<TextView>(R.id.EmailView)
            val textScore = rootView.findViewById<TextView>(R.id.ScoreView)
            val score = scoreBoard[position]

            textEmail.text = score.first
            textScore.text = "Score: ${score.second}"

            return rootView
        }

    }
}