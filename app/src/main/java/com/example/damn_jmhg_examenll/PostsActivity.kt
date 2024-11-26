package com.example.damn_jmhg_examenll

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.damn_jmhg_examenll.api.repositories.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostsActivity : AppCompatActivity() {
    private val postsRepository = PostRepository()

    lateinit var ibRegresarPosts: ImageButton;
    lateinit var rvPosts: RecyclerView;

    private lateinit var adapterPost: AdapterRecylerPost;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_posts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ibRegresarPosts = findViewById(R.id.ibRegresarPosts)
        rvPosts = findViewById(R.id.rvPosts)

        adapterPost = AdapterRecylerPost(emptyList())

        rvPosts.adapter = adapterPost
        rvPosts.layoutManager = LinearLayoutManager(this)

        val id = intent?.extras?.getLong("id")
        obtenerPosts(id!!)

        adapterPost.setOnItemClickListener(object : AdapterRecylerPost.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //Toast.makeText(this@MainActivity, "Click en ${adapterUser.users[position].name}", Toast.LENGTH_LONG).show()
                val intentComments = Intent(this@PostsActivity, CommentActivity::class.java)
                intentComments.putExtra("postId", adapterPost.posts[position].id)
                startActivity(intentComments)
            }
        })

        ibRegresarPosts.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun obtenerPosts(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val posts = postsRepository.getPostsByUserId(id)
            withContext(Dispatchers.Main) {
                adapterPost.posts = posts
                adapterPost.notifyDataSetChanged()
            }
        }
    }
}