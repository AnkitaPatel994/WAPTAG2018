package com.konnect.waptag2017.visitor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.konnect.waptag2017.BaseAppCompatActivity;
import com.konnect.waptag2017.R;
import com.konnect.waptag2017.volleyRequest.CustomRequest;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by qlooit-9 on 11/11/16.
 */
public class VisitorsActivity extends BaseAppCompatActivity {

    private TextView txtBusCat, txtCountry, txtState;
    String BUSINESS_CATEGORY = "";
    String STATE = "";
    String COUNTRY = "";
    ArrayList<String> business_category_list = new ArrayList<>();
    ArrayList<String> STATE_LIST = new ArrayList<>();
    ArrayList<String> COUNTRY_LIST = new ArrayList<>();
    private EditText edtName, edtCompany, edtNo, edtMail, edtCity, edtState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.visitor_activity);
        setUpActionBar(getString(R.string.title_visitors));

        initControl();

    }

    private void initControl() {

        fillStaticData();

        imgAdv = (ImageView) findViewById(R.id.imgAdv);

        updateUI(imgAdv);

        edtName = (EditText) findViewById(R.id.edtName);
        edtCompany = (EditText) findViewById(R.id.edtCompany);
        edtNo = (EditText) findViewById(R.id.edtNum);
        edtMail = (EditText) findViewById(R.id.edtEmail);
        edtCity = (EditText) findViewById(R.id.edtCity);
        edtState = (EditText) findViewById(R.id.edtState);
        txtState = (TextView) findViewById(R.id.txtState);
        txtCountry = (TextView) findViewById(R.id.txtCountry);
        txtBusCat = (TextView) findViewById(R.id.txtBusCat);

        edtNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    if (edtNo.getText().toString().startsWith("7") ||
                            edtNo.getText().toString().startsWith("8") ||
                            edtNo.getText().toString().startsWith("9")) {
                    } else {
                        edtNo.setText("");
                        edtNo.setError("Enter Numbers starting with 7,8 or 9");
                    }
                }
            }
        });

        txtState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                openStateDialog();
            }
        });
        txtBusCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                openBusinessCategoryDialog();
            }
        });
        txtCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                openCountryDialog();
            }
        });

    }

    private ImageView imgAdv;

    private void fillStaticData() {

        business_category_list.add("Manufacturing");
        business_category_list.add("Trading");
        business_category_list.add("Import/Export");
        business_category_list.add("Industry");
        business_category_list.add("Chemical");
        business_category_list.add("Retail");
        business_category_list.add("Others");

        STATE_LIST.add("Andaman & Nicobar Island");
        STATE_LIST.add("Andhra Pradesh");
        STATE_LIST.add("Arunachal Pradesh");
        STATE_LIST.add("Assam");
        STATE_LIST.add("Bihar");
        STATE_LIST.add("Chandigarh");
        STATE_LIST.add("Chhattisgarh");
        STATE_LIST.add("Dadra & Nagar");
        STATE_LIST.add("Daman & Diu");
        STATE_LIST.add("Delhi");
        STATE_LIST.add("Goa");
        STATE_LIST.add("Gujarat");
        STATE_LIST.add("Haryana");
        STATE_LIST.add("Himachal Pradesh");
        STATE_LIST.add("Jammu & Kashmir");
        STATE_LIST.add("Jharkhand");
        STATE_LIST.add("Karnataka");
        STATE_LIST.add("Kerala");
        STATE_LIST.add("Lakshadweep");
        STATE_LIST.add("Madhya Pradesh");
        STATE_LIST.add("Maharashtra");
        STATE_LIST.add("Manipur");
        STATE_LIST.add("Meghalaya");
        STATE_LIST.add("Mizoram");
        STATE_LIST.add("Nagaland");
        STATE_LIST.add("Odisha (Orissa)");
        STATE_LIST.add("Pondichery");
        STATE_LIST.add("Punjab");
        STATE_LIST.add("Rajasthan");
        STATE_LIST.add("Sikkim");
        STATE_LIST.add("Tamil Nadu");
        STATE_LIST.add("Telangana");
        STATE_LIST.add("Tripura");
        STATE_LIST.add("Uttar Pradesh");
        STATE_LIST.add("Uttarakhand");
        STATE_LIST.add("West Bengal");

        COUNTRY_LIST.add("Afghanistan");
        COUNTRY_LIST.add("Albania");
        COUNTRY_LIST.add("Algeria");
        COUNTRY_LIST.add("Andorra");
        COUNTRY_LIST.add("Angola");
        COUNTRY_LIST.add("Antarctica");
        COUNTRY_LIST.add("Argentina");
        COUNTRY_LIST.add("Armenia");
        COUNTRY_LIST.add("Aruba");
        COUNTRY_LIST.add("Australia");
        COUNTRY_LIST.add("Austria");
        COUNTRY_LIST.add("Azerbaijan");
        COUNTRY_LIST.add("Bahrain");
        COUNTRY_LIST.add("Bangladesh");
        COUNTRY_LIST.add("Belarus");
        COUNTRY_LIST.add("Belgium");
        COUNTRY_LIST.add("Belize");
        COUNTRY_LIST.add("Benin");
        COUNTRY_LIST.add("Bhutan");
        COUNTRY_LIST.add("Bolivia");
        COUNTRY_LIST.add("Bosnia And Herzegovina");
        COUNTRY_LIST.add("Botswana");
        COUNTRY_LIST.add("Brazil");
        COUNTRY_LIST.add("Brunei Darussalam");
        COUNTRY_LIST.add("Bulgaria");
        COUNTRY_LIST.add("Burkina Faso");
        COUNTRY_LIST.add("Burundi");
        COUNTRY_LIST.add("Cambodia");
        COUNTRY_LIST.add("Cameroon");
        COUNTRY_LIST.add("Canada");
        COUNTRY_LIST.add("Cape Verde");
        COUNTRY_LIST.add("Central African Republic");
        COUNTRY_LIST.add("Chad");
        COUNTRY_LIST.add("Chile");
        COUNTRY_LIST.add("China");
        COUNTRY_LIST.add("Christmas Island");
        COUNTRY_LIST.add("Cocos (keeling) Islands");
        COUNTRY_LIST.add("Colombia");
        COUNTRY_LIST.add("Comoros");
        COUNTRY_LIST.add("Congo");
        COUNTRY_LIST.add("Congo");
        COUNTRY_LIST.add("Cook Islands");
        COUNTRY_LIST.add("Costa Rica");
        COUNTRY_LIST.add("Croatia");
        COUNTRY_LIST.add("Cuba");
        COUNTRY_LIST.add("Cyprus");
        COUNTRY_LIST.add("Czech Republic");
        COUNTRY_LIST.add("Denmark");
        COUNTRY_LIST.add("Djibouti");
        COUNTRY_LIST.add("Timor-leste");
        COUNTRY_LIST.add("Ecuador");
        COUNTRY_LIST.add("Egypt");
        COUNTRY_LIST.add("El Salvador");
        COUNTRY_LIST.add("Equatorial Guinea");
        COUNTRY_LIST.add("Eritrea");
        COUNTRY_LIST.add("Estonia");
        COUNTRY_LIST.add("Ethiopia");
        COUNTRY_LIST.add("Falkland Islands");
        COUNTRY_LIST.add("Faroe Islands");
        COUNTRY_LIST.add("Fiji");
        COUNTRY_LIST.add("Finland");
        COUNTRY_LIST.add("France");
        COUNTRY_LIST.add("French Polynesia");
        COUNTRY_LIST.add("Gabon");
        COUNTRY_LIST.add("Gambia");
        COUNTRY_LIST.add("Georgia");
        COUNTRY_LIST.add("Germany");
        COUNTRY_LIST.add("Ghana");
        COUNTRY_LIST.add("Gibraltar");
        COUNTRY_LIST.add("Greece");
        COUNTRY_LIST.add("Greenland");
        COUNTRY_LIST.add("Guatemala");
        COUNTRY_LIST.add("Guinea");
        COUNTRY_LIST.add("Guinea-bissau");
        COUNTRY_LIST.add("Guyana");
        COUNTRY_LIST.add("Haiti");
        COUNTRY_LIST.add("Honduras");
        COUNTRY_LIST.add("Hong Kong");
        COUNTRY_LIST.add("Hungary");
        COUNTRY_LIST.add("India");
        COUNTRY_LIST.add("Indonesia");
        COUNTRY_LIST.add("Iran");
        COUNTRY_LIST.add("Iraq");
        COUNTRY_LIST.add("Ireland");
        COUNTRY_LIST.add("Isle Of Man");
        COUNTRY_LIST.add("Israel");
        COUNTRY_LIST.add("Italy");
        COUNTRY_LIST.add("Japan");
        COUNTRY_LIST.add("Jordan");
        COUNTRY_LIST.add("Kazakhstan");
        COUNTRY_LIST.add("Kenya");
        COUNTRY_LIST.add("Kiribati");
        COUNTRY_LIST.add("Kuwait");
        COUNTRY_LIST.add("Kyrgyzstan");
        COUNTRY_LIST.add("Latvia");
        COUNTRY_LIST.add("Lebanon");
        COUNTRY_LIST.add("Lesotho");
        COUNTRY_LIST.add("Liberia");
        COUNTRY_LIST.add("Libya");
        COUNTRY_LIST.add("Liechtenstein");
        COUNTRY_LIST.add("Lithuania");
        COUNTRY_LIST.add("Luxembourg");
        COUNTRY_LIST.add("Macao");
        COUNTRY_LIST.add("Macedonia");
        COUNTRY_LIST.add("Madagascar");
        COUNTRY_LIST.add("Malawi");
        COUNTRY_LIST.add("Malaysia");
        COUNTRY_LIST.add("Maldives");
        COUNTRY_LIST.add("Mali");
        COUNTRY_LIST.add("Malta");
        COUNTRY_LIST.add("Marshall Islands");
        COUNTRY_LIST.add("Mauritania");
        COUNTRY_LIST.add("Mauritius");
        COUNTRY_LIST.add("Mayotte");
        COUNTRY_LIST.add("Mexico");
        COUNTRY_LIST.add("Micronesia");
        COUNTRY_LIST.add("Moldova");
        COUNTRY_LIST.add("Monaco");
        COUNTRY_LIST.add("Mongolia");
        COUNTRY_LIST.add("Montenegro");
        COUNTRY_LIST.add("Morocco");
        COUNTRY_LIST.add("Mozambique");
        COUNTRY_LIST.add("Myanmar");
        COUNTRY_LIST.add("Namibia");
        COUNTRY_LIST.add("Nauru");
        COUNTRY_LIST.add("Nepal");
        COUNTRY_LIST.add("Netherlands");
        COUNTRY_LIST.add("New Caledonia");
        COUNTRY_LIST.add("New Zealand");
        COUNTRY_LIST.add("Nicaragua");
        COUNTRY_LIST.add("Niger");
        COUNTRY_LIST.add("Nigeria");
        COUNTRY_LIST.add("Niue");
        COUNTRY_LIST.add("Norway");
        COUNTRY_LIST.add("Oman");
        COUNTRY_LIST.add("Pakistan");
        COUNTRY_LIST.add("Palau");
        COUNTRY_LIST.add("Panama");
        COUNTRY_LIST.add("Papua New Guinea");
        COUNTRY_LIST.add("Paraguay");
        COUNTRY_LIST.add("Peru");
        COUNTRY_LIST.add("Philippines");
        COUNTRY_LIST.add("Pitcairn");
        COUNTRY_LIST.add("Poland");
        COUNTRY_LIST.add("Portugal");
        COUNTRY_LIST.add("Puerto Rico");
        COUNTRY_LIST.add("Qatar");
        COUNTRY_LIST.add("Romania");
        COUNTRY_LIST.add("Russian Federation");
        COUNTRY_LIST.add("Rwanda");
        COUNTRY_LIST.add("Saint Barthélemy");
        COUNTRY_LIST.add("Samoa");
        COUNTRY_LIST.add("San Marino");
        COUNTRY_LIST.add("Sao Tome And Principe");
        COUNTRY_LIST.add("Saudi Arabia");
        COUNTRY_LIST.add("Senegal");
        COUNTRY_LIST.add("Serbia");
        COUNTRY_LIST.add("Seychelles");
        COUNTRY_LIST.add("Sierra Leone");
        COUNTRY_LIST.add("Singapore");
        COUNTRY_LIST.add("Slovakia");
        COUNTRY_LIST.add("Slovenia");
        COUNTRY_LIST.add("Solomon Islands");
        COUNTRY_LIST.add("Somalia");
        COUNTRY_LIST.add("South Africa");
        COUNTRY_LIST.add("Korea");
        COUNTRY_LIST.add("Spain");
        COUNTRY_LIST.add("Sri Lanka");
        COUNTRY_LIST.add("Saint Helena");
        COUNTRY_LIST.add("Saint Pierre And Miquelon");
        COUNTRY_LIST.add("Sudan");
        COUNTRY_LIST.add("Suriname");
        COUNTRY_LIST.add("Swaziland");
        COUNTRY_LIST.add("Sweden");
        COUNTRY_LIST.add("Switzerland");
        COUNTRY_LIST.add("Syrian Arab Republic");
        COUNTRY_LIST.add("Taiwan");
        COUNTRY_LIST.add("Tajikistan");
        COUNTRY_LIST.add("Tanzania");
        COUNTRY_LIST.add("Thailand");
        COUNTRY_LIST.add("Togo");
        COUNTRY_LIST.add("Tokelau");
        COUNTRY_LIST.add("Tonga");
        COUNTRY_LIST.add("Tunisia");
        COUNTRY_LIST.add("Turkey");
        COUNTRY_LIST.add("Turkmenistan");
        COUNTRY_LIST.add("Tuvalu");
        COUNTRY_LIST.add("United Arab Emirates");
        COUNTRY_LIST.add("Uganda");
        COUNTRY_LIST.add("United Kingdom");
        COUNTRY_LIST.add("Ukraine");
        COUNTRY_LIST.add("Uruguay");
        COUNTRY_LIST.add("United States");
        COUNTRY_LIST.add("Uzbekistan");
        COUNTRY_LIST.add("Vanuatu");
        COUNTRY_LIST.add("Holy See");
        COUNTRY_LIST.add("Venezuela");
        COUNTRY_LIST.add("Vietnam");
        COUNTRY_LIST.add("Wallis And Futuna");
        COUNTRY_LIST.add("Yemen");
        COUNTRY_LIST.add("Zambia");
        COUNTRY_LIST.add("Zimbabwe");

    }

    private void openBusinessCategoryDialog() {

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, business_category_list))
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        BUSINESS_CATEGORY = item.toString();
                        txtBusCat.setText(item.toString());
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        dialog.show();

    }

    private void openStateDialog() {

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, STATE_LIST))
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        STATE = item.toString();
                        txtState.setText(item.toString());
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        dialog.show();

    }

    private void openCountryDialog() {

        DialogPlus dialog = DialogPlus.newDialog(this)
                .setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, COUNTRY_LIST))
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        COUNTRY = item.toString();
                        if (COUNTRY.equals("India")) {
                            txtState.setVisibility(View.VISIBLE);
                            edtState.setVisibility(View.GONE);
                        } else {
                            txtState.setVisibility(View.GONE);
                            edtState.setVisibility(View.VISIBLE);
                        }
                        txtCountry.setText(item.toString());
                        dialog.dismiss();
                    }
                })
                .setGravity(Gravity.BOTTOM)
                .setCancelable(true)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        dialog.show();

    }

    public void callFinishVisitor(View v) {

        if (checkValidation()) {
            if (isNetworkAvailable(getApplicationContext()))
                callFinalAPi();
            else
                preToast(getString(R.string.no_internet_connection));
        }

    }

    private void callFinalAPi() {

        startDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = WEB_KEY + API_VISITOR_REGISTRATION;
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", edtName.getText().toString());
        params.put("company_name", edtCompany.getText().toString());
        params.put("num", edtNo.getText().toString());
        params.put("email", edtMail.getText().toString());
        params.put("city", edtCity.getText().toString());
        params.put("state", STATE);
        params.put("country", COUNTRY);
        params.put("bus_cat", BUSINESS_CATEGORY);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                deBug(response.toString());
                closeDialog();
                try {
                    String error = response.getString("error");
                    String message = response.getString("message");
                    if (error.toUpperCase(Locale.US).equals("FALSE")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(VisitorsActivity.this);
                        builder.setTitle(getResources().getString(R.string.app_name))
                                .setMessage(getString(R.string.visitor_register_text))
                                .setCancelable(true)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                })
                                .show();
                    } else {
                        alertBox(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                closeDialog();
                preToast(getString(R.string.try_again));
            }
        });

        int socketTimeout = 60000;//60 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }

    private boolean checkValidation() {

        if (!COUNTRY.equals("India")) {
            STATE = edtState.getText().toString().trim();
        }

        boolean status = true;

        if (edtName.getText().toString().trim().isEmpty()) {
            status = false;
            preToast("Enter Name");
        } else if (edtCompany.getText().toString().trim().isEmpty()) {
            status = false;
            preToast("Enter Company name");
        } else if (edtNo.getText().toString().trim().isEmpty()) {
            status = false;
            preToast("Enter Mobile");
        } else if (edtNo.getText().length() < 10) {
            status = false;
            preToast("Enter valid Mobile");
        } else if (edtMail.getText().toString().trim().isEmpty()) {
            status = false;
            preToast("Enter Email");
        } else if (!isValidEmail(edtMail.getText().toString().trim())) {
            status = false;
            preToast("Enter valid Email");
        } else if (edtCity.getText().toString().trim().isEmpty()) {
            status = false;
            preToast("Enter City");
        } else if (STATE.isEmpty()) {
            status = false;
            preToast("Enter State");
        } else if (COUNTRY.isEmpty()) {
            status = false;
            preToast("Enter Country");
        } else if (BUSINESS_CATEGORY.isEmpty()) {
            status = false;
            preToast("Select Business category");
        }

        return status;
    }

}
