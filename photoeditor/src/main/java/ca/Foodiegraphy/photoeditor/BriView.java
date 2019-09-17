package ca.Foodiegraphy.photoeditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;

import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ca.Foodiegraphy.features.Brightness;
import ca.Foodiegraphy.features.OnSaveBitmap;

import static android.media.effect.EffectFactory.EFFECT_BRIGHTNESS;


public class BriView extends GLSurfaceView implements GLSurfaceView.Renderer {

        private static final String TAG = "ImageFilterView";
        private int[] mTextures = new int[2];
        private EffectContext mEffectContext;
        private Effect mEffect;
        private Textu mTexRenderer = new Textu();
        private int mImageWidth;
        private int mImageHeight;
        private boolean mInitialized = false;
        private Brightness mCurrentEffect;
        private Bitmap mSourceBitmap;
        private CustomEffect mCustomEffect;
        private OnSaveBitmap mOnSaveBitmap;
        private boolean isSaveImage = false;

        public BriView(Context context) {
            super(context);
            init();
        }

        public BriView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            setEGLContextClientVersion(2);
            setRenderer(this);
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        }

        void setSourceBitmap(Bitmap sourceBitmap) {
       /* if (mSourceBitmap != null && mSourceBitmap.sameAs(sourceBitmap)) {
            //mCurrentEffect = NONE;
        }*/
            mSourceBitmap = sourceBitmap;
            mInitialized = false;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            if (mTexRenderer != null) {
                mTexRenderer.updateViewSize(width, height);
            }
        }

        @Override
        public void onDrawFrame(GL10 gl) {

            if (!mInitialized) {
                //Only need to do this once
                mEffectContext = EffectContext.createWithCurrentGlContext();
                mTexRenderer.init();
                loadTextures();
                mInitialized = true;
            }
            if (mCustomEffect != null) {
                //if an effect is chosen initialize it and apply it to the texture
                initEffect();
                applyEffect();
            }
            renderResult();
            if (isSaveImage) {
                final Bitmap mFilterBitmap = BitmapUtil.createBitmapFromGLSurface(this, gl);
                Log.e(TAG, "onDrawFrame: " + mFilterBitmap);
                isSaveImage = false;
                if (mOnSaveBitmap != null) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mOnSaveBitmap.onBitmapReady(mFilterBitmap);
                        }
                    });
                }
            }
        }

        void setFilterEffect(Brightness effect) {
            mCurrentEffect = effect;
            mCustomEffect = null;
            requestRender();
        }

        void setFilterEffect(CustomEffect customEffect) {
            mCustomEffect = customEffect;
            requestRender();
        }


        void saveBitmap(OnSaveBitmap onSaveBitmap) {
            mOnSaveBitmap = onSaveBitmap;
            isSaveImage = true;
            requestRender();
        }

        private void loadTextures() {
            // Generate textures
            GLES20.glGenTextures(2, mTextures, 0);

            // Load input bitmap
            if (mSourceBitmap != null) {
                mImageWidth = mSourceBitmap.getWidth();
                mImageHeight = mSourceBitmap.getHeight();
                mTexRenderer.updateTextureSize(mImageWidth, mImageHeight);

                // Upload to texture
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0]);
                GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mSourceBitmap, 0);

                // Set texture parameters
                GLToolbox.initTexParams();
            }
        }

        private void initEffect() {
            EffectFactory effectFactory = mEffectContext.getFactory();
            if (mEffect != null) {
                mEffect.release();
            }
            if (mCustomEffect != null) {
                mEffect = effectFactory.createEffect(mCustomEffect.getEffectName());
                Map<String, Object> parameters = mCustomEffect.getParameters();
                for (Map.Entry<String, Object> param : parameters.entrySet()) {
                    mEffect.setParameter(param.getKey(), param.getValue());
                }
            }
            else
                {

                switch (mCurrentEffect) {


                    case BRIGHTNESS:
                        mEffect = effectFactory.createEffect(EFFECT_BRIGHTNESS);
                        mEffect.setParameter("brightness", 2.0f);
                        break;

                }
            }
        }

        private void applyEffect() {
            mEffect.apply(mTextures[0], mImageWidth, mImageHeight, mTextures[1]);
        }

        private void renderResult() {
            if (mCustomEffect != null) {

                mTexRenderer.renderTexture(mTextures[1]);
            } else {

                mTexRenderer.renderTexture(mTextures[0]);
            }
        }
    }


