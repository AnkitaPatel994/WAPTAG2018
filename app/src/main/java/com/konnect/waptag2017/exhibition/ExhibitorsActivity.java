package com.konnect.waptag2017.exhibition;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.konnect.waptag2017.BaseAppCompatActivity;
import com.konnect.waptag2017.R;
import com.konnect.waptag2017.adapter.ExhibitorListAdapter;
import com.konnect.waptag2017.model.ExhibitorModel;
import com.konnect.waptag2017.volleyRequest.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by qlooit-9 on 9/11/16.
 */
public class ExhibitorsActivity extends BaseAppCompatActivity {

    private ListView mListExhibitor;
    private ArrayList<String> exhibitor_company = new ArrayList<>();
    private ArrayList<String> exhibitor_name = new ArrayList<>();
    private ArrayList<String> exhibitor_mobile = new ArrayList<>();
    private ArrayList<String> exhibitor_address = new ArrayList<>();
    private ArrayList<String> exhibitor_mail = new ArrayList<>();
    private ArrayList<String> exhibitor_stall = new ArrayList<>();
    private ExhibitorListAdapter exhibitorListAdapter;
    ArrayList<ExhibitorModel> exhibitorModelArrayList;
    ArrayList<ExhibitorModel> exhibitorModelArrayList_dummy;
    private EditText searchBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActionBar(getString(R.string.title_exhibitions));
        setContentView(R.layout.exhibition_activity);
        getSupportActionBar().setElevation(0);

        initControl();

