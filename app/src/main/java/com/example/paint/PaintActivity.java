package com.example.paint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.Stack;
import java.util.UUID;

public class PaintActivity extends AppCompatActivity {
    private static final String TAG = "PaintActivity";
    private FrameLayout frame;
    private PaintView paintView;
    private Button clear, saveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        frame = findViewById(R.id.frm);
        paintView = new PaintView(this);
        frame.addView(paintView);
        clear = (Button) findViewById(R.id.btnPoint);
        clear.setOnClickListener(undoLast);
        clear.setOnLongClickListener(clearPathsListener);
        saveBtn = (Button) findViewById(R.id.btnSave);
    }

    private View.OnClickListener undoLast = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            paintView.undo();
        }
    };

    private View.OnLongClickListener clearPathsListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            paintView.undoPaths();
            return true;
        }
    };

    public void addLine(View view) {
        paintView.addLine();
    }

    public void addRect(View view) {
        paintView.addRect();
    }

    public void addPath(View view) {
        paintView.addPath();
    }

    public void addCircle(View view) {
        paintView.addCircle();
    }

    public void changeColor(View view) {
        String color = view.getTag().toString();
        paintView.setColor(color);
    }

    public void changeFill(View view) {
            switch(view.getId()) {
                case R.id.btnFill:
                    paintView.fillStyle(true);
                    break;
                case R.id.btnUnFill:
                    paintView.fillStyle(false);
                    break;
            }
    }

    public void changeWidth(View view) {
        switch (view.getId()) {
            case R.id.btnMore:
                paintView.setStrokeWidth(true);
                break;
            case R.id.btnLess:
                paintView.setStrokeWidth(false);
                break;
        }
    }

    public void keepBiggest(View view){
        if(paintView.keepBiggest() == false)
            Toast.makeText(this, "No shapes with area found", Toast.LENGTH_LONG).show();
    }

    public void saveImage(View view){
        paintView.setDrawingCacheEnabled(true);
        String imageSaved = MediaStore.Images.Media.insertImage(
                getContentResolver(), paintView.getDrawingCache(),
                UUID.randomUUID().toString() + ".png", "drawing");
    }

}
