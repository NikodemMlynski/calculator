package com.example.helloworld;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

class CalculatorNumber {
    private String currentNumber = "";
    private boolean isDecimal = false;
    private boolean isResult = false;
    public void setIsResult(boolean isResult) {
        this.isResult = isResult;
    }

    public void addDigit(String digit) {
        if(isResult) {
            currentNumber = digit;
            isResult = false;
        } else {
            currentNumber += digit;
        }
    }

    public void addDecimalPoint() {
        if(isResult) return;
        if (!isDecimal) {
            currentNumber += ".";
            isDecimal = true;
        }
    }

    public int getCurrentInt() {
        try {
            return Integer.parseInt(currentNumber);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public float getCurrentFloat() {
        try {
            return Float.parseFloat(currentNumber);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    public void reset() {
        currentNumber = "";
        isDecimal = false;
    }
    public String getCurrentNumber() {
        return currentNumber;
    }
}

public class MainActivity extends AppCompatActivity {
    ArrayList<CalculatorNumber> numbers = new ArrayList<CalculatorNumber>();
    ArrayList<String> operations = new ArrayList<String>();
    Button acButton;
    Button plusMinusButton;
    Button percentButton;

    Button divideButton;
    Button multiplyButton;
    Button subtractButton;
    Button plusButton;
    Button equalButton;
    TextView result;
    Button dotButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dotButton = findViewById(R.id.buttonDot);

        divideButton = findViewById(R.id.divideButton);
        multiplyButton = findViewById(R.id.multiplyButton);
        subtractButton = findViewById(R.id.subtractButton);
        plusButton = findViewById(R.id.plusButton);

        plusMinusButton = findViewById(R.id.plusMinusButton);
        percentButton = findViewById(R.id.percentButton);
        acButton = findViewById(R.id.acButton);

        equalButton = findViewById(R.id.buttonEqual);

        setButtonClickListener(R.id.button0);
        setButtonClickListener(R.id.button1);
        setButtonClickListener(R.id.button2);
        setButtonClickListener(R.id.button3);
        setButtonClickListener(R.id.button4);
        setButtonClickListener(R.id.button5);
        setButtonClickListener(R.id.button6);
        setButtonClickListener(R.id.button7);
        setButtonClickListener(R.id.button8);
        setButtonClickListener(R.id.button9);

        numbers.add(new CalculatorNumber());
        dotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDotClick();
            }
        });

        result = findViewById(R.id.result);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddClick();
            }
        });
        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubtractClick();
            }
        });
        multiplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleMultiplyCLick();
            }
        });
        divideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDivideClick();
            }
        });

        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEqualClick();
            }
        });
        percentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePercentCLick();
            }
        });
        acButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acClick();
            }
        });

    }
    void addNumber()
    {
        if (numbers.isEmpty() || !(numbers.get(numbers.size() - 1).getCurrentNumber().isEmpty())) {
            CalculatorNumber calcNumber = new CalculatorNumber();
            numbers.add(calcNumber);
        }
        numbers.forEach(calculatorNumber -> System.out.println(calculatorNumber.getCurrentNumber()));
        updateResult();
    }
    void updateResult() {
        String text = "";
            if(!(numbers.isEmpty())) {
            if (!(numbers.get(numbers.size() - 1).getCurrentNumber().isEmpty())) {
                CalculatorNumber lastNumber = numbers.get(numbers.size() - 1);
                text = lastNumber.getCurrentNumber();
            }
        }
        result.setText(text);
    }
    void acClick () {
        numbers.clear();
        CalculatorNumber calcNumber = new CalculatorNumber();
        numbers.add(calcNumber);
        updateResult();
    }
    void handlePercentCLick() {
        addNumber();
        operations.add("%");
    }
    void handleAddClick() {
        addNumber();
        operations.add("+");
    }
    void handleSubtractClick() {
        addNumber();
        operations.add("-");
    }

    void handleMultiplyCLick(){
        addNumber();
        operations.add("*");
    }
    void handleDivideClick(){
        addNumber();
        operations.add("/");
    }
    void handleNumberClick(String number){
        if (!(numbers.isEmpty())) {
            CalculatorNumber lastNumber = numbers.get(numbers.size() - 1);
            lastNumber.addDigit(number);
        }
        numbers.forEach(calculatorNumber -> System.out.println(calculatorNumber.getCurrentNumber()));
        updateResult();

    }
    void handleDotClick() {
        if (!(numbers.isEmpty() || numbers.get(numbers.size() - 1).getCurrentNumber().isEmpty())) {
            CalculatorNumber lastNumber = numbers.get(numbers.size() - 1);
            lastNumber.addDecimalPoint();
        }
        updateResult();
    }

    void handleEqualClick() {
        int numberIndex = 0;
        int operationIndex = 0;
        float equationValue = numbers.get(numberIndex++).getCurrentFloat();
        while (numberIndex < numbers.size() && operationIndex < operations.size()) {
              switch(operations.get(operationIndex)) {
                  case "+":
                      equationValue = equationValue + numbers.get(numberIndex++).getCurrentFloat();
                      break;
                  case "-":
                      equationValue = equationValue - numbers.get(numberIndex++).getCurrentFloat();
                      break;
                  case "/":
                      equationValue = equationValue / numbers.get(numberIndex++).getCurrentFloat();
                      break;
                  case "*":
                      equationValue = equationValue * numbers.get(numberIndex++).getCurrentFloat();
                      break;
                  case "%":
                      equationValue = equationValue % numbers.get(numberIndex++).getCurrentFloat();
                      break;
                  default:
                      operationIndex++;
              }
              Number value = checkIfDecimal(equationValue);
              result.setText(value.toString());
              numbers.clear();
              operations.clear();
              CalculatorNumber resultNumber = new CalculatorNumber();
              resultNumber.addDigit(value.toString());
              resultNumber.setIsResult(true);
              numbers.add(resultNumber);
        }
    }


    private void setButtonClickListener(int buttonId) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonText = ((Button)v).getText().toString();
                handleNumberClick(buttonText);
            }
        });
    }
    private static Number checkIfDecimal(float number) {
        if(number == (int) number) {
            return (int) number;
        }
        return number;
    }

}