        assignDefaultData();
        if (isNetworkAvailable(getApplicationContext())) {
            manageResponseTemp();
            callExhibitorsService();
        } else {
            try {
                JSONArray jsonArray = new JSONArray(getPref(PREF_EXHIBITOR_LIST, ""));
                if (jsonArray.length() == 0) {
                    manageResponseTemp();
                } else {
                    manageResponse(jsonArray);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private ImageView imgAdv;

    private void assignDefaultData() {

        exhibitor_company.add("Cloud International");
        exhibitor_name.add("Sejal Mehta");
        exhibitor_mobile.add("9979611678");
        exhibitor_address.add("418, GIDC, Corporation industrial Estate, Road no: 34, Near Bhikshuk Gruh, Odhav, Ahmedabad.");
        exhibitor_mail.add("info@cloudwater.in");
        exhibitor_stall.add("S-2");

        exhibitor_company.add("Max Pure Water System Pvt. Ltd");
        exhibitor_name.add("Rahul Jhabak");
        exhibitor_mobile.add("7600010480");
        exhibitor_address.add("Nr.Bharat Petrol pump, Opp, Bharat cancer hospital, Kumbhariya Road, Saroli, Surat.");
        exhibitor_mail.add("rahulsjain@gmail.com");
        exhibitor_stall.add("S-5");

        exhibitor_company.add("Sure Water Technology");
        exhibitor_name.add("Rishabh Sanghavi");
        exhibitor_mobile.add("9898500062");
        exhibitor_address.add("B-404, 4th floor, Galaxy Complex, Nr.National Handloom, Law Garden, ");
        exhibitor_mail.add("sure.ro@hotmail.com ");
        exhibitor_stall.add("S-3");

        exhibitor_company.add("Neer Water Purification");
        exhibitor_name.add("Piyushbhai R.Shah");
        exhibitor_mobile.add("9824044952 / 9227144952");
        exhibitor_address.add("202, Anand Chamber, Opp Mukta Jivan Colour lab, Above Income Tax Under Bridge, Navarangpura, Ahmedabad.");
        exhibitor_mail.add("sales@neerwater.com");
        exhibitor_stall.add("N-1, N-2, N-3");

        exhibitor_company.add("Fivebro International Pvt.Ltd.");
        exhibitor_name.add("Nishit Bhai");
        exhibitor_mobile.add("7874516161");
        exhibitor_address.add("Building no:9-10 Sigma Corporate, Bihind Rajpath Club, Opp. S.G Highway, Bodakdev , ahmedabad.");
        exhibitor_mail.add("nishit@fivebro.com");
        exhibitor_stall.add("S-13");

        //-----------------

        exhibitor_company.add("Doshion Private Limited");
        exhibitor_name.add("Ashit Bhai");
        exhibitor_mobile.add("9825073131");
        exhibitor_address.add("Plot No: 24 Phase-II, GIDC Vatva, Ahmedabad.");
        exhibitor_mail.add("ashish.chauhan@doshion.com");
        exhibitor_stall.add("S-14");

        exhibitor_company.add("Excel Filteration Pvt. Ltd.");
        exhibitor_name.add("Kalpesh Shah");
        exhibitor_mobile.add("9904575045 / 9227219421");
        exhibitor_address.add("C-107/108 Signature Business Park-II, Near Sarkhej Sanand Circle, Sarkhej, Ahmedabad.");
        exhibitor_mail.add("md@efpwater.com");
        exhibitor_stall.add("S-1");

        exhibitor_company.add("Krinovate System & Communications");
        exhibitor_name.add("Ritesh Desai");
        exhibitor_mobile.add("9825108897");
        exhibitor_address.add("15,Swaroop Tenaments, Opp Dominos pizza, Nr. Tulsidham Cross Road, Manjalpur, Baroda, Gujarat");
        exhibitor_mail.add("krinovate@yahoo.com");
        exhibitor_stall.add("S-23");

        exhibitor_company.add("Cosmos Water Solutions Pvt.Ltd.");
        exhibitor_name.add("Sejal Mehta");
        exhibitor_mobile.add("9979611678");
        exhibitor_address.add("207, Ashirwad Paras, Opp. Prahladnagar, Corporate Road, Prahladnagar, Ahmedabad");
        exhibitor_mail.add("sejal.mehta@cosmoswater.in");
        exhibitor_stall.add("S-4");

        exhibitor_company.add("Nice Industries");
        exhibitor_name.add("Chetan Taparia");
        exhibitor_mobile.add("9426079729");
        exhibitor_address.add("51, Tribhovan Estate, Road No-8, Kathwada, ");
        exhibitor_mail.add("tapariachetan@gmail.com");
        exhibitor_stall.add("S-31");

        exhibitor_company.add("Meera Trade Impex");
        exhibitor_name.add("Bhavesh Patel");
        exhibitor_mobile.add("9825125556");
        exhibitor_address.add("303, Super Diamond Market, Nr. Mini bazar, Varacha Road, Surat.");
        exhibitor_mail.add("fstfc100@gmail.com");
        exhibitor_stall.add("N-4 to N-7");

        exhibitor_company.add("S.K. Water Care");
        exhibitor_name.add("Shailesh Patel");
        exhibitor_mobile.add("9725251313");
        exhibitor_address.add("S.K. House, Opp. Jilla Panchayat, GIDC Road, Palanpur-385001");
        exhibitor_mail.add("sk.watercare@yahoo.com");
        exhibitor_stall.add("N-34, N-35");

        exhibitor_company.add("Royal Technologies");
        exhibitor_name.add("Bhavesh Zalawadia");
        exhibitor_mobile.add("9374155752 / 9825276918");
        exhibitor_address.add("B-11 Gajanan Estate, Nr. Zaveri Estate, B/h Kathwada GIDC Ahmedabad.");
        exhibitor_mail.add("royaltechnologies99@gmail.com");
        exhibitor_stall.add("S-6");

        exhibitor_company.add("ACE Hygiene Product LTD");
        exhibitor_name.add("Kishor Charatmol");
        exhibitor_mobile.add("9870925172");
        exhibitor_address.add("1003A, Penindula Tower, G.K.Marg,Lower Parel,Mumbai.");
        exhibitor_mail.add("kishor@alfauv.com");
        exhibitor_stall.add("N-46");

        exhibitor_company.add("Active Carbon India Pvt.Ltd");
        exhibitor_name.add("Pratyush Reddy");
        exhibitor_mobile.add("9848789373, 9553575908");
        exhibitor_address.add("G1, K5, Kimtee Colony, Road No.12, Banjara Hills, Hydrabad");
        exhibitor_mail.add("info@activecarbon.net");
        exhibitor_stall.add("N-55");

        exhibitor_company.add("Aditi Sales");
        exhibitor_name.add("Umang Baherawala");
        exhibitor_mobile.add("9824075677");
        exhibitor_address.add("13, Navjyot Complex, Opp: Sarthi-1, Nr. A-one School, Subhash Chowk, Memnagar, Ahmedabad");
        exhibitor_mail.add("aditisalesnservices@gmail.com");
        exhibitor_stall.add("N-29");

        exhibitor_company.add("Aishwarya Marketing Company");
        exhibitor_name.add("Sachinbhai R. Ringwala");
        exhibitor_mobile.add("9426655567/ 9327029224");
        exhibitor_address.add("1st floor Sarita KunjvBunglow B/h, Karnavati Hospital, Ellisbridge, opp,  Town Hall, ");
        exhibitor_mail.add("filterhousing@yahoo.co.in");
        exhibitor_stall.add("N-52, N-53");

        exhibitor_company.add("Asian RO Systems");
        exhibitor_name.add("Mr.Hiteshbhai Paneliya");
        exhibitor_mobile.add("9574080630");
        exhibitor_address.add("Shop No-3, shivangi intercity, Opp: Sudaravan So, Near, Kenal, Nikol Road, Ahmedabad.");
        exhibitor_mail.add("asianro5@gmail.com");
        exhibitor_stall.add("N-38");


        exhibitor_company.add("Bhumi Electronics Pvt.Ltd");
        exhibitor_name.add("Jitu Bhai");
        exhibitor_mobile.add("9998274729");
        exhibitor_address.add("106, Shiv Complex, Opp. Ramvadi, Mangrol Road,Keshod, Junagadh, Saurastra, Gujrat.");
        exhibitor_mail.add("info@pecificrotech.com");
        exhibitor_stall.add("S-30(B)");

        exhibitor_company.add("BlueMount Appliances Pvt. Ltd.");
        exhibitor_name.add("Amit Gupta ");
        exhibitor_mobile.add("9958693983 / 9560899675");
        exhibitor_address.add("B-96, Pushpanjali Enclave, Pitampura, New Delhi-34");
        exhibitor_mail.add("cc@bluemountro.com");
        exhibitor_stall.add("S-67(A), S-68(A)");

        exhibitor_company.add("Brahmani Water Solution");
        exhibitor_name.add("J.K.Patel");
        exhibitor_mobile.add("9099017226/ 9099017227");
        exhibitor_address.add("Plot No 61, Ambica Ind.Estate, Opp.Government Housing, Nr.Vatva Overbridge, Vatva");
        exhibitor_mail.add("info@alfafilter.in");
        exhibitor_stall.add("S-65(A)");

        exhibitor_company.add("Braqqua Chems");
        exhibitor_name.add("Kiran Vishwanathan");
        exhibitor_mobile.add("9908390009");
        exhibitor_address.add(" 27/115, Kranthiharika Appts,Huda Complex,Saroornagar,Hyderabad -500035");
        exhibitor_mail.add("braquachems@gmail.com ");
        exhibitor_stall.add("N-64, N-65");

        exhibitor_company.add("Canter Water Cooler");
        exhibitor_name.add("Amit Agrawal");
        exhibitor_mobile.add("9737207870");
        exhibitor_address.add("161-162, Ramdev industrail Estate, Opp.Shahwadi Bus");
        exhibitor_mail.add("canterwatercooler@yahoo.com");
        exhibitor_stall.add("S-24 (B)");

        exhibitor_company.add("Chasten Water Component");
        exhibitor_name.add("Manoj Bhai");
        exhibitor_mobile.add("9824522620");
        exhibitor_address.add("94,1st Floor, Rameshwar Industrial Estate, Nr. Jay Chemical, Kenal Road, Odhav, Ahmedabad.");
        exhibitor_mail.add("pureaquaengineers@gmail.com ");
        exhibitor_stall.add("S-10");


        exhibitor_company.add("Chemtech Solutions");
        exhibitor_name.add("Hardik Sompura");
        exhibitor_mobile.add("9998123416");
        exhibitor_address.add("Shed No - 13, Shri Raman Estate, B/h - V. K. Estate, Near Railway Crossing, Nr. Naresh Furniture, Gota, Ahmedabad ");
        exhibitor_mail.add("chemicals.chemtech@gmail.com");
        exhibitor_stall.add("N-45");

        exhibitor_company.add("Cool Planet");
        exhibitor_name.add("Sumit Agarwal");
        exhibitor_mobile.add("9824031151 / 9429025623");
        exhibitor_address.add("5, Haribhai Estate, Beside, G.B. Wheelers, Nr. Shahwadi Bus Stand, Narol, Ahmedabad");
        exhibitor_mail.add("sheetalwatewrcooler@yahoo.com ");
        exhibitor_stall.add("S - 30 (A)");

        exhibitor_company.add("Creativeflex");
        exhibitor_name.add("Shaik Wajeed Ali");
        exhibitor_mobile.add("9573538442 / 9573538341");
        exhibitor_address.add("C-2, Co-Operative Industrial Estate, Balanagar, Hyderabad.");
        exhibitor_mail.add("creativeflexengineering@gmail.com");
        exhibitor_stall.add("N-22");

        exhibitor_company.add("DC Water World Supermart (P)Ltd");
        exhibitor_name.add("Dinesh Chawla Ji");
        exhibitor_mobile.add("9999817555");
        exhibitor_address.add("D-31 Commercial Complex, Ranjeet Nagar, New Delhi.");
        exhibitor_mail.add(" dinesh@watersupermart.com");
        exhibitor_stall.add("S-66 (A)");

        exhibitor_company.add("Deval Specific Inc");
        exhibitor_name.add("Hemant Bhai");
        exhibitor_mobile.add("9924315890");
        exhibitor_address.add("21, Dharnidhar Apartment, 204 Nehru Park, Vastrapur, ");
        exhibitor_mail.add("lotusro2009@yahoo.in");
        exhibitor_stall.add("N-31");

        exhibitor_company.add("E-MEM Environmental Technology Co.Ltd");
        exhibitor_name.add("Miss Ivy");
        exhibitor_mobile.add("0086 131 6994 3676");
        exhibitor_address.add("No.26, Longquan Road, Future SCI-TECH City, Hangzhou City, Zhejiang Province, China");
        exhibitor_mail.add("1175973522@qq.com");
        exhibitor_stall.add("N-48");

        exhibitor_company.add("Eigen Water Electotech Industries Pvt.Ltd");
        exhibitor_name.add("Shishir, Himanshu Goyal");
        exhibitor_mobile.add("9953720410");
        exhibitor_address.add("1st Floor,Mangolpura Kalan, Sector-2, Rohini, New Delhi");
        exhibitor_mail.add("eigenwater1@gmail.com");
        exhibitor_stall.add("S-62(A)");

        exhibitor_company.add("FCW Technology");
        exhibitor_name.add("Chaganbhai A. Patel");
        exhibitor_mobile.add("9825070520");
        exhibitor_address.add("E-106, G.I.D.C Estate Electronic-Zone Sec- 26, ");
        exhibitor_mail.add("info@fcwindia.com");
        exhibitor_stall.add("S-24(A), S-25(A)");

        exhibitor_company.add("H2O Scientific");
        exhibitor_name.add("Nirav Dedania");
        exhibitor_mobile.add("9824495095");
        exhibitor_address.add("Mahadev Vadi Main Road, Opp School No-47 Laxminagar, Rajkot");
        exhibitor_mail.add("h2oscientific@gmail.com");
        exhibitor_stall.add("S - 22(B)");

        exhibitor_company.add("Hi-Tech Sweet Water Technology Pvt.Ltd");
        exhibitor_name.add("Rajesh Bhai");
        exhibitor_mobile.add("9898075555");
        exhibitor_address.add("1, Basement, Viral Apprt, opp, Alekh Apprt, Lad Society Road Nehrupark, Vastrapur");
        exhibitor_mail.add("rajesh@hitechro.net ");
        exhibitor_stall.add("N-57 to 63");

        exhibitor_company.add("Hiten Techno Products Corporation");
        exhibitor_name.add("Hiten Jhaveri");
        exhibitor_mobile.add("9322679997");
        exhibitor_address.add("1014, Dr.Ankleshwariy House, Old Hanuman Road, Opp-Electricity House, Relief Road, Ahmedabad.");
        exhibitor_mail.add("hiten.jhaveri@hitentechno.com");
        exhibitor_stall.add("N-28");

        exhibitor_company.add("HM Digital Inc");
        exhibitor_name.add("Sarvesh Bhai");
        exhibitor_mobile.add("9909032584");
        exhibitor_address.add("Korea Business Devlopment Center, A26/4, Mohan Cooperative Industrial Estate, New Delhi, 110044, India.");
        exhibitor_mail.add("arhmdigital@gmail.com");
        exhibitor_stall.add("S-29(A)");

        exhibitor_company.add("Jay Metal");
        exhibitor_name.add("Mitesh Kasagara");
        exhibitor_mobile.add("9824166106/ 924029929");
        exhibitor_address.add("Survey No, 38-1 Plot No, 42, Bh, Krishna Park Hotel, Nr. Prashant Casting, Gondal Road, Vavdi");
        exhibitor_mail.add("metals_forging@yahoo.com");
        exhibitor_stall.add("S-12");

        exhibitor_company.add("Jay Water Management Pvt. Ltd.");
        exhibitor_name.add("Gaurav Bhai");
        exhibitor_mobile.add("9825317378");
        exhibitor_address.add("Jay House, Nr. Stadium Circle, Opp Gwalia Sweet Navrangpura, Ahmedabad");
        exhibitor_mail.add("gaurav@jaywater.com");
        exhibitor_stall.add("S-25 (B)");

        exhibitor_company.add("Joy Stick Wellness");
        exhibitor_name.add("Jayant Desai");
        exhibitor_mobile.add("9374125257");
        exhibitor_address.add("2025, Belgium Squre, OPP. Liner Bus Stop, Delhi Gate, Surat - 395003, Gujarat, India");
        exhibitor_mail.add("joystickwellness@gmail.com ");
        exhibitor_stall.add("N-18, N-19");

        exhibitor_company.add("Joystick Biocare");
        exhibitor_name.add("Nitesh Bhai");
        exhibitor_mobile.add("9913402000, 9974117744");
        exhibitor_address.add("75, Navjivan Industrial Society, srvoday Petrol Pump,Surat");
        exhibitor_mail.add("joystickbiocare@gmail.com");
        exhibitor_stall.add("S-61");

        exhibitor_company.add("Krishna Enterprise");
        exhibitor_name.add("Riteshbhai Patel");
        exhibitor_mobile.add("9714777990");
        exhibitor_address.add("14, Ambika Society, 2nd floor, Nr.Nabard Tower, Usmanpura,");
        exhibitor_mail.add("kirshnaenterprise09@gmail.com");
        exhibitor_stall.add("N-36");

        exhibitor_company.add("Krishna Marketing");
        exhibitor_name.add("Dipakbhai");
        exhibitor_mobile.add("9825524433");
        exhibitor_address.add("Salun Bazar, Nadiad");
        exhibitor_mail.add("apple.inc@rediffmail.com");
        exhibitor_stall.add("S-30");

        exhibitor_company.add("Laxcru Water Tech Pvt. Ltd.");
        exhibitor_name.add("Vinodbhai Dhudhat");
        exhibitor_mobile.add("9727976500");
        exhibitor_address.add("74 / 75,  Haridham Ind, Estate opp,  Gurudwara Temple, Odhav, ");
        exhibitor_mail.add("vinoddhudhat@hotmail.com");
        exhibitor_stall.add("S-33");

        exhibitor_company.add("Lubi Industries - LLP");
        exhibitor_name.add("Aashay Patel");
        exhibitor_mobile.add("8238087829");
        exhibitor_address.add("Near Kalyan Meals, Naroda Road, Ahmedabad-380025");
        exhibitor_mail.add("ajpatel@lubipumps.com");
        exhibitor_stall.add("S-32");

        exhibitor_company.add("Micropore Filter Technology");
        exhibitor_name.add("Dineshbhai");
        exhibitor_mobile.add("9662173006 / 9824522620");
        exhibitor_address.add("46, Rameshwar ind. Estate, Nr. Jay Camical, Canal Road, Odhav, ");
        exhibitor_mail.add("microporefiltertechnology@gmail.com");
        exhibitor_stall.add("N-23, N-24");

        exhibitor_company.add("MMP Filration Pvt. Ltd");
        exhibitor_name.add("V.K. Agrawal");
        exhibitor_mobile.add("9825044104");
        exhibitor_address.add("C- 3, Anushruti Tower, S.G.Road, Thaltej");
        exhibitor_mail.add("vkagrawal@mmpfilter.com");
        exhibitor_stall.add("N-32");

        exhibitor_company.add("Navkar R.O. System");
        exhibitor_name.add("Ashish Shah ");
        exhibitor_mobile.add("9925247858");
        exhibitor_address.add("B-9, Basement, New York Tower-A, Near Cargo Ford Show Room, Thaltej Cross Road,  S.G. Highway, Thaltej, ");
        exhibitor_mail.add("navkar.rosystem@yahoo.in");
        exhibitor_stall.add("S-26");

        exhibitor_company.add("New Tech Water Purifier Systems Pvt. Ltd.");
        exhibitor_name.add("Mr.Bhagwati Jain");
        exhibitor_mobile.add("9377770971 / 9377780971");
        exhibitor_address.add("Basement A-1, Jupitor Tower B\\/H, Grandbhagwati Hotel S.G. Highway Road, Ahmedabad");
        exhibitor_mail.add("jainbhagwati@hotmail.com");
        exhibitor_stall.add("S-9");

        exhibitor_company.add("Newpex Enterprise");
        exhibitor_name.add("Amrit Patel");
        exhibitor_mobile.add("9879522746");
        exhibitor_address.add("1, Ratnmani Complex, T.B, Nagar opp,  B.R.T.S. Bus Stop Thakkarnagar, Nr.Chamak Chuna, ");
        exhibitor_mail.add("newpex_amritjal@yahoo.com");
        exhibitor_stall.add("S-28(B)");

        exhibitor_company.add("Nextech");
        exhibitor_name.add("Saurabh Bhai");
        exhibitor_mobile.add("9028466667");
        exhibitor_address.add("Saniputra Co Oparative Housing Society, B/h Arco Transport, Khadgaav Road, Wadi, Nagpur, MH-440023");
        exhibitor_mail.add("nextechro@gmail.com");
        exhibitor_stall.add("S-8");

        exhibitor_company.add("ORG Biotech");
        exhibitor_name.add("Snehalbhai Kotadia");
        exhibitor_mobile.add("9825146217");
        exhibitor_address.add("26, Nalanda Industrial Estate, S.P. Ring Road, Odhav, ");
        exhibitor_mail.add("snehal@orgengitech.com");
        exhibitor_stall.add("N-33");

        exhibitor_company.add("Org Engitech Pvt. Ltd.");
        exhibitor_name.add("Bhupesh Patel");
        exhibitor_mobile.add("9925044149 / 9879504010");
        exhibitor_address.add("25, Nalanda Industrial Estate, S.P. Ring Road, Kathwada, ");
        exhibitor_mail.add("bhupesh@orgengitech.com");
        exhibitor_stall.add("N-8, N-9, N-10");

        exhibitor_company.add("P Zone Electronics Pvt.Ltd");
        exhibitor_name.add("Rajesh Gupta");
        exhibitor_mobile.add("9811505697 / 9560333675");
        exhibitor_address.add("Dhaniram Colony, Sector 19, Rohini, Badli, Jail Road, New Delhi 110042");
        exhibitor_mail.add("info@pzone.in");
        exhibitor_stall.add("S-67(B), S-68(B)");

        exhibitor_company.add("P.L. Products ");
        exhibitor_name.add("Chandan  Sharma");
        exhibitor_mobile.add("9825083672");
        exhibitor_address.add("TF -33, 34, 35, Someshwar Mall, G.I.D.C.-1, Modhera Road, ");
        exhibitor_mail.add("mukesh419@yahoo.co.in");
        exhibitor_stall.add("S-15");

        exhibitor_company.add("Paras Filters");
        exhibitor_name.add("Paras Shah");
        exhibitor_mobile.add("9712902016");
        exhibitor_address.add("23,24,35,36 Gajanan Ind.Park, Vatwa, Ahmedabad");
        exhibitor_mail.add("parasfilter@gmail.com");
        exhibitor_stall.add("N-25");

        exhibitor_company.add("Pratham Filter Industries");
        exhibitor_name.add("Ajay Shroff");
        exhibitor_mobile.add("7573998044 / 9820246044");
        exhibitor_address.add("Unit No - 19, Singal Estate, Behind Jaipur Transport, Ujala Circle, S.G.Highway, Sarkhej, Ahmedabad");
        exhibitor_mail.add("ajay@prathamfilter.com");
        exhibitor_stall.add("S-69");

        exhibitor_company.add("Pure & Cure Technology");
        exhibitor_name.add("Hemant Dandekar");
        exhibitor_mobile.add("9820032292");
        exhibitor_address.add("Plot No. 38/2, Panchratna Industrial Estate, Nr. Vivek Steel Changodar, Ahmedabad");
        exhibitor_mail.add("hemant@purencure.com");
        exhibitor_stall.add("S-34");

        exhibitor_company.add("Pure H2O");
        exhibitor_name.add("Vikram Thakar");
        exhibitor_mobile.add("7878799688");
        exhibitor_address.add("402, Ramchandra House opp,  Dinesh Hall Income Tax Ashram Road,");
        exhibitor_mail.add("info@pureh2oro.com");
        exhibitor_stall.add("S-11");

        exhibitor_company.add("Pure N Safe ");
        exhibitor_name.add("Umesh Agrawal");
        exhibitor_mobile.add("9811292701");
        exhibitor_address.add("B-11, Ground Floor, Shankar Garden, New Delhi,India");
        exhibitor_mail.add("info@purensafe.com");
        exhibitor_stall.add("N-30");

        exhibitor_company.add("Reliance Industries");
        exhibitor_name.add("Dineshbhai");
        exhibitor_mobile.add("9825066716 , 8460131889");
        exhibitor_address.add("No, 19, Gujarat Botteling Road, Rakhiyal, ");
        exhibitor_mail.add("relianceagencies07@rediffmail.com");
        exhibitor_stall.add("N-39");

        exhibitor_company.add("Rewa Water Tech ");
        exhibitor_name.add("Jainam Shah");
        exhibitor_mobile.add("9374444001 / 9428914001");
        exhibitor_address.add("Ground Floor Everest Tower Opp Shastri Nagar B.R.T.S Near Pragatinagar Gardan 132 Ft Ring Road, Narnapura,");
        exhibitor_mail.add("jimy1403@gmail.com");
        exhibitor_stall.add("N-37");

        exhibitor_company.add("Sai Services");
        exhibitor_name.add("Ankit Patel");
        exhibitor_mobile.add("8735983988");
        exhibitor_address.add("Shop, No-8, Crystal Plaza, Kubereshwar Road, opp, vallabh School Lane, Waghodia-Dabhoi Ring Road, ");
        exhibitor_mail.add("ankitpatel@yahoo.com");
        exhibitor_stall.add("S-45");

        exhibitor_company.add("Season Samrat");
        exhibitor_name.add("Lalji Bhai");
        exhibitor_mobile.add("9824349958");
        exhibitor_address.add("LG-9, Dadar(Basement) Sunrise Chamber, Minibazar Varachha");
        exhibitor_mail.add("lgkakkad@yahoo.com");
        exhibitor_stall.add("N-21");

        exhibitor_company.add("Seven Seas Solutions");
        exhibitor_name.add("Krushnakant Mehta");
        exhibitor_mobile.add("7874303290");
        exhibitor_address.add("B-FF-03 Radhe Shopping Mall Khokhra Circle Khokhra, ");
        exhibitor_mail.add("kkmaheta@sevenseassolutions.com");
        exhibitor_stall.add("N-42");

        exhibitor_company.add("Shreya Enterprise");
        exhibitor_name.add("Ashok Patel");
        exhibitor_mobile.add("9898228284 / 9898406504");
        exhibitor_address.add("208, Platinum Plaza, Opp - Kunj Mall, Nr. Shukan Bunglow Cross road, Nikol-Naroda Road, Nikol");
        exhibitor_mail.add("seapuremkt@gmail.com");
        exhibitor_stall.add("N-13, N-14");

        exhibitor_company.add("Silver Water Technology");
        exhibitor_name.add("Mr.Sharad Bhai");
        exhibitor_mobile.add("9979387938");
        exhibitor_address.add("Survey No.211, Plot No.6, Near Ravi Technoforge, Essen Road, Veraval, Rajkot-360024");
        exhibitor_mail.add("silverwatertechnology@yahoo.com");
        exhibitor_stall.add("S-22(A)");

        exhibitor_company.add("Sun Aqua R.O System");
        exhibitor_name.add("Pinakin Patel");
        exhibitor_mobile.add("9327920278");
        exhibitor_address.add("B- 201, Elanza crest, NR. Sigma Corporet, Shindhu Bhavn Road, Off- S.G.Road, Ahmedabad");
        exhibitor_mail.add("sunaquaro@gmail.com");
        exhibitor_stall.add("S-44");

        exhibitor_company.add("Supertronics");
        exhibitor_name.add("Ramesh Bhatt");
        exhibitor_mobile.add("9825257793");
        exhibitor_address.add("3Rd Floor, Golden Arc, B/h, Hasubhai Chembars, opp, V, S, Hospital, Madalpur, Ellisbridge, ");
        exhibitor_mail.add("supertronics1@gmail.com");
        exhibitor_stall.add("N-20");

        exhibitor_company.add("Swastik industries");
        exhibitor_name.add("Pukhraj Jain");
        exhibitor_mobile.add("9313765368");
        exhibitor_address.add("Block B, Wazirpur Industrial Area, Ashok Vihar, Delhi, 110052");
        exhibitor_mail.add("swastikindustries98@gmail.com");
        exhibitor_stall.add("S-35");

        exhibitor_company.add("Ultrafit (V - Tech Water Industries)");
        exhibitor_name.add("Mr.Vivek Patel");
        exhibitor_mobile.add("9429980908");
        exhibitor_address.add("Dharammegh Industrial Estate, Plot No.17, Nr.Nalanda School, Morbi-Rajkot Highway, Morbi-363641");
        exhibitor_mail.add("contact@ultrafitindia.com");
        exhibitor_stall.add("S-7");

        exhibitor_company.add("Uma Cooling Industries");
        exhibitor_name.add("Bipin Bhai");
        exhibitor_mobile.add("9825238608");
        exhibitor_address.add("Shop no -1, Kirtidham App, Nr. Renukabhavmn, Ishwerkrup Road, Varachha, Surat");
        exhibitor_mail.add("umacoolingindustries@gmail.com");
        exhibitor_stall.add("S-40 (A)");

        exhibitor_company.add("V.P. Marketing");
        exhibitor_name.add("Pravin Patel");
        exhibitor_mobile.add("9328283234");
        exhibitor_address.add("Jay Ambe Commercial Center Shop No-25, Kothariya Ring Road Gondal By- Pass Chowkdi, opp,  Nisan Show Room");
        exhibitor_mail.add("v.p.marketing773@gmail.com");
        exhibitor_stall.add("N-56");

        exhibitor_company.add("V.V.Marketing");
        exhibitor_name.add("Vishal Sharma");
        exhibitor_mobile.add("9824294134");
        exhibitor_address.add("311, 312, Star Plaza Nr. Circuit House, ");
        exhibitor_mail.add("vishal@newaterindia.com");
        exhibitor_stall.add("N-15,16,17");

        exhibitor_company.add("Valiant");
        exhibitor_name.add("Ravi Bhai");
        exhibitor_mobile.add("9638050090");
        exhibitor_address.add("");
        exhibitor_mail.add("");
        exhibitor_stall.add("S-28(A)");

        exhibitor_company.add("Voltaic Power System");
        exhibitor_name.add("Harshad bhai");
        exhibitor_mobile.add("9879547570 / 9825641985");
        exhibitor_address.add("Sajatpura Po, Sapavada Ta, Idar.");
        exhibitor_mail.add("hcropower@gmail.com");
        exhibitor_stall.add("S - 29 (B)");

        exhibitor_company.add("Water Care INC");
        exhibitor_name.add("Pareshbhai Patel");
        exhibitor_mobile.add("7624064100 / 9974062181");
        exhibitor_address.add("Shop No.0, Sanjivani Complex, Behind Manoj Medical, Nr.Jain Derasar, Sonal Road, Vejalpur, Ahmedabad.");
        exhibitor_mail.add("watercareinc@yahoo.com");
        exhibitor_stall.add("N-26, N-27");

        exhibitor_company.add("Whirlping RO");
        exhibitor_name.add("Mr. Wade");
        exhibitor_mobile.add("0086 137 6041 5747");
        exhibitor_address.add("China");
        exhibitor_mail.add("wade@whirlping-ro.com");
        exhibitor_stall.add("N-49");

        exhibitor_company.add("White Wave Enterprise Pvt Ltd");
        exhibitor_name.add("Mukulesh Bhai");
        exhibitor_mobile.add("9879008396");
        exhibitor_address.add("White wave House, 47 Shyamal Row House Part -3B Nr Sanjay Tower 100 Ft Anandnagar Road, Satelite,");
        exhibitor_mail.add("info@white wave .co.in");
        exhibitor_stall.add("N-41");

        exhibitor_company.add("Yash Electronics");
        exhibitor_name.add("Bhagvan Parmar");
        exhibitor_mobile.add("9904471106 /9624154810");
        exhibitor_address.add("Shop No-4, Aditya Flats, Near, Mahesh Dairy, Chandranagar Char Rasta, Narayan Nagar Road, Paldi, ");
        exhibitor_mail.add("yashelectronicsro@gmail.com");
        exhibitor_stall.add("N-11, N-12");

        exhibitor_company.add("Zedtech Water Solution Pvt.Ltd");
        exhibitor_name.add("Anil jain");
        exhibitor_mobile.add("9998977660");
        exhibitor_address.add("16, Goyal Intercity Row House, B/h. Drivein Cinema, Nr- Vaibhavlaxmi Temple, Thaltej, Ahmedabag");
        exhibitor_mail.add("md@zedtechro.com");
        exhibitor_stall.add("S-27");


    }

    private void callExhibitorsService() {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = WEB_KEY + API_EXHIBITORS_LIST;
        Map<String, String> params = new HashMap<String, String>();
        params.put("date", getPref(PREF_EXHIBITORS_DATE, "0"));
        deBug(params.toString());
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String error = response.getString("error");
                    String message = response.getString("message");
                    if (error.toUpperCase(Locale.US).equals("FALSE") && message.equals("done")) {
                        JSONObject jsonObject = response.getJSONObject("data");
                        updatePref(PREF_EXHIBITOR_LIST, String.valueOf(jsonObject.getJSONArray("result")));
                        updatePref(PREF_EXHIBITORS_DATE, jsonObject.getString("max"));
                        manageResponse(jsonObject.getJSONArray("result"));
                    } else if (error.toUpperCase(Locale.US).equals("FALSE") && message.equals("already")) {
                        JSONArray jsonArray = new JSONArray(getPref(PREF_EXHIBITOR_LIST, ""));
                        manageResponse(jsonArray);
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

                preToast("Try again");
                Log.e("TAG", "ERROR in LOGIN RESPONSE LISTENER");
            }
        });
        int socketTimeout = 60000;//60 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }

    private void manageResponse(JSONArray jsonArray) {

        exhibitorModelArrayList = new ArrayList<>();
        exhibitorModelArrayList_dummy = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject dataObject = jsonArray.getJSONObject(i);
                ExhibitorModel exhibitorModel = new ExhibitorModel();
                exhibitorModel.setExhibitor_company(dataObject.getString("company_name"));
                exhibitorModel.setExhibitor_name(dataObject.getString("contact_person"));
                exhibitorModel.setExhibitor_mobile(dataObject.getString("contact_number"));
                exhibitorModel.setExhibitor_mail(dataObject.getString("email"));
                exhibitorModel.setExhibitor_stall(dataObject.getString("stall_no"));
                exhibitorModel.setExhibitor_add(dataObject.getString("address"));
                exhibitorModelArrayList.add(exhibitorModel);
                exhibitorModelArrayList_dummy.add(exhibitorModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        manageView();

    }

    private void manageResponseTemp() {

        exhibitorModelArrayList = new ArrayList<>();
        exhibitorModelArrayList_dummy = new ArrayList<>();

        for (int i = 0; i < exhibitor_company.size(); i++) {
            ExhibitorModel exhibitorModel = new ExhibitorModel();
            exhibitorModel.setExhibitor_company(exhibitor_company.get(i));
            exhibitorModel.setExhibitor_name(exhibitor_name.get(i));
            exhibitorModel.setExhibitor_mobile(exhibitor_mobile.get(i));
            exhibitorModel.setExhibitor_mail(exhibitor_mail.get(i));
            exhibitorModel.setExhibitor_stall(exhibitor_stall.get(i));
            exhibitorModel.setExhibitor_add(exhibitor_address.get(i));
            exhibitorModelArrayList.add(exhibitorModel);
            exhibitorModelArrayList_dummy.add(exhibitorModel);

        }
        manageView();

    }

    private void initControl() {

        mListExhibitor = (ListView) findViewById(R.id.listExhibitor);
        searchBox = (EditText) findViewById(R.id.searchBox);

        imgAdv = (ImageView) findViewById(R.id.imgAdv);

        updateUI(imgAdv);

        searchBox.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(String.valueOf(s));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }


    private void manageView() {
        exhibitorListAdapter = new ExhibitorListAdapter(this, exhibitorModelArrayList);
        mListExhibitor.setAdapter(exhibitorListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_item_search) {
            if (searchBox.getVisibility() == View.GONE) {
                searchBox.setVisibility(View.VISIBLE);
                searchBox.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

            } else {
                hideKeyboard();
                searchBox.setVisibility(View.GONE);
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void filter(String charText) {

        charText = charText.toLowerCase();

        exhibitorModelArrayList.clear();

        if (charText.length() == 0) {
            exhibitorModelArrayList.addAll(exhibitorModelArrayList_dummy);
            appendAddItem(exhibitorModelArrayList_dummy);
        } else {
            for (int i = 0; i < exhibitorModelArrayList_dummy.size(); i++) {
                if ((exhibitorModelArrayList_dummy.get(i).getExhibitor_company().toLowerCase().contains(charText)) ||
                        (exhibitorModelArrayList_dummy.get(i).getExhibitor_name().toLowerCase().contains(charText))) {
                    exhibitorModelArrayList.add(exhibitorModelArrayList_dummy.get(i));
                }
            }
            appendAddItem(exhibitorModelArrayList);
        }
    }

    private void appendAddItem(final ArrayList<ExhibitorModel> novaLista) {
        manageView();
    }

    @Override
    public void onBackPressed() {
        if (searchBox.getVisibility() == View.VISIBLE) {
            searchBox.setVisibility(View.GONE);
        } else
            super.onBackPressed();
    }
}
