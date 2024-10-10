package com.example.firebasedemo.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.firebasedemo.data.model.UserData;
import com.example.firebasedemo.data.repo.AuthRepo;
import com.example.firebasedemo.utils.BaseResponse;

public class LoginViewModel extends ViewModel {
    AuthRepo authRepo = new AuthRepo();

    public LiveData<BaseResponse<UserData>> loginUser = authRepo.loginUser;

    public void setLoginUser(UserData userData){
        authRepo.loginWithEmailAndPassword(userData);
    }
}
