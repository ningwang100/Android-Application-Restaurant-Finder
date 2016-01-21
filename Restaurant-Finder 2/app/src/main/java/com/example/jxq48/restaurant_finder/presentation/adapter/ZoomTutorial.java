package com.example.jxq48.restaurant_finder.presentation.adapter;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;

import com.example.jxq48.restaurant_finder.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
/**
 * Created by jxq48 on 8/1/15.
 */
public class ZoomTutorial {
    final private int mAnimationDuration = 300;
    private Animator mCurrentAnimator;

    private View mContainView;
    private ViewGroup mThumbViewParent;
    private View mExpandedView;

    private Rect startBounds;
    private float startScale;
    private float startScaleFinal;

    public ZoomTutorial(View containerView,View expandedView) {
        mContainView = containerView;
        mExpandedView = expandedView;
    }

    /**
     *
     */
    public void zoomImageFromThumb(final View thumbView) {
        mThumbViewParent = (ViewGroup) thumbView.getParent();
        // If there's an animation in progress, cancel it immediately and
        // proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of
        startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container view.
        // Also set the container view's offset as the origin for the bounds,
        // since that's the origin for the positioning animation properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        mContainView.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);
        set_Center_crop(finalBounds);

        mExpandedView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations to the
        // top-left corner of
        // the zoomed-in view (the default is the center of the view).
        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(1);
        animSet.play(ObjectAnimator.ofFloat(mExpandedView, "pivotX", 0f))
                .with(ObjectAnimator.ofFloat(mExpandedView, "pivotY", 0f))
                .with(ObjectAnimator.ofFloat(mExpandedView, "alpha", 1.0f));
        animSet.start();

        startZoomAnim(mExpandedView, startBounds, finalBounds, startScale);
        // Upon clicking the zoomed-in image, it should zoom back down to the
        // original bounds and show the thumbnail instead of the expanded image.
        startScaleFinal = startScale;
    }

    /**
     *
     *
     * Adjust the start bounds to be the same aspect ratio as the final bounds
     * using the "center crop" technique.
     * This prevents undesirable stretching during the animation.
     * Also calculate the start scaling factor (the end scaling factor is always 1.0).
     */
    private void set_Center_crop(Rect finalBounds) {
        if ((float) finalBounds.width() / finalBounds.height() > (float)
                startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }
    }

    public void startZoomAnim(View v, Rect startBounds, Rect finalBounds, float startScale) {
        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set.play(
                ObjectAnimator.ofFloat(v, "x", startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(v, "y", startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(v, "scaleX", startScale, 1f))
                .with(ObjectAnimator.ofFloat(v, "scaleY", startScale, 1f));

        set.setDuration(mAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
                if (listener != null) {
                    listener.onExpanded();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
                if (listener != null) {
                    listener.onExpanded();
                }
            }
        });
        set.start();
        mCurrentAnimator = set;
    }

    public boolean getScaleFinalBounds(int position) {

        int firstPosition = ((AdapterView<?>)mThumbViewParent).getFirstVisiblePosition();
        View childView = mThumbViewParent.getChildAt(position - firstPosition);

        startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        try {

            childView.getGlobalVisibleRect(startBounds);
        } catch (Exception e) {
            return false;
        }
        mContainView.findViewById(R.id.container).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);
        set_Center_crop(finalBounds);
        startScaleFinal = startScale;
        return true;
    }

    /**
     * Do animation according to position
     * @param position
     */
    public void closeZoomAnim(int position) {
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }
        // Animate the four positioning/sizing properties in parallel,back to their original values.
        AnimatorSet set = new AnimatorSet();
        boolean isInBound = getScaleFinalBounds(position);
        if (isInBound) {
            set.play(ObjectAnimator.ofFloat(mExpandedView, "x", startBounds.left))
                    .with(ObjectAnimator.ofFloat(mExpandedView, "y", startBounds.top))
                    .with(ObjectAnimator.ofFloat(mExpandedView, "scaleX", startScaleFinal))
                    .with(ObjectAnimator.ofFloat(mExpandedView, "scaleY", startScaleFinal));
        } else {
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(mExpandedView, "alpha", 0.1f);
            set.play(alphaAnimator);
        }
        set.setDuration(mAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mExpandedView.clearAnimation();
                mExpandedView.setVisibility(View.GONE);
                mCurrentAnimator = null;
                if (listener != null) {
                    listener.onThumbed();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mExpandedView.clearAnimation();
                mExpandedView.setVisibility(View.GONE);
                mCurrentAnimator = null;
                if (listener != null) {
                    listener.onThumbed();
                }
            }
        });
        set.start();
        mCurrentAnimator = set;
    }
    private OnZoomListener listener;
    public void setOnZoomListener(OnZoomListener l) {
        listener = l;
    }
    public interface OnZoomListener {
        public void onExpanded();
        public void onThumbed();
    }
}
