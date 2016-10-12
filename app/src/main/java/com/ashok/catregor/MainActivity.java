package com.ashok.catregor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String CATEGORY_BLOGS = "CATEGORY_BLOGS";
    public static final String CATEGORY = "CATEGORY";
    private RecyclerView mCategoryList;
    private Map<String, List<Blog>> categoryToBlogMap = new HashMap<>();

    private final ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            categoryToBlogMap.clear();
            for (DataSnapshot snapShot : dataSnapshot.getChildren()) {
                Blog blog = snapShot.getValue(Blog.class);
                List<Blog> categoryBlogList = categoryToBlogMap.get(blog.getCategory());
                if (categoryBlogList == null) {
                    categoryBlogList = new ArrayList<>();
                    categoryToBlogMap.put(blog.getCategory(), categoryBlogList);
                }
                categoryBlogList.add(blog);
            }
            provideAdapter();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void provideAdapter() {
        final List<String> categories = new ArrayList<>(categoryToBlogMap.keySet());
        mCategoryList.setAdapter(new RecyclerView.Adapter<CategoryViewHolder>() {
            @Override
            public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category, parent, false));
            }

            @Override
            public void onBindViewHolder(CategoryViewHolder holder, int position) {
                holder.bind(categories.get(position));
            }

            @Override
            public int getItemCount() {
                return categories.size();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Movie");
        mCategoryList = (RecyclerView) findViewById(R.id.category_list);
        mCategoryList.setHasFixedSize(true);
        mCategoryList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase.addListenerForSingleValueEvent(valueEventListener);
    }

    private class CategoryViewHolder extends  RecyclerView.ViewHolder {

        private final TextView view;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            view = (TextView) itemView;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCategoryBlogs(view.getText().toString());
                }
            });
        }

        public void bind(String category) {
            view.setText(category);
        }
    }

    private void showCategoryBlogs(String s) {
        ArrayList<Blog> categoryBlogs = (ArrayList<Blog>)categoryToBlogMap.get(s);
        Intent intent = new Intent(this, CategoryBlogActivity.class);
        intent.putExtra(CATEGORY_BLOGS, categoryBlogs);
        intent.putExtra(CATEGORY, s);
        startActivity(intent);
    }
}
