package br.edu.fateczl.compute_age;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

    private EditText inputDia;
    private EditText inputMes;
    private EditText inputAno;
    private TextView output;

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

        inputDia = findViewById(R.id.inputDia);
        inputMes = findViewById(R.id.inputMes);
        inputAno = findViewById(R.id.inputAno);
        output = findViewById(R.id.output);

        Button btnCalc = findViewById(R.id.btnCalc);
        btnCalc.setOnClickListener(op -> ComputeAge());
    }

    private void ComputeAge() {
        int diaNasc = 0;
        int mesNasc = 0;
        int anoNasc = 0;
        try {
            diaNasc = Integer.parseInt(inputDia.getText().toString());
            mesNasc = Integer.parseInt(inputMes.getText().toString());
            anoNasc = Integer.parseInt(inputAno.getText().toString());

            if (diaNasc < 1 || diaNasc > 31) {
                throw new Exception(getString(R.string.diaInvalido));
            }
            if (mesNasc < 1 || mesNasc > 12) {
                throw new Exception(getString(R.string.mesInvalido));
            }
            if (anoNasc > LocalDate.now().getYear()) {
                throw new Exception(getString(R.string.anoInvalido));
            }

            int diaNow = LocalDate.now().getDayOfMonth();
            int mesNow = LocalDate.now().getMonthValue();
            int anoNow = LocalDate.now().getYear();

            if (diaNow < diaNasc) {
                mesNow -= 1;
                if (mesNow < 8) { //Antes de Agosto
                    if (mesNow == 2) {
                        if (anoNow % 4 == 0 && anoNow % 100 == 0 && anoNow % 400 == 0) {
                            diaNow += 29;
                        } else {
                            diaNow += 28;
                        }
                    } else if (mesNow % 2 > 0 || mesNow == 0) {
                        diaNow += 31;
                    } else {
                        diaNow += 30;
                    }
                } else if (mesNow % 2 == 0) { //Durante e Após Agosto
                    diaNow += 31;
                } else {
                    diaNow += 30;
                }
            }
            if (mesNow < mesNasc) {
                anoNow -= 1;
                mesNow += 12;
            }

            int anoIdade = anoNow - anoNasc;
            int mesIdade = mesNow - mesNasc;
            int diaIdade = diaNow - diaNasc;

            String answer = String.format(getString(R.string.output), anoIdade, mesIdade, diaIdade);

            output.setText(answer);

        } catch (Exception e) {
            if (e.getMessage().contains("input")) {
                output.setText(getString(R.string.error));
            } else {
                output.setText(e.getMessage());
            }
        }


    }
}