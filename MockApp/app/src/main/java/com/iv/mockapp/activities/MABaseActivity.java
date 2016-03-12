package com.iv.mockapp.activities;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.iv.mockapp.R;
import com.iv.mockapp.utils.view.MAViewUtil;

/**
 * Created by vineeth on 10/09/16
 */
public class MABaseActivity extends AppCompatActivity {

    public void showIndefiniteSnackBar(View view, String errorMessage, View.OnClickListener onClickListener) {
        if (errorMessage == null) {
            errorMessage = getString(R.string.unknown_error);
        }

        MAViewUtil.showIndefiniteSnackBar(view, errorMessage, onClickListener);
    }
}
