package com.example.examentecnicoelektraapp.slider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.example.examentecnicoelektraapp.ProductsQuery;
import com.example.examentecnicoelektraapp.R;
import com.example.examentecnicoelektraapp.pojos.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    private Context context;
    private List<Producto> imagenes;

    public PagerAdapter(Activity activity, List<ProductsQuery.Product> products) {
        this.context = activity.getApplicationContext();
        List<Producto> imagenes = new ArrayList<>();
        for (ProductsQuery.Product producto : products)
            imagenes.add(
                    new Producto("", Objects.requireNonNull(producto.name()), Objects.requireNonNull(producto.image()),
                            Objects.requireNonNull(producto.price()).toString(), ""
                    )
            );
        this.imagenes = imagenes;
    }

    @Override
    public int getCount() {
        return this.imagenes.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //return super.instantiateItem(container, position);

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.custom_item_slide, null);
        ImageView image = view.findViewById(R.id.imageslider);
        TextView titulo =  view.findViewById(R.id.nameslider);
        TextView price = view.findViewById(R.id.priceslider);
        titulo.setText(imagenes.get(position).getName());
        price.setText("$".concat(imagenes.get(position).getPrice()));
        Glide.with(context).load(imagenes.get(position).getImage()).into(image);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
