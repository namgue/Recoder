package yuhan.hgcq.client.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import yuhan.hgcq.client.R;
import yuhan.hgcq.client.controller.MemberController;
import yuhan.hgcq.client.model.dto.member.MemberDTO;

public class Select extends AppCompatActivity {

    /* View */
    ImageButton privated, share;

    /* 서버와 통신 */
    MemberController mc;

    /* Toast */
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Recoder");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_select);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        /* 서버와 연결할 Controller 생성 */
        mc = new MemberController(this);

        /* View와 Layout 연결 */
        privated = (ImageButton) findViewById(R.id.privated);
        share = (ImageButton) findViewById(R.id.share);

        /* 관련된 페이지 */
        Intent loginPage = new Intent(this, Login.class);
        Intent albumMainPage = new Intent(this, AlbumMain.class);
        Intent groupMainPage = new Intent(this, GroupMain.class);

        /* 개인 버튼 눌림 */
        privated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                albumMainPage.putExtra("isPrivate", true);
                startActivity(albumMainPage);
            }
        });

        /* 공유 버튼 눌림 */
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mc.isLogin(new Callback<MemberDTO>() {
                    @Override
                    public void onResponse(Call<MemberDTO> call, Response<MemberDTO> response) {
                        if (response.isSuccessful()) {
                            MemberDTO loginMember = response.body();
                            groupMainPage.putExtra("loginMember", loginMember);
                            startActivity(groupMainPage);
                        } else {
                            startActivity(loginPage);
                        }
                    }

                    @Override
                    public void onFailure(Call<MemberDTO> call, Throwable t) {
                        handler.post(() -> {
                            Toast.makeText(Select.this, "서버와 통신 실패했습니다. 네트워크를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        });
    }
}
