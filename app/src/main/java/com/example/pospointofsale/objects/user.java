package com.example.pospointofsale.objects;

public class user {
        public String email;
        public String password;
        public String username;
        public user() {
        }

        public user(String email, String password , String username)
        {
            this.email = email;
            this.password = password;
            this.username = username;

        }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

}
