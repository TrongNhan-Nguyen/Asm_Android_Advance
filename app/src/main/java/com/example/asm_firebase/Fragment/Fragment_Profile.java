package com.example.asm_firebase.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.asm_firebase.Adapter.Adapter_ListView_Transcript;
import com.example.asm_firebase.DAO.DAO_Transcript;
import com.example.asm_firebase.Model.Course;
import com.example.asm_firebase.Model.FireBaseCallback;
import com.example.asm_firebase.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Profile extends Fragment {
    private View view;
    private ImageView imgAvatar;
    private TextView txtEmail, txtType;
    GoogleSignInClient mGoogleSignInClient;
    String email;

    public Fragment_Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        imgAvatar = (ImageView) view.findViewById(R.id.fProfile_ImgAvatar);
        txtEmail = (TextView) view.findViewById(R.id.fProfile_TxtMail);
        txtType = (TextView) view.findViewById(R.id.fProfile_TxtType);

        Intent intent = getActivity().getIntent();
        String type = intent.getStringExtra("type");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (type != null){
            email = "nhanntps10674@fpt.edu.vn";
            txtEmail.setText(email);
            txtType.setText("Local");
            imgAvatar.setImageResource(R.drawable.avatar_local);
        }else if (acct != null){
            email = "google_mail@gmail.com";
            txtEmail.setText(email);
            txtType.setText("Google");
            imgAvatar.setImageResource(R.drawable.avatar_google);
        }else{
            email = "facebook_mail@gmail.com";
            txtEmail.setText(email);
            txtType.setText("Facebook");
            imgAvatar.setImageResource(R.drawable.avatar_facebook);
        }

        return view;
    }

}
