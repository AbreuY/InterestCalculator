package com.shiqianguan.interestcalculator.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.shiqianguan.interestcalculator.R;
import com.shiqianguan.interestcalculator.util.Account;

import java.math.BigDecimal;

public class CalculatorActivity extends AppCompatActivity {
    private EditText balance_edit;
    private EditText annual_interest_edit;
    private EditText years_edit;
    private Button calculate_button;
    private TextView result_text;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        initView();
        initListener();
    }

    private void initView() {
        balance_edit = (EditText) findViewById(R.id.balance);
        annual_interest_edit = (EditText) findViewById(R.id.annual_interest);
        years_edit = (EditText) findViewById(R.id.years);
        calculate_button = (Button) findViewById(R.id.calculate_button);
        result_text = (TextView) findViewById(R.id.result_text);
    }

    private void initListener() {
        EditTextWatcher editTextWatcher = new EditTextWatcher();
        balance_edit.addTextChangedListener(editTextWatcher);
        annual_interest_edit.addTextChangedListener(editTextWatcher);
        years_edit.addTextChangedListener(editTextWatcher);
        calculate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    double balance = Double.parseDouble(balance_edit.getText().toString());
                    double annual_interest = Double.parseDouble(annual_interest_edit.getText().toString()) / 100;
                    int years = Integer.parseInt(years_edit.getText().toString());
                    try {
                        result_text.requestFocus();
                        //计算时间很短，此次不做异步处理
                        result_text.setText("计算结果为：" + String.valueOf(Account.getTotalBalance(balance, annual_interest, years).setScale(4, BigDecimal.ROUND_HALF_EVEN)) + "元");
                        hideSoftInput();
                        result_text.setVisibility(View.VISIBLE);
                    } catch (IllegalArgumentException e) {
                        years_edit.requestFocus();
                        showToast("年份输入不能为负数，请重新输入");
                    }
                }
            }
        });
    }

    private boolean checkInput() {
        if (balance_edit.getText().length() == 0) {
            balance_edit.requestFocus();
            showToast("存入金额不能为空");
            return false;
        }
        if (annual_interest_edit.getText().length() == 0) {
            annual_interest_edit.requestFocus();
            showToast("年利率不能为空");
            return false;
        }
        if (years_edit.getText().length() == 0) {
            years_edit.requestFocus();
            showToast("存入年份不能为空");
            return false;
        }
        return true;
    }

    private void showToast(String content) {
        if (mToast == null) {
            mToast = Toast.makeText(CalculatorActivity.this, content, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(content);
        }
        mToast.show();
    }

    private void hideSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromInputMethod(getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    private class EditTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            result_text.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
