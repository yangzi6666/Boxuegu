package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.utils.MD5Utils;


public class LoginActivity extends AppCompatActivity {
    private TextView tv_main_title;
    private TextView tv_back,tv_register,tv_find_psw;
    private Button btn_login;
    private String userName,psw,spPsw;
    private EditText et_user_name,et_psw;

    private  void init(){
        tv_main_title=(TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        tv_back=(TextView) findViewById(R.id.tv_back);
        tv_register=(TextView) findViewById(R.id.tv_register);
        tv_find_psw=(TextView) findViewById(R.id.tv_find_psw);
        btn_login=(Button)findViewById(R.id.btn_login);
        et_psw=(EditText)findViewById(R.id.et_psw);
        et_user_name=(EditText)findViewById(R.id.et_user_name);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                userName = et_user_name.getText().toString().trim();
                psw = et_psw.getText().toString().trim();
                String md5psw = MD5Utils.md5(psw);
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(LoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(LoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }else if(md5psw.equals(spPsw)){
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                 saveLoginStatus(true,userName);
                 Intent data = new Intent();
                 data.putExtra("isLogin",true);
                 setResult(RESULT_OK,data);
                 Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                 LoginActivity.this.finish();
                    return;
                }else if(spPsw!=null&&!TextUtils.isEmpty(spPsw)&&!md5psw.equals(spPsw)){
                    Toast.makeText(LoginActivity.this,"输入的用户名和密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(LoginActivity.this,"此用户不存在",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            //从注册界面传递过来的用户名
            String userName = data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                et_user_name.setText(userName);
                //设置光标的位置
                et_user_name.setSelection(userName.length());
            }
        }
    }

    private void saveLoginStatus(boolean status, String userName) {
        //loginInfo表示文件名
        SharedPreferences sp =getSharedPreferences("loginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        editor.putBoolean("isLogin",status);//存入boolean类型的登录状态
        editor.putString("loginUserName",userName);//存入登录状态时的用户名
        editor.commit();//提交修
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}//

