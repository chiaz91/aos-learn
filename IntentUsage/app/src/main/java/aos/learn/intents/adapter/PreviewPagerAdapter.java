package aos.learn.intents.adapter;

import android.net.Uri;
import android.view.View;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
public class PreviewPagerAdapter extends PagerAdapter{
    Uri[] uris;

    public PreviewPagerAdapter(Uri... uris){
        this.uris = uris;
    }

    @Override
    public int getCount() {
        return uris.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView v = new ImageView(container.getContext());
        v.setImageURI(uris[position]);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
