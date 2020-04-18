package com.example.blogit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mBlog;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Blog");
        mBlog=(RecyclerView)findViewById(R.id.Recycler_View);
        mBlog.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions Optins = new FirebaseRecyclerOptions.Builder<Blog>().setQuery(mDatabase,Blog.class).build();
        FirebaseRecyclerAdapter<Blog,BlogView> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogView>(Optins) {



            @NonNull
            @Override
            public BlogView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog,parent,false);
                return new BlogView(view);
            }

           @Override
           protected void onBindViewHolder(@NonNull BlogView holder, int position, @NonNull Blog model) {
               holder.SetTitle(model.getTitle());
               holder.SetStory(model.getStory());

           }



        };
        mBlog.setAdapter(firebaseRecyclerAdapter);
    }


    public static class BlogView extends RecyclerView.ViewHolder
    {
        View mView;
        public  BlogView(@NonNull View itemView) {
            super(itemView);
            mView=itemView;

        }

        public void SetTitle(String title)
        {
            TextView textView = (TextView)mView.findViewById(R.id.Post_title);
            textView.setText(title);
        }

        public void SetStory(String Story)
        {
            TextView textView = (TextView)mView.findViewById(R.id.Post_story);
            textView.setText(Story);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_button)
        {
            startActivity(new Intent(MainActivity.this,Post.class));

        }
        return super.onOptionsItemSelected(item);
    }
}
