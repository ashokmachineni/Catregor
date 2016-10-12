package com.ashok.catregor;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ashok.catregor.MainActivity.CATEGORY;
import static com.ashok.catregor.MainActivity.CATEGORY_BLOGS;

public class CategoryBlogActivity extends AppCompatActivity {

    private RecyclerView mCategoryBlogList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_category);
        mCategoryBlogList = (RecyclerView) findViewById(R.id.category_blog_list);
        mCategoryBlogList.setHasFixedSize(true);
        mCategoryBlogList.setLayoutManager(new LinearLayoutManager(this));
        final List<Blog> blogList = (ArrayList<Blog>)getIntent().getSerializableExtra(CATEGORY_BLOGS);
        setTitle(getIntent().getStringExtra(CATEGORY));
        mCategoryBlogList.setAdapter(new RecyclerView.Adapter<BlogViewHolder>() {
            @Override
            public BlogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new BlogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_row, parent, false));
            }

            @Override
            public void onBindViewHolder(BlogViewHolder holder, int position) {
                Blog blog = blogList.get(position);
                holder.setImage(blog.getImage());
                holder.setTitle(blog.getTitle());
            }

            @Override
            public int getItemCount() {
                return blogList.size();
            }
        });
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public BlogViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }
        public void setTitle(String title){
            TextView post_title = (TextView) mView.findViewById(R.id.post_title);
            post_title.setText(title);

        }

        public void setImage(String image){
            ImageView post_image = (ImageView) mView.findViewById(R.id.post_image);
            Picasso.with(mView.getContext()).load(image).into(post_image);

        }

    }
}
