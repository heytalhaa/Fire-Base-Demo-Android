package com.example.firebasedemo.ui.signup;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.firebasedemo.R;
import com.example.firebasedemo.data.model.UserData;
import com.example.firebasedemo.databinding.ActivitySignupBinding;
import com.example.firebasedemo.utils.BaseResponse;

public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private SignupViewModel signupViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        signupViewModel.createUser.observe(this, new Observer<BaseResponse<UserData>>() {
            @Override
            public void onChanged(BaseResponse<UserData> userDataBaseResponse) {
                switch (userDataBaseResponse.getStatus()){
                    case ONLOADING:
                        Toast.makeText(SignupActivity.this, "Loading", Toast.LENGTH_SHORT).show();
                        break;
                    case ONSUCCESS:
                        Toast.makeText(SignupActivity.this, "id "+ userDataBaseResponse.getData().getuId(), Toast.LENGTH_SHORT).show();
                        break;
                    case ONERROR:
                        Toast.makeText(SignupActivity.this, userDataBaseResponse.getError(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        binding.signupBtn.setOnClickListener(view -> {
            String email = binding.emailET.getText().toString();
            String password = binding.passwordET.getText().toString();
            UserData userData = new UserData();
            userData.setEmail(email);
            userData.setPassword(password);
            signupViewModel.signUp(userData);
        });
    }
}