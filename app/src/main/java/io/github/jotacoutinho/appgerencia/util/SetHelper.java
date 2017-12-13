package io.github.jotacoutinho.appgerencia.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.snmp4j.smi.OID;

import io.github.jotacoutinho.appgerencia.MainActivity;
import io.github.jotacoutinho.appgerencia.R;
import io.github.jotacoutinho.appgerencia.mib.MIBTree;

/**
 * Created by Joao Pedro on 13/12/2017.
 */

public class SetHelper extends AppCompatActivity {

    public MIBTree mib = MainActivity.mib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_helper);

        final OID target = (OID) getIntent().getExtras().get("entry");

        final EditText value = (EditText) findViewById(R.id.set_value);
        Button confirm = (Button) findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("value", Integer.valueOf(value.getText().toString()));
                returnIntent.putExtra("target", target);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        });

    }
}
