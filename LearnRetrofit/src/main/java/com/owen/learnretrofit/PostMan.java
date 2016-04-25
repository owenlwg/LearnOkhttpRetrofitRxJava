package com.owen.learnretrofit;

/**
 * Created by Owen on 2016/4/25.
 */
public class PostMan {

    /**
     * user : owen
     * password : 123456
     */

    private ArgsBean args;
    /**
     * args : {"user":"owen","password":"123456"}
     * data : Duis posuere augue vel cursus pharetra. In luctus a ex nec pretium. Praesent neque quam, tincidunt nec
     * leo eget, rutrum vehicula magna.
     Maecenas consequat elementum elit, id semper sem tristique et. Integer pulvinar enim quis consectetur interdum
     volutpat.
     * url : https://echo.getpostman.com/post?user=owen&password=123456
     */

    private String data;
    private String url;

    public ArgsBean getArgs() {
        return args;
    }

    public void setArgs(ArgsBean args) {
        this.args = args;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class ArgsBean {
        private String user;
        private String password;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
