package com.example.damn_jmhg_examenll

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.damn_jmhg_examenll.api.repositories.CommentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentActivity : AppCompatActivity() {
    private val commentRepository = CommentRepository()

    lateinit var ibRegresarComentarios: ImageButton;
    lateinit var rvComentarios: RecyclerView;

    private lateinit var adapterComment: AdapterRecyclerComment;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ibRegresarComentarios = findViewById(R.id.ibRegresarComentarios)
        rvComentarios = findViewById(R.id.rvComentarios)

        adapterComment = AdapterRecyclerComment(emptyList())

        rvComentarios.adapter = adapterComment
        rvComentarios.layoutManager = LinearLayoutManager(this)

        val postId = intent?.extras?.getLong("postId")
        obtenerComentarios(postId!!)

        ibRegresarComentarios.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun obtenerComentarios(postId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = commentRepository.getCommentsByPostId(postId)
            withContext(Dispatchers.Main) {
                adapterComment.comments = response
                adapterComment.notifyDataSetChanged()
            }
        }
    }
}