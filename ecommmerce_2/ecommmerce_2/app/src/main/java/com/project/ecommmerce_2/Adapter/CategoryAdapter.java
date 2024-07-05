package com.project.ecommmerce_2.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.project.ecommmerce_2.Category.CategoryFragment;
import com.project.ecommmerce_2.Model.CategoryModel;
import com.project.ecommmerce_2.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private CategoryFragment categoryFragment;
    private List<CategoryModel> categoryList;
    private int selectedPosition = RecyclerView.NO_POSITION; /*(Ini valuenya sama dengan -1) untuk tracking position*/

    public CategoryAdapter(CategoryFragment categoryFragment, List<CategoryModel> categoryList) {
        this.categoryFragment = categoryFragment;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(categoryFragment.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel category = categoryList.get(position);
        holder.textViewName.setText(category.getNama());

        if(selectedPosition == position){
            holder.cv.setCardBackgroundColor(ContextCompat.getColor(categoryFragment.getContext(), R.color.darkergrey_2));
        } else {
            holder.cv.setCardBackgroundColor(ContextCompat.getColor(categoryFragment.getContext(), R.color.lightergrey));
        }
        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = position;
                notifyDataSetChanged();


                if (category.getId() == -1){
                    categoryFragment.fetchData();
                } else {
                    categoryFragment.fetchDataByCategory(category.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        CardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.txtCategory);
            cv = itemView.findViewById(R.id.ItemCardView);
        }
    }
}
