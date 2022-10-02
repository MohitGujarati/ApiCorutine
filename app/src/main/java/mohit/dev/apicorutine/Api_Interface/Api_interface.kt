package mohit.dev.apicorutine.Api_Interface

import mohit.dev.apicorutine.View.Post
import mohit.dev.apicorutine.View.User
import retrofit2.Call
import retrofit2.http.*

interface Api_interface {

    @GET("/posts/{id}")
    suspend fun getPost(@Path("id") postId: Int): Post

    @GET("/users/{id}")
    suspend fun getUser(@Path("id") userId: Int): User

    @GET("/users/{id}/posts")
    suspend fun getPostsByUser(@Path("id") userId: Int): List<Post>

}