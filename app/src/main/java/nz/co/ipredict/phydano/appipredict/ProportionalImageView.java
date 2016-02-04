package nz.co.ipredict.phydano.appipredict;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by phydano on 5/02/2016.
 * I have obtained the code from here: http://www.ryadel.com/en/android-proportionally-stretch-imageview-fit-whole-screen-width-maintaining-aspect-ratio/
 * Basically this class is to help with scaling image proportionally to display on different screen resolution
 */
public class ProportionalImageView extends ImageView {

    public ProportionalImageView (Context context){
        super(context);
    }

    public ProportionalImageView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public ProportionalImageView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        Drawable d = getDrawable();
        if(d != null){
            int w = MeasureSpec.getSize(widthMeasureSpec);
            int h = w * d.getIntrinsicHeight() / d.getIntrinsicWidth();
            setMeasuredDimension(w, h);
        }
        else super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
