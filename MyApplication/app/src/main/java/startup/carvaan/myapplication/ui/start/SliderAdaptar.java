package startup.carvaan.myapplication.ui.start;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import startup.carvaan.myapplication.R;

public class SliderAdaptar extends PagerAdapter {

    Context mContext;
    LayoutInflater layoutInflater;

    public SliderAdaptar(Context mContext) {
        this.mContext = mContext;
    }


    public int[] slide_images = {
            R.drawable.connect,
           R.drawable.investing,
            R.drawable.moneyflow
    };

    public String[] slide_headings = {
            "Connect with StartUps",
            "Invest Without money in StartUps",
            "Get Real Money"

    };

    public String[] slide_descriptions = {
            "Feel Free to connect with the new emerging StartUps, get to know about them ,their work,their blogs and to trade with whosoever you'd like",
            "Invest in the StartUp of your choice without real money  and help in the raising of a StartUp.",
            "Get real Money from trading in StartUps or by achieving your targets and  get real life benefits also gets a chance to get intern and free lancing in them.  "
    };



    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImage = view.findViewById(R.id.slideImage);
        TextView slideHeading = view.findViewById(R.id.slideHeading);
        TextView slideDescription = view.findViewById(R.id.slideDescription);

        slideImage.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descriptions[position]);


        container.addView(view);




        return view;

    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);
    }
}
