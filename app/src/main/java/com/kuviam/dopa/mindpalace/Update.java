package com.kuviam.dopa.mindpalace;

/**
public class Update extends ActionBarActivity {
    EditText add_name;
    byte[] image;
    ImageView  img;
    Bitmap theImage;
    int imageId;
    byte imageInByte[];

    Button add_save_btn, add_view_all, update_btn, update_view_all,add_image;
    LinearLayout add_view, update_view;

    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;


    String 	valid_name = null,
            Toast_msg = null,
            valid_user_id = "";
    int USER_ID;
    DataBaseHandler dbHandler = new DataBaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__update__loci);

        // set screen
        Set_Add_Update_Screen();

        // set visibility of view as per calling activity
        String called_from = getIntent().getStringExtra("called");

        if (called_from.equalsIgnoreCase("add")) {
            add_view.setVisibility(View.VISIBLE);
            update_view.setVisibility(View.GONE);
        } else {

            update_view.setVisibility(View.VISIBLE);
            add_view.setVisibility(View.GONE);
            USER_ID = Integer.parseInt(getIntent().getStringExtra("USER_ID"));

            Loci loci = dbHandler.getLoci(USER_ID);



            add_name.setText(loci.getName());

            Intent intnt = getIntent();
            byte[] image = (byte[])loci.getImage();
            image.clone();
            if(image!=null){
                int a= loci.getID();

                Toast_msg = "successfully";
                Show_Toast(Toast_msg);

            }
            ByteArrayInputStream imageStream = new ByteArrayInputStream(image);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            img.setImageBitmap(theImage);

            Toast_msg = "success";
            Show_Toast(Toast_msg);
             dbHandler.close();
        }


        add_name.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Is_Valid_Person_Name(add_name);
            }
        });


        final String[] option = new String[] { "Take from Camera",
                "Select from Gallery" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Option");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Log.e("Selected Item", String.valueOf(which));
                if (which == 0) {
                    callCamera();
                }
                if (which == 1) {
                    callGallery();
                }

            }
        });
        final AlertDialog dialog = builder.create();


        add_image.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                dialog.show();

            }
        });

        add_save_btn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                // check the value state is null or not
                if (valid_name != null) {

                    dbHandler.addLoci(new Loci(valid_name, imageInByte));


                    Toast_msg = "Data inserted successfully";
                    Show_Toast(Toast_msg);
                    Reset_Text();
                    Reset_Image();

                }

            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
              valid_name = add_name.getText().toString();


                // check the value state is null or not
                if (valid_name != null ) {

                    dbHandler.updateContact(new Loci(valid_name,image));
                    dbHandler.close();
                    Toast_msg = "Data Update successfully";
                    Show_Toast(Toast_msg);
                    Reset_Text();
                    Reset_Image();
                } else {
                    Toast_msg = "Sorry Some Fields are missing.\nPlease Fill up all.";
                    Show_Toast(Toast_msg);
                }

            }
        });
        update_view_all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent view_user = new Intent(Update.this,
                        Add.class);
                view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(view_user);
                finish();
            }
        });

        add_view_all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent view_user = new Intent(Update.this,
                        Add.class);
                view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(view_user);
                finish();
            }
        });

    }



    public void Set_Add_Update_Screen() {

        add_name = (EditText) findViewById(R.id.add_name);
        img=(ImageView)findViewById(R.id.imageView3);

        add_image= (Button) findViewById(R.id.add_image);

        add_save_btn = (Button) findViewById(R.id.add_save_btn);
        update_btn = (Button) findViewById(R.id.update_btn);
        add_view_all = (Button) findViewById(R.id.add_view_all);
        update_view_all = (Button) findViewById(R.id.update_view_all);

        add_view = (LinearLayout) findViewById(R.id.add_view);
        update_view = (LinearLayout) findViewById(R.id.update_view);

        add_view.setVisibility(View.GONE);
        update_view.setVisibility(View.GONE);

    }

    public void Is_Valid_Person_Name(EditText edt) throws NumberFormatException {
        if (edt.getText().toString().length() <= 0) {
            //edt.setError("Accept Alphabets Only.");
           // valid_name = null;

        } else {
            valid_name = edt.getText().toString();
        }

    }

    public void Show_Toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void Reset_Text() {

        add_name.getText().clear();
    }

    private void Reset_Image() {
        img.setImageBitmap(null);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case CAMERA_REQUEST:

                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    imageInByte = stream.toByteArray();

                    // Inserting Contacts
                    Log.d("Insert: ", "Inserting ..");
                    setImage(imageInByte);

                }
                break;
            case PICK_FROM_GALLERY:
                Bundle extras2 = data.getExtras();

                if (extras2 != null) {
                    Bitmap yourImage = extras2.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    imageInByte = stream.toByteArray();

                    // Inserting Contacts
                    Log.d("Insert: ", "Inserting ..");
                    setImage(imageInByte);

                }

                break;
        }
    }

    private void setImage(byte[] imageInByte) {

        ByteArrayInputStream imageStream = new ByteArrayInputStream(imageInByte);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        img.setImageBitmap(theImage);


    }

   
    public void callCamera() {
        Intent cameraIntent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra("crop", "true");
        cameraIntent.putExtra("aspectX", 0);
        cameraIntent.putExtra("aspectY", 0);
        cameraIntent.putExtra("outputX", 200);
        cameraIntent.putExtra("outputY", 150);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }



    public void callGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_GALLERY);

    }

}
**/