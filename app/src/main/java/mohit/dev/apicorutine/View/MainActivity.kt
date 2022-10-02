package mohit.dev.apicorutine.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mohit.dev.apicorutine.Api_Interface.Api_interface
import mohit.dev.apicorutine.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val BASE_URL="https://jsonplaceholder.typicode.com"

data class Post(val id: Int, val userId: Int, val title: String)
data class User(val id: Int, val name: String, val email: String)

class MainActivity : AppCompatActivity() {
   lateinit var TextView:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn=findViewById<Button>(R.id.button)
        TextView=findViewById(R.id.textView)

        btn.setOnClickListener {
            doApiRequest()
        }

    }

    private fun doApiRequest() {
        val retrofit=Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val blogService=retrofit.create(Api_interface::class.java)
        //Launching Coroutine in background thread
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("Api_Requestcall","Coroutine thread name ${Thread.currentThread().name}")
            try {
                val blogPost = blogService.getPost(1)
                val user = blogService.getUser(blogPost.userId)
                val postsByUser = blogService.getPostsByUser(user.id)


                //As we cant use bg thread to update our ui
                withContext(Dispatchers.Main){
                    TextView.text = "User ${user.name} made ${postsByUser.size} posts"
                }

            } catch (exception: Exception) {
                Log.e("Api_Requestcall", "Exception $exception")

            }

        }

    }
}