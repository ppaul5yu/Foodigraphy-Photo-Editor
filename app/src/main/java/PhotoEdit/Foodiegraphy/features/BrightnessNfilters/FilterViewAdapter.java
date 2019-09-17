package PhotoEdit.Foodiegraphy.features.BrightnessNfilters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.Foodiegraphy.editor.R;



import ja.burhanrashid52.photoeditor.PhotoFilter;


public class FilterViewAdapter extends RecyclerView.Adapter<FilterViewAdapter.ViewHolder>
{

    private static filterLister mFilterListener;
    private static List<Pair<String, PhotoFilter>> mPairList = new ArrayList<>();

    public FilterViewAdapter(filterLister filterLister) {
        mFilterListener = filterLister;
        setupFilters();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Pair<String, PhotoFilter> filterPair = mPairList.get(position);
        Bitmap fromAsset = getBitmapFromAsset(holder.itemView.getContext(), filterPair.first);
        holder.mImageFilterView.setImageBitmap(fromAsset);
        holder.mTxtFilterName.setText(filterPair.second.name().replace("_", " "));
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mPairList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageFilterView;
        TextView mTxtFilterName;

        ViewHolder(View itemView) {
            super(itemView);
            mImageFilterView = itemView.findViewById(R.id.imgFilterView);
            mTxtFilterName = itemView.findViewById(R.id.txtFilterName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFilterListener.onFilterSelected(mPairList.get(getLayoutPosition()).second);
                }
            });
        }
    }

    private Bitmap getBitmapFromAsset(Context context, String strName) {
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
            return BitmapFactory.decodeStream(istr);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setupFilters() {
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/original.jpg", PhotoFilter.NONE));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/auto_fix.png", PhotoFilter.AUTO_FIX));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/bNw.png", PhotoFilter.BLACK_WHITE));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/brightness.png", PhotoFilter.BRIGHTNESS));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/contrast.png", PhotoFilter.CONTRAST));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/documentary.png", PhotoFilter.DOCUMENTARY));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/flip_horizental.png", PhotoFilter.FLIP_HORIZONTAL));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/flip_vertical.png", PhotoFilter.FLIP_VERTICAL));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/gray.png", PhotoFilter.GRAY_SCALE));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/more_light.png", PhotoFilter.FILL_LIGHT));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/posterize.png", PhotoFilter.POSTERIZE));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/rotate.png", PhotoFilter.ROTATE));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/saturate.png", PhotoFilter.SATURATE));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/sepia.png", PhotoFilter.SEPIA));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/more_light.png", PhotoFilter.FILL_LIGHT));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/sharpen.png", PhotoFilter.SHARPEN));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/temprature.png", PhotoFilter.TEMPERATURE));
        mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/vignette.png", PhotoFilter.VIGNETTE));


    }
}
