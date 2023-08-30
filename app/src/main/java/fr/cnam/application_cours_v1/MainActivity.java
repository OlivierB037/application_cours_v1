package fr.cnam.application_cours_v1;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.List;

import fr.cnam.application_cours_v1.Fragments.Fragment1;
import fr.cnam.application_cours_v1.Fragments.Fragment2;
import fr.cnam.application_cours_v1.Fragments.Fragment3;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    protected static boolean paused = false;
    private final String TAG = "MainActivity call";
    private final int OPEN_FILE_CODE = 1;
    private final int OPEN_IMAGE_PERMISSION = 73;
    private final int CHOOSE_FILE_PERMISSION = 37;
    private final int CREATE_FILE_PERMISSION = 377;
    private Uri pathUri;

//    ServiceListener mServiceListener;
    public static boolean prefChanged;
//    interface ServiceListener{
//        void ServiceAction();
//        void toastText(String text);
//    }

    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        }
    };


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("oncreate", "mainActivity");
        //setTheme(R.style.Th);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String theme = preferences.getString("theme","unknown");

        if(theme.equals("Light")){
            Log.i(TAG,"theme preference is :" + theme);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else if (theme.equals("Dark")){
            Log.i(TAG,"theme preference is :" + theme);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{ Log.i(TAG,"theme preference unknown:" + theme);}
        setContentView(R.layout.activity_main);
        Log.i(TAG, "local class: "+ getLocalClassName());
        Log.i(TAG, "class from componentName: "+ getComponentName().getClassName());


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        

        //getSupportActionBar().hide();
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_main, new Fragment1())
                    .commit();
            Log.i("etat sauvegarde", "savedInstanceState = null");
        }*/

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*@Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.i("MainActivity", "onAttachfragment called");
        final FragmentContainerView fragmentView = (FragmentContainerView) findViewById(R.id.fragment1);
        if(fragmentView == null){ Log.i("MainActivity call", "FragmentContainerview is null");}
        RelativeLayout relativeLayout = (RelativeLayout) fragment.getActivity().findViewById(R.id.relativeFragLayout);
        if(fragmentView == null){ Log.i("MainActivity call", "FragmentContainerview is null");}
        //        TextView textView = (TextView) findViewById(R.id.textview1);
