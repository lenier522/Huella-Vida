package com.zonassoft.footprintforlife.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.zonassoft.footprintforlife.R;
import com.zonassoft.footprintforlife.utils.Tools;


public class AboutActivity extends AppCompatActivity {
    private String version;
    TextView txt_firma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_about);
        txt_firma = findViewById(R.id.firma_lcp);
        initToolbar();
        version="";

        try {
            PackageInfo packageInfo=getPackageManager().getPackageInfo(getPackageName(),0);
            version=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {

        }

        ((TextView)findViewById(R.id.version_code)).setText(version);

        ((View) findViewById(R.id.company)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://zonassoft.com"));
                startActivity(i);
            }
        });

        ((View) findViewById(R.id.website)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://zonassoft.com"));
                startActivity(i);
            }
        });
        txt_firma.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Crear un AlertDialog.Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);

                // Inflar el layout personalizado
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.item_lcp, null);

                // Configurar el AlertDialog para que use el layout personalizado
                builder.setView(dialogView)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Acción cuando el usuario presiona "Aceptar"
                                dialog.dismiss();
                            }
                        });

                // Establecer fondo transparente
                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                // Configurar el texto de la URL en color azul claro y sin subrayado
                TextView tvWeb = dialogView.findViewById(R.id.tv_web);
                String url = "https://perf3ctsolutions.com";
                String text = "Web : " + url;

                // Configurar el texto con URL en azul claro sin subrayado
                SpannableString spannableString = new SpannableString(text);
                int start = text.indexOf(url);
                int end = start + url.length();
                spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#87CEFA")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannableString.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        // Abrir el navegador con la URL
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    }
                }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                tvWeb.setText(spannableString);
                tvWeb.setMovementMethod(LinkMovementMethod.getInstance()); // Hacer que los enlaces sean clicables

                // Mostrar el dialog
                dialog.show();

                return true;
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.about));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.mdtp_white);
        Tools.setSystemBarLight(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_search, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.grey_60));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {

        }
        return super.onOptionsItemSelected(item);
    }


}
