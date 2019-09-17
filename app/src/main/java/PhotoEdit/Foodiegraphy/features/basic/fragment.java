package PhotoEdit.Foodiegraphy.features.basic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


public abstract class fragment extends Fragment
{

    protected abstract int getLayoutId();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (getLayoutId() == 0)
        {
            throw new IllegalArgumentException("Invalid_id");
        }
        return inflater.inflate(getLayoutId(), container, false);
    }
}
