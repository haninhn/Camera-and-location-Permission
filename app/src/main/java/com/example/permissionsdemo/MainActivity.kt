package com.example.permissionsdemo

import android.Manifest
import android.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    //permission for cam only
    private  val cameraResultLauncher : ActivityResultLauncher<String> = //asq for camera permetion
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
             isGranted ->
            if( isGranted){
                Toast.makeText(this, "Permission granted for camera.", Toast.LENGTH_LONG).show()}
                 else{
                Toast.makeText(this, "Permission denied for camera.", Toast.LENGTH_LONG).show()
                      }
            }
//permission for cam and location
    private  val cameraAndlocationResultLauncher : ActivityResultLauncher<Array<String>> = //asq for camera permetion
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){
                permissions ->
            /**
            Here it returns a Map of permission name as key with boolean as value
            We loop through the map to get the value we need which is the boolean
            value
             */
                permissions.entries.forEach{
                    val permissionName = it.key
                    val isGranted = it.value
                    if (isGranted) {
                        //check the permission name and perform the specific operation
                        if ( permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                            Toast.makeText(
                                this,
                                "Permission granted for location",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }else{
                            //check the permission name and perform the specific operation
                            Toast.makeText(
                                this,
                                "Permission granted for Camera",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    } else {
                        if ( permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                            Toast.makeText(
                                this,
                                "Permission denied for location",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }else{
                            Toast.makeText(
                                this,
                                "Permission denied for Camera",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnCameraPermission : Button = findViewById(R.id.btnCameraPermission)
        //ytasty 3al version mt3 tlp w permission mwjoda fil manifest ou non  //chek if the permission exist
        btnCameraPermission.setOnClickListener {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                Manifest.permission.CAMERA)){
          // request for permeation kan m3titoch il permission mil loul ki nrj3 n3wd ncliqui y5rjli il dialog hatha
         showRationaleDialog("Permission Demo requires camera access", "Camera cannot be used because Camera access is denied")
              }else{
                  //dialog for (REQUEST FOR  permission(to access to the camera))
            // The registered ActivityResultCallback gets the result of this request.
                      // cameraResultLauncher.launch(Manifest.permission.CAMERA)
            cameraAndlocationResultLauncher.launch(arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,))
              }
        }

    }
    /**
     * Shows rationale dialog for displaying why the app needs permission
     * Only shown if the user has denied the permission request previously
     */
    private  fun showRationaleDialog(title: String, Message: String){
        val build: AlertDialog.Builder = AlertDialog.Builder(this)
        build.setTitle(title)
            .setMessage(Message)
            .setPositiveButton("Cancel"){dialog, _-> dialog.dismiss() }
        build.create().show()
    }
}