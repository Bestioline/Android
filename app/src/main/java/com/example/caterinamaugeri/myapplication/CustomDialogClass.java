package com.example.caterinamaugeri.myapplication;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import DB.TabUtenti;

public class CustomDialogClass extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no;
    private boolean b;
    private Session session;
    private TextView tx;
    private TabUtenti tabUtenti;

    public CustomDialogClass(Activity a, boolean b,Session session) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.b=b;
        this.session=session;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        tx= (TextView) findViewById(R.id.dialog);
        if(b)
            tx.setText("vuoi davvero uscire ?");
        else
            tx.setText("vuoi davvero cancellare l'account ?");
        tabUtenti=new TabUtenti(c);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes :

                //TERMINA L'ACTIVITY
                if(b){ //è logout

                    Intent intent = new Intent(c, MainActivity.class);
                    session.logout();
                    c.startActivity(intent);

                }
                else{
                    //è elimina account

                    tabUtenti.open();
                    String mail = session.getusename();
                    boolean cancellato= tabUtenti.cancellaUtente("'"+mail+"'");
                    System.out.println("cancellato="+cancellato);
                    tabUtenti.close();
                    session.logout();
                    Intent intent2 = new Intent(c, MainActivity.class);
                    c.startActivity(intent2);
                }

                break;
            case R.id.btn_no:
                //RESTA NELL'ACTIVITY
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}