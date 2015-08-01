package com.moses.custom;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.moses.R;

public class GroupListItem extends View {

    // Attr
    private String tittle;
    private Integer balance;
    private String value;

    // paint for drawing custom view
    private Paint circle;
    private Paint tittlePaint;
    private Paint valuePaint;
    private Paint imagePaint;
    private Bitmap bitmap;

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;

        // redraw the view
        invalidate();
        requestLayout();
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;

        // redraw the view
        invalidate();
        requestLayout();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;

        // redraw the view
        invalidate();
        requestLayout();
    }

    public GroupListItem(Context context, AttributeSet attrs) {
        super(context);
        // paint object for drawing in onDraw
        circle = new Paint();
        tittlePaint = new Paint();
        valuePaint = new Paint();
        imagePaint = new Paint();

        // get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.GroupListItem, 0, 0);

        try {
            // get attrs specified in attrs.xml
            tittle = a.getString(R.styleable.GroupListItem_tittle);
            value = a.getString(R.styleable.GroupListItem_value);
            balance = a.getInteger(R.styleable.GroupListItem_balance, 0);

        } finally {
            a.recycle();
        }

        Resources res = getResources();
        bitmap = BitmapFactory.decodeResource(res, R.drawable.default_avatar);
        bitmap = getCroppedBitmap(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw the View
        // get half of the width and height as we are working with a circle
        int viewWidth = this.getMeasuredWidth();
        int viewHeightHalf = (int) (1.1 * (this.getMeasuredHeight() / 2));

        // get the radius as half of the width or height, whichever is smaller
        // subtract ten so that it has some space around it
        int radius = 20;

        circle.setStyle(Style.FILL);
        circle.setAntiAlias(true);

        // set the paint color using the circle color specified
        valuePaint.setTextSize(30);
        valuePaint.setColor(Color.parseColor("#6E6E6E"));
        valuePaint.setTextAlign(Paint.Align.RIGHT);
        if (balance == 1) {
            circle.setColor(Color.parseColor("#4B9242"));
            canvas.drawCircle((float) (0.95 * viewWidth), viewHeightHalf - 15,
                    radius, circle);
            canvas.drawText(value, (float) (0.9 * viewWidth), viewHeightHalf,
                    valuePaint);
        } else if (balance == -1) {
            circle.setColor(Color.parseColor("#A61D1D"));
            canvas.drawCircle((float) (0.95 * viewWidth), viewHeightHalf - 15,
                    radius, circle);
            canvas.drawText(value, (float) (0.9 * viewWidth), viewHeightHalf,
                    valuePaint);
        } else {
            circle.setColor(Color.parseColor("#E1E1E1"));
            canvas.drawCircle((float) (0.95 * viewWidth), viewHeightHalf - 15,
                    radius, circle);
            canvas.drawText("Settled up", (float) (0.9 * viewWidth),
                    viewHeightHalf, valuePaint);
        }

        tittlePaint.setColor(Color.parseColor("#6E6E6E"));
        tittlePaint.setTextSize(40);
        tittlePaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(tittle, (float) (0.2 * viewWidth), viewHeightHalf,
                tittlePaint);

        canvas.drawBitmap(bitmap, (float) (0.05 * viewWidth),
                (float) (viewHeightHalf - bitmap.getHeight() / 1.5), imagePaint);
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        // Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        // return _bmp;
        return output;
    }
}
