/**
 *
 * Copyright 2016 Xiaofei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package xiaofei.library.gridlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Xiaofei on 16/1/26.
 */
public class GridLayout extends ViewGroup {

    private int mHorizontalSpacing = 0;

    private int mVerticalSpacing = 0;

    private int mNumColumns = 1;

    private int mNumRows = 1;

    public GridLayout(Context context) {
        this(context, null, 0);
    }

    public GridLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GridLayoutAttrs);
        mHorizontalSpacing = (int) a.getDimension(R.styleable.GridLayoutAttrs_horizontalSpacing, mHorizontalSpacing);
        mVerticalSpacing = (int) a.getDimension(R.styleable.GridLayoutAttrs_verticalSpacing, mVerticalSpacing);
        mNumColumns = a.getInteger(R.styleable.GridLayoutAttrs_numColumns, mNumColumns);
        mNumRows = a.getInteger(R.styleable.GridLayoutAttrs_numRows, mNumRows);
        a.recycle();
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        if (horizontalSpacing < 0) {
            throw new IllegalArgumentException("horizontalSpacing should not be less than 0.");
        }
        mHorizontalSpacing = horizontalSpacing;
        invalidate();
    }

    public void setVerticalSpacing(int verticalSpacing) {
        if (verticalSpacing < 0) {
            throw new IllegalArgumentException("verticalSpacing should not be less than 0.");
        }
        mVerticalSpacing = verticalSpacing;
        invalidate();
    }

    public void setNumColumns(int numColumns) {
        if (numColumns < 0) {
            throw new IllegalArgumentException("numColumns should not be less than 0.");
        }
        if (getChildCount() > mNumRows * numColumns) {
            throw new IllegalArgumentException("GridLayout will not be able to contain the children.");
        }
        mNumColumns = numColumns;
        invalidate();
    }

    public void setNumRows(int numRows) {
        if (numRows < 0) {
            throw new IllegalArgumentException("numRows should not be less than 0.");
        }
        if (getChildCount() > numRows * mNumColumns) {
            throw new IllegalArgumentException("GridLayout will not be able to contain the children.");
        }
        mNumRows = numRows;
        invalidate();
    }

    private boolean isFull() {
        return getChildCount() >= mNumColumns * mNumRows;
    }

    public void addView(View child) {
        if (isFull()) {
            throw new UnsupportedOperationException("GridLayout is full.");
        }
        super.addView(child);
    }

    public void addView(View child, int index) {
        if (isFull()) {
            throw new UnsupportedOperationException("GridLayout is full.");
        }
        super.addView(child, index);
    }

    public void addView(View child, int index, LayoutParams params) {
        if (isFull()) {
            throw new UnsupportedOperationException("GridLayout is full.");
        }
        super.addView(child, index, params);
    }

    public void addView(View child, LayoutParams params) {
        if (isFull()) {
            throw new UnsupportedOperationException("GridLayout is full.");
        }
        super.addView(child, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int count = getChildCount();
        int childState = 0;
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int width = MeasureSpec.getSize(widthMeasureSpec);// will always be a specific value.
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        int maxChildWidth = 0;
        int maxChildHeight = 0;
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        final int childWidth = Math.max(0, (width - paddingLeft - paddingRight - mHorizontalSpacing * (mNumColumns - 1)) / mNumColumns);
        final int childHeight = Math.max(0, (height - paddingTop - paddingBottom - mVerticalSpacing * (mNumRows - 1)) / mNumRows);
        int parentWidthSpec = MeasureSpec.makeMeasureSpec(childWidth, widthMode);
        int parentHeightSpec = MeasureSpec.makeMeasureSpec(childHeight, heightMode);
        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = child.getLayoutParams();
                final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthSpec, 0, lp.width);
                final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightSpec, 0, lp.height);
                child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                maxChildWidth = Math.max(maxChildWidth, child.getMeasuredWidth());
                maxChildHeight = Math.max(maxChildHeight, child.getMeasuredHeight());
                childState = ViewCompat.combineMeasuredStates(childState, ViewCompat.getMeasuredState(child));
            }
        }
        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            //maxChildWidth = Math.min()
            parentWidthSpec = MeasureSpec.makeMeasureSpec(maxChildWidth, widthMode);
            parentHeightSpec = MeasureSpec.makeMeasureSpec(maxChildHeight, heightMode);
            childState = 0;
            for (int i = 0; i < count; ++i) {
                final View child = getChildAt(i);
                if (child.getVisibility() != GONE) {
                    final LayoutParams lp = child.getLayoutParams();
                    final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthSpec, 0, lp.width);
                    final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightSpec, 0, lp.height);
                    child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
                    maxChildWidth = Math.max(maxChildWidth, child.getMeasuredWidth());
                    maxChildHeight = Math.max(maxChildHeight, child.getMeasuredHeight());
                    childState = ViewCompat.combineMeasuredStates(childState, ViewCompat.getMeasuredState(child));
                }
            }
        }
        final int maxWidth = Math.max(
                maxChildWidth * mNumColumns + mHorizontalSpacing * (mNumColumns - 1) + paddingLeft + paddingRight,
                getSuggestedMinimumWidth());
        final int maxHeight = Math.max(
                maxChildHeight * mNumRows + mVerticalSpacing * (mNumRows - 1) + paddingTop + paddingBottom,
                getSuggestedMinimumHeight());
        final int widthResult = widthMode == MeasureSpec.EXACTLY ? width : maxWidth;
        final int heightResult = heightMode == MeasureSpec.EXACTLY ? height : maxHeight;
        setMeasuredDimension(ViewCompat.resolveSizeAndState(widthResult, widthMeasureSpec, childState),
                ViewCompat.resolveSizeAndState(heightResult, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        final int numRows = mNumRows;
        final int numColumns = mNumColumns;
        final int horizontalSpacing = mHorizontalSpacing;
        final int verticalSpacing = mVerticalSpacing;
        final int childWidth = (getMeasuredWidth() - paddingLeft - paddingRight - horizontalSpacing * (numColumns - 1)) /numColumns;
        final int childHeight = (getMeasuredHeight() - paddingTop - paddingBottom - verticalSpacing * (numRows - 1)) /numRows;
        for (int i = 0; i < count; ++i) {
            final View child = getChildAt(i);
            final int row = i / numColumns;
            final int column = i % numColumns;
            if (child.getVisibility() != View.GONE) {
                final int childLeft = paddingLeft + column * (childWidth + horizontalSpacing);
                final int childTop = paddingTop + row * (childHeight + verticalSpacing);
                final int tmpWidth = Math.max(childWidth, child.getMeasuredWidth());
                final int tmpHeight = Math.max(childHeight, child.getMeasuredHeight());
                child.layout(childLeft, childTop, childLeft + tmpWidth, childTop + tmpHeight);
            }
        }
    }
}