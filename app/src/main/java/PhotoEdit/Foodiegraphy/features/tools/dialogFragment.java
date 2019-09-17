package PhotoEdit.Foodiegraphy.features.tools;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;


import com.Foodiegraphy.editor.R;



public class dialogFragment extends DialogFragment {

    public static final String TAG = dialogFragment.class.getSimpleName();
    public static final String EXTRA_INPUT_TEXT = "extra_input_text";
    public static final String EXTRA_COLOR_CODE = "extra_color_code";
    private InputMethodManager mInputMethodManager;
    private int mColorCode;
    private TextEditor mTextEditor;

    public interface TextEditor {
        void onDone(String inputText, int colorCode);
    }



    public static dialogFragment show(@NonNull AppCompatActivity appCompatActivity,
                                      @NonNull String inputText,
                                      @ColorInt int colorCode) {
        Bundle args = new Bundle();
        args.putString(EXTRA_INPUT_TEXT, inputText);
        args.putInt(EXTRA_COLOR_CODE, colorCode);
        dialogFragment fragment = new dialogFragment();
        fragment.setArguments(args);
        fragment.show(appCompatActivity.getSupportFragmentManager(), TAG);
        return fragment;
    }


    public static dialogFragment show(@NonNull AppCompatActivity appCompatActivity) {
        return show(appCompatActivity,
                "", ContextCompat.getColor(appCompatActivity, R.color.white));
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);


    }


    public void setOnTextEditorListener(TextEditor textEditor)
    {
        mTextEditor = textEditor;
    }
}
