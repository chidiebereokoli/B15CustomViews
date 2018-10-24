//In this demo is about how we extend an EditText class to make a custom text
//editing view.
package com.example.chidi.b15customviews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

//[1]
public class EditTextWithClear extends AppCompatEditText {


    // The 3 constructors are as outlined below
    //Required for creating a new instance of a view programmatically
    //[2]
    public EditTextWithClear(Context context) {
        super(context);
        init();
    }
    //Required to create a view from the XML layout and apply XML attributes
    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    //This is required to apply a default style to the UI elements, without having to specify it in each layout file
    public EditTextWithClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    //here we initialize the custom view.

    Drawable mClearButtonImage;//Member varaible for the drawable X button

    //[4]
    private void init() {
    //Here we initialize the Member variable is initialized to the drawable resource ic_android_black_24dp
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(),
                R.drawable.ic_clear_black_24dp, null);
        // TODO: If the clear (X) button is tapped, clear the text.

        //This TouchListener responds to events inside the bounds of the button.
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if ((getCompoundDrawablesRelative()[2] != null)) {
//The expression above returns the drawable at the end of text[2]. If no drawable is present, the expression returns noll.
//This code executes only if the location is not null, which means that the clear(X) button is in that location. else, code returns false
                    float clearButtonStart; // Used for LTR languages
                    float clearButtonEnd;  // Used for RTL languages
                    boolean isClearButtonClicked = false;
                    // TODO: Detect the touch in RTL or LTR layout direction.
                    //This is used to determine if a touch occurs after the start of the button in an LTR layout, or before the end of the
                    //button, in an RTL layout
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                        // If RTL, get the end of the button on the left side.
                        clearButtonEnd = mClearButtonImage
                                .getIntrinsicWidth() + getPaddingStart();
                        // If the touch occurred before the end of the button,
                        // set isClearButtonClicked to true.
                        if (event.getX() < clearButtonEnd) {
                            isClearButtonClicked = true;
                        }
                    } else {
                        // Layout is LTR.
                        // Get the start of the button on the right side.
                        clearButtonStart = (getWidth() - getPaddingEnd()
                                - mClearButtonImage.getIntrinsicWidth());
                        // If the touch occurred after the start of the button,
                        // set isClearButtonClicked to true.
                        if (event.getX() > clearButtonStart) {
                            isClearButtonClicked = true;
                        }
                    }
                    // TODO: Check for actions if the button is tapped.


                    /*The first touch event is ACTION_DOWN. Use it to check if the clear (X) button is touched (ACTION_DOWN).
                            If it is, switch the clear button to the black version.
                            The second touch event, ACTION_UP occurs when the gesture is finished.
                    Your code can then clear the text, hide the clear (X) button, and return true. Otherwise the code returns false.*/


                    if (isClearButtonClicked) {
                        // Check for ACTION_DOWN (always occurs before ACTION_UP).
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            // Switch to the black version of clear button.
                            mClearButtonImage =
                                    ResourcesCompat.getDrawable(getResources(),
                                            R.drawable.ic_clear_black_24dp, null);
                            showClearButton();
                        }
                        // Check for ACTION_UP.
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            // Switch to the opaque version of clear button.
                            mClearButtonImage =
                                    ResourcesCompat.getDrawable(getResources(),
                                            R.drawable.ic_clear_opaque_24dp, null);
                            // Clear the text and hide the clear button.
                            getText().clear();
                            hideClearButton();
                            return true;
                        }
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });

        // TODO: If the text changes, show or hide the clear (X) button.
        //[5]
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //Only onText changed will be used in this demo, afterTextChanged and beforeTextChanged will not be used.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    /**
     * Shows the clear (X) button., when the user enters text
     */
    //If there is no text, the EditTextWithClear custom view hides the clear (X) button
    //[2]
    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds //THis method is used to show or hide the Clear (X) button.
                //The method returns the exact size of the drawable
                (null,                      // Start of text.
                        null,               // Above text.
                        mClearButtonImage,  // End of text.
                        null);              // Below text.
    }

    /**
     * Hides the clear button.
     */
    //[2]
    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,             // Start of text.
                        null,      // Above text.
                        null,      // End of text.
                        null);     // Below text.
    }
}
