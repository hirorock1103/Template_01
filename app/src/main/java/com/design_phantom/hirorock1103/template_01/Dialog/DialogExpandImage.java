package com.design_phantom.hirorock1103.template_01.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.design_phantom.hirorock1103.template_01.R;

public class DialogExpandImage extends AppCompatDialogFragment {

    private ImageView image;
    private byte[] imageByte;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.f_image_area, null);

        try{
            imageByte = getArguments().getByteArray("image");
        }catch (Exception e){
            imageByte = null;
        }

        image = view.findViewById(R.id.image);

        if(imageByte != null && imageByte.length > 0){

            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            Matrix matrix = new Matrix();
            // 拡大比率
            float rsz_ratio_w = 3.0f;
            float rsz_ratio_h = 3.0f;
            // 比率をMatrixに設定
            matrix.postScale( rsz_ratio_w, rsz_ratio_h );

            //resize
            Bitmap resizeImage;
            resizeImage = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix,true);

            image.setImageBitmap(resizeImage);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(view)
                .setTitle(getString(R.string.dialogTitleExpandImage))
                .setNegativeButton(getString(R.string.cancel), null)
                .setPositiveButton(getString(R.string.OK), null);


        Dialog dialog = builder.create();

        return dialog;

    }
}
