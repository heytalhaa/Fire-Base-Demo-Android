package com.example.firebasedemo.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.firebasedemo.R;
import com.example.firebasedemo.data.model.UserData;
import com.example.firebasedemo.databinding.ActivityLoginBinding;
import com.example.firebasedemo.ui.signup.SignupActivity;
import com.example.firebasedemo.utils.BaseResponse;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        binding.signUpTV.setOnClickListener(view -> {
            startActivity(new Intent(this, SignupActivity.class));
        });

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.loginUser.observe(this, new Observer<BaseResponse<UserData>>() {
            @Override
            public void onChanged(BaseResponse<UserData> userDataBaseResponse) {
                switch (userDataBaseResponse.getStatus()){
                    case ONLOADING:
                        Toast.makeText(LoginActivity.this, "Loading......", Toast.LENGTH_SHORT).show();
                        break;
                    case ONSUCCESS:
                        Toast.makeText(LoginActivity.this, "LogIN" + userDataBaseResponse.getData().getuId(), Toast.LENGTH_SHORT).show();
                        break;
                    case ONERROR:
                        Toast.makeText(LoginActivity.this, userDataBaseResponse.getError(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        binding.loginBtn.setOnClickListener(view -> {
            String email = binding.emailET.getText().toString();
            String password = binding.passwordET.getText().toString();
            UserData userData = new UserData();
            userData.setEmail(email);
            userData.setPassword(password);
            loginViewModel.setLoginUser(userData);
        });
    }
}