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

import com.Foodiegraphy.editor.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ja.burhanrashid52.photoeditor.PhotoFilter;

public class Brightness extends RecyclerView.Adapter<PhotoEdit.Foodiegraphy.features.BrightnessNfilters.Brightness.ViewHolder>
    {

        private brig bListener;
        private List<Pair<String, PhotoFilter>> mPairList = new ArrayList<>();

        public Brightness(brig brig) {
            bListener = brig;
            setupFilters();
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoEdit.Foodiegraphy.features.BrightnessNfilters.Brightness.ViewHolder holder, int position)
        {
            Pair<String, PhotoFilter> filterPair = mPairList.get(position);
            Bitmap fromAsset = getBitmapFromAsset(holder.itemView.getContext(), filterPair.first);
            holder.mImageFilterView.setImageBitmap(fromAsset);
            holder.mTxtFilterName.setText(filterPair.second.name().replace("_", " "));
        }

        @Override
        public PhotoEdit.Foodiegraphy.features.BrightnessNfilters.Brightness.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_view, parent, false);
            return new PhotoEdit.Foodiegraphy.features.BrightnessNfilters.Brightness.ViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return mPairList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView mImageFilterView;
            TextView mTxtFilterName;

            ViewHolder(View itemView) {
                super(itemView);
                mImageFilterView = itemView.findViewById(R.id.imgFilterView);
                mTxtFilterName = itemView.findViewById(R.id.txtFilterName);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bListener.onFilterSelected(mPairList.get(getLayoutPosition()).second);
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
            mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/brightness.png", PhotoFilter.NONE));
            mPairList.add(new Pair<>("BrightnessFiltersSaturationFlips/brightness.png", PhotoFilter.BRIGHTNESS));

        }
    }


