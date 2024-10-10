package com.example.firebasedemo.data.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.firebasedemo.data.model.UserData;
import com.example.firebasedemo.utils.BaseResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthRepo {

    private FirebaseAuth firebaseAuth;
    private MutableLiveData<BaseResponse<UserData>> _createUser = new MutableLiveData<>();
    public LiveData <BaseResponse<UserData>> createUser = _createUser;

    private MutableLiveData<BaseResponse<UserData>> _loginUser = new MutableLiveData<>();
    public  LiveData<BaseResponse<UserData>> loginUser = _loginUser;

    public AuthRepo(){
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void signUpWithEmailAndPassword(UserData userData){
        _createUser.postValue(BaseResponse.loading());

        firebaseAuth.createUserWithEmailAndPassword(userData.getEmail(), userData.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        UserData userData1 = new UserData();
                        userData1.setuId(task.getResult().getUser().getUid());

                        _createUser.postValue(BaseResponse.success(userData1));
                    }else {
                        _createUser.postValue(BaseResponse.error(task.getException().getLocalizedMessage()));
                    }
                });
    }

    public void loginWithEmailAndPassword(UserData userData){
        _loginUser.postValue(BaseResponse.loading());

        firebaseAuth.signInWithEmailAndPassword(userData.getEmail(), userData.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        UserData loginData = new UserData();
                        loginData.setuId(task.getResult().getUser().getUid());
                        _loginUser.postValue(BaseResponse.success(loginData));
                    }else {
                        _loginUser.postValue(BaseResponse.error(task.getException().getLocalizedMessage()));
                    }
                });
    }
}
