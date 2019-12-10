package com.hkt.fcamp1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    public int blackImage = R.drawable.white_rec;
    public int greenImage = R.drawable.orange_rec;
    public int[] randomPosition;
    public int amount;
    public int status;
    Context ctx;
    ImageAdapter(Context context, int amount, int[]randomPosition, int status){
        this.randomPosition = randomPosition;
        this.amount = amount;
        this.ctx = context;
        this.status = status;
    }


    public boolean isEnabled(int _position){
        return true;
    }


    public int[] getRandomPosition() {
        return randomPosition;
    }

    @Override
    public int getCount() {
        return amount;
    }

    @Override
    public Object getItem(int position) {
        return  position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setRandomPosition(int[] randomPosition) {
        this.randomPosition = randomPosition;
    }


    @Override
    public View getView (final int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        // if it's not recycled, initialize some attributes
        if (convertView == null) {

            imageView = new ImageView(ctx);
            // Center crop image
    //        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        } else {
            imageView = (ImageView) convertView;
        }
        // Set images into ImageView
        if (status == 0) {
            imageView.setImageResource(blackImage);
            for (int i = 0; i < randomPosition.length; i++) {
                if (position == randomPosition[i]) {
                    imageView.setImageResource(greenImage);
                }
            }

        } else {
            imageView.setImageResource(blackImage);
        }


        imageView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 185));

        return imageView;
    }
}

