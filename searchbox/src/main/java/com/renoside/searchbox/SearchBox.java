package com.renoside.searchbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class SearchBox extends ConstraintLayout {

    /**
     * 定义控件
     */
    private EditText input;
    private ImageView clear;
    /**
     * 定义需要用到的变量
     */
    private Drawable leftIco;
    private Drawable rightIco;
    private OnRightIcoListener onRightIcoListener;

    public SearchBox(Context context) {
        super(context);
        initView();
        initListener();
    }

    public SearchBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.search_box, this, true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchBox);
        initView();
        if (typedArray != null) {
            /**
             * 添加搜索框内左边图标
             */
            leftIco = typedArray.getDrawable(R.styleable.SearchBox_left_ico);
            if (leftIco != null) {
                leftIco.setBounds(0, 0, leftIco.getMinimumWidth() / 2, leftIco.getMinimumHeight() / 2);
            }
            /**
             * 添加搜索框内右边图标
             */
            rightIco = typedArray.getDrawable(R.styleable.SearchBox_right_ico);
            if (rightIco != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                rightIco.setBounds(0, 0, rightIco.getMinimumWidth() / 2, leftIco.getMinimumHeight() / 2);
            }
            /**
             * 显示图标并设置文本图标的间距
             */
            input.setCompoundDrawables(leftIco, null, rightIco, null);
            input.setCompoundDrawablePadding(10);
            /**
             * 设置搜索框提示文本
             */
            String inputHint = typedArray.getString(R.styleable.SearchBox_input_hint);
            input.setHint(inputHint);
        }
        initListener();
    }

    public SearchBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initListener();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        input = findViewById(R.id.input);
        clear = findViewById(R.id.clear);
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        /**
         * 搜索框触摸监听
         */
        input.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                /**
                 * 获取第二个图标即右边的图标
                 */
                Drawable drawable = input.getCompoundDrawables()[2];
                /**
                 * 右边没图标直接返回
                 */
                if (drawable == null)
                    return false;
                /**
                 * 不是点击直接返回
                 */
                if (motionEvent.getAction() != MotionEvent.ACTION_UP)
                    return false;
                /**
                 * 消费点击事件
                 */
                if (motionEvent.getX() > input.getWidth() - input.getPaddingRight() - drawable.getIntrinsicWidth()) {
                    /**
                     * 记录输入类型
                     */
                    int inputType = input.getInputType();
                    /**
                     * 禁止弹输入法
                     */
                    input.setInputType(InputType.TYPE_NULL);
                    /**
                     * 抛出监听
                     */
                    onRightIcoListener.onClick();
                    /**
                     * 重新设置回去输入类型
                     */
                    input.setInputType(inputType);
                    return false;
                }
                return false;
            }
        });
        /**
         * 监听搜索框文本变化
         */
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            /**
             * 变化期间的监听
             * @param charSequence 字符串集合
             * @param i length
             * @param i1 start
             * @param i2 end
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (clear.getVisibility() == View.GONE && charSequence != null) {
                    /**
                     * 当有文本输入时，隐藏右边图标，显示清空图标
                     */
                    input.setCompoundDrawables(leftIco, null, null, null);
                    clear.setVisibility(View.VISIBLE);
                } else if (charSequence.length() == 0 || charSequence == null) {
                    /**
                     * 当文本为空时，隐藏清空图标，显示右边图标
                     */
                    clear.setVisibility(View.GONE);
                    input.setCompoundDrawables(leftIco, null, rightIco, null);
                }
                if (clear.getVisibility() == View.VISIBLE) {
                    /**
                     * 设置清空图标监听
                     */
                    clear.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            input.setText("");
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 抛出右边图标监听
     *
     * @param onRightIcoListener
     */
    public void setOnRightIcoListener(OnRightIcoListener onRightIcoListener) {
        this.onRightIcoListener = onRightIcoListener;
    }

    /**
     * 获取搜索框文本
     *
     * @return
     */
    public String getText() {
        return input.getText().toString();
    }

}
