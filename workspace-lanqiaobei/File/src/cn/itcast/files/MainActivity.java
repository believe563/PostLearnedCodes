package cn.itcast.files;

import cn.itcast.service.FileService;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btSave;
	private EditText etFileName;
	private EditText etFileContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btSave=(Button) findViewById(R.id.btSave);
		btSave.setOnClickListener(new OnClickBtSaveListener());
	}
	private final class OnClickBtSaveListener implements View.OnClickListener{
	    @Override
		public void onClick(View v) {
			etFileName=(EditText) findViewById(R.id.fileName);
			etFileContent=(EditText) findViewById(R.id.fileContent);
			String fileName=etFileName.getText().toString();
			String fileContent=etFileContent.getText().toString();
			//提供一个业务类    FileName是自己写的类
			FileService service=new FileService(getApplicationContext());
			try {
				service.save(fileName,fileContent);
				//保存完后要弹出提示信息 提示保存成功
				Toast.makeText(getApplicationContext(), R.string.success, Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
			Toast.makeText(getApplicationContext(), R.string.fail, Toast.LENGTH_SHORT).show();

				e.printStackTrace();
			}
		}
	}
}