//        textView.setText("test depuis mainActivity");


    }*/

    @Override
    protected void onStart() {
        super.onStart();
        
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.action_settings){
            Log.i(TAG, "onOptionItemSelected called with actions_settings");
            Intent settingsIntent = new Intent(Settings.ACTION_SETTINGS);
            ComponentName componentName = settingsIntent.resolveActivity(getPackageManager());
            if(componentName != null){
                Log.i(TAG, "componentName is not null");
                String classe = "class: " + componentName.getClassName();
                Log.i(TAG, classe);
                String packageName = "package: "+ componentName.getPackageName();
                Log.i(TAG, packageName );
                startActivity(settingsIntent);
                return true;
            }
        }
        else if(item.getItemId() == R.id.map_item){
            Log.i(TAG, "onOptionItemSelected called with map_item");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=new-york"));

            startActivity(mapIntent);
        }
        else if (item.getItemId() == R.id.app_settings){
            Intent appSettingsIntent = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(appSettingsIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        paused = true;
        Log.i(TAG,"onPause called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume called");
        if (prefChanged){
            Log.i(TAG,"prefChanged is true");
            prefChanged = false;
            recreate();
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(
                myReceiver,new IntentFilter("My Event"));

    }

    public void updateUi(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String theme = preferences.getString("theme","unknown");
        if(theme.equals("Light")){
            Log.i(TAG,"theme preference is :" + theme);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else if (theme.equals("Dark")){
            Log.i(TAG,"theme preference is :" + theme);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{ Log.i(TAG,"theme preference unknown:" + theme);}
        setContentView(R.layout.activity_main);
    }
/*
**************** Carlo floating action button ***************
 */

    public void onOff(View v){
        Log.i(TAG, "onOff: test1");
        Fragment fragment = getVisibleFragment();
        Log.i(TAG, "onOff: id =" + fragment.getId());
        //String name = fragment.getArguments().getString("name");
        Log.i(TAG, "onOff: tag=" + fragment.getTag());
        if(fragment instanceof Fragment1){
            Log.i("MainActivity call", "onOff fragment1");
            Toast.makeText(fragment.getContext(),"fragment1", Toast.LENGTH_LONG).show();
        }
        else if(fragment instanceof Fragment2){
            Log.i("MainActivity call", "onOff fragment2");
            Toast.makeText(fragment.getContext(),"fragment2", Toast.LENGTH_LONG).show();
        }
        else if(fragment instanceof Fragment3){
            //Log.i("MainActivity call", "visible fragment : " + name );
            Toast.makeText(fragment.getContext(),"fragment 3", Toast.LENGTH_LONG).show();
        }
        else{
            Log.i(TAG," visible fragment class simple name = " + fragment.getClass().getSimpleName());

            Toast.makeText(this,"you're in " + fragment.getClass().getSimpleName(),Toast.LENGTH_LONG).show();
        }
//        TextView textView = (TextView) getView().findViewById(R.id.textfrag2);
//        Log.i("Fragment2 call", fragment.toString());
    }



    private Fragment getVisibleFragment(){
//        FragmentManager fragmentManager = this.getSupportFragmentManager();
//        List<Fragment> fragments = fragmentManager.getFragments();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("fragment_container");
        List<Fragment> fragments = fragment.getChildFragmentManager().getFragments();
        for (Fragment f : fragments){
            if(f!=null && f.isVisible()){
                Log.i("MainActivity call", "getvisiblefragment returns a fragment");
                return f;
            }
        }
        Log.i("MainActivity call", "getvisiblefragment returns null");
        return null;


    }

    /*
    ********************* INTENTS FRAGMENT **********************
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(requestCode == OPEN_FILE_CODE && data != null) {
                Log.i(TAG, "data: " + data.getDataString());
                pathUri = data.getData();
                //File selectedFile = new File();

                requestFilePermission(OPEN_IMAGE_PERMISSION);
                //openIntent.addCategory(Intent.CATEGORY_OPENABLE);

                if(pathUri == null){
                    Log.i(TAG,"content type is null");
                }
                else {
                    Log.i(TAG, "intent result type:" + pathUri.toString());
                }
            }
            else{ Log.i(TAG, "data is null or code is wrong");}
        }catch(RuntimeException e){
            Log.i(TAG, e.toString());
            Toast.makeText(fr.cnam.application_cours_v1.MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG,"onrequestPermission called");
        boolean granted = true;
        for(int i = 0; i<grantResults.length;i++){
            if(grantResults[i] == PackageManager.PERMISSION_DENIED){

                Log.i(TAG, "permission code " + requestCode + " denied for: " + permissions[i]);
                granted = false;
            }
        }
        if(granted){
            Log.i(TAG, "granted condition is true");
            switch (requestCode) {
                case CHOOSE_FILE_PERMISSION: getFile();
                break;
                case OPEN_IMAGE_PERMISSION: openImage(pathUri);
                break;
                case CREATE_FILE_PERMISSION: Log.i(TAG,"create file");
                break;
                default:Log.i(TAG,"unknown request code");
                break;

            }
        }
        if (requestCode == OPEN_FILE_CODE){
            Log.i(TAG,"request code is 37");


        }
    }


    public void requestFilePermission(View v){

        if (v.getId() == R.id.openButton) {
            Log.i(TAG,"view is: openButton");
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, CHOOSE_FILE_PERMISSION);
            Log.i(TAG, "permission Line passed");
        }

    }

    public void requestFilePermission(int permissionCode){
        requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, permissionCode);
        Log.i(TAG,"permission Line passed in overload function");
    }

    public void getFile(){
        Log.i("Fragment2 call","openFile() called");

        Intent openIntent = new Intent(Intent.ACTION_GET_CONTENT);
        openIntent.setType("*/*");
        //File path = Environment.getExternalStorageDirectory();
        //Uri uri = Uri.fromFile(path);
        //Log.i(TAG,"uri type: " + uri.toString());
        //openIntent.setData(uri);
        openIntent.putExtra(Intent.EXTRA_FROM_STORAGE, true);

        openIntent.addCategory(Intent.CATEGORY_OPENABLE);
//        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
//        chooserIntent.putExtra(Intent.EXTRA_INTENT,openIntent);
        startActivityForResult(Intent.createChooser(openIntent,"choix"),OPEN_FILE_CODE);

        /*
         * modifier pour récupérer plusieurs fichiers
         * après avoir choisi le fichier, afficher nom(s) et type, bouton share avec createchooser
         * créer intent type folder pour choisir destination
         * créer un log avec create intent et ajouter validation de la copie sur le meme event
         * */



    }
    public void openImage(Uri uri){
        Intent openImageIntent = new Intent(Intent.ACTION_VIEW);
        openImageIntent.setDataAndType(uri, "image/*");
        startActivity(openImageIntent);
    }



    public Uri getPathUri() {
        return pathUri;
    }

    /*
    **************************** INTENT SERVICE *****************************
     */

//    public void setServiceListener(ServiceListener mServiceListener) {
//        this.mServiceListener = mServiceListener;
//    }


    /*
    ********************************* THREADS FRAGMENT *********************************
     */


}