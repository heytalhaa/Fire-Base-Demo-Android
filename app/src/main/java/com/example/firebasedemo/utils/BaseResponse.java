package com.example.firebasedemo.utils;

public class BaseResponse<T> {

        public enum Status {
            ONLOADING,
            ONSUCCESS,
            ONERROR
        };

        private Status status;
        private T data;
        private String error;

        private BaseResponse(Status status , T data , String error){
            this.status = status;
            this.data = data;
            this.error = error;
        }

        public static <T> BaseResponse<T> success(T data){
            return new BaseResponse<>(Status.ONSUCCESS , data , null);
        }

        public static <T> BaseResponse<T> error(String error){
            return new BaseResponse<>(Status.ONERROR , null , error);
        }

        public static <T> BaseResponse<T> loading(){
            return new BaseResponse<>(Status.ONLOADING , null , null);
        }

        public Status getStatus(){
            return status;
        }

        public T getData(){
            return data;
        }
        public String getError(){
            return error;
        }

}
