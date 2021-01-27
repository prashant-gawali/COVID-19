package com.example.covid_19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.covid_19.model.ImageSliderModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<SliderViewHolder> {
    Context context;
    List<ImageSliderModel> imageSliderModels;

    public ImageSliderAdapter(Context context, List<ImageSliderModel> imageSliderModels) {
        this.context = context;
        this.imageSliderModels = imageSliderModels;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider,parent,false);

        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {

        viewHolder.imageView.setImageResource(imageSliderModels.get(position).getImage());

    }

    @Override
    public int getCount() {
        return imageSliderModels.size();
    }
}
class SliderViewHolder extends SliderViewAdapter.ViewHolder {
    ImageView imageView;

    public SliderViewHolder(View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.image_view);
    }
}
