package com.urh.model;
import android.content.Context;
import android.content.SharedPreferences;
import com.urh.R;

public class Usuario {

    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Usuario(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    private String name;
    private String email;
    private String password;
    private String token;

    public static String getPersistedToken(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(context.getString(R.string.app_name),
                        Context.MODE_PRIVATE);

        String token = sharedPreferences.getString("token", "");
        return "Bearer " + token;
    }

    public static Usuario getPersistedUser(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(context.getString(R.string.app_name),
                        Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        String password = sharedPreferences.getString("password", "");
       Usuario user = new Usuario();
       user.setEmail(email);
       user.setPassword(password);
       return user;
    }

    public static void persistirUsuario(Context context, Usuario usuario) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(context.getString(R.string.app_name),
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", usuario.getToken());
        editor.putString("email", usuario.getEmail());
        editor.putString("name", usuario.getName());
        editor.putString("password", usuario.getPassword());
        editor.commit();
    }

    public static void persistirToken(Context context, String token) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(context.getString(R.string.app_name),
                        Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.commit();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
