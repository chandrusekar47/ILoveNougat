package cs.zappos.ilovenougat.databinding;

import android.databinding.BindingAdapter;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class CustomBindingAdapter {
    @BindingAdapter(value = "imageUrl")
    public static void setImageUrl(ImageView imageView, String imageUrl) {
        imageView.setImageDrawable(imageView.getContext().getDrawable(android.R.drawable.ic_menu_gallery));
        ImageLoader.getInstance().displayImage(imageUrl, imageView);
    }

    @BindingAdapter(value = "strikeThroughText")
    public static void setStrikeThroughText(TextView textView, String text) {
        textView.setText(text);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }
}
