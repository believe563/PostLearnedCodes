package cn.itcast.service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.R.integer;
import android.content.Context;

public class FileService{

	private Context context;//通过上下文对象迅速得到文件输出流对象
	public FileService(Context context) {
		super();
		this.context = context;
	}
	/**
	 * 文件保存
	 * @param fileName 文件名称
	 * @param fileContent 文件内容
	 */
	public void save(String fileName, String fileContent) throws Exception{
		// TODO Auto-generated method stub
		//java a2se
		//首先得到一个文件的输出流对象
		//context.MODE_PRIVATE私有操作模式：创建出来的文件只能被本应用访问，其他应用无法访问，并且写入的文件会覆盖源文件内容
		FileOutputStream outStream=context.openFileOutput(fileName, context.MODE_PRIVATE);//文件名称和文件的操作模式，，，这里有异常  将它抛给Activity类，提示用户保存失败
		//向输出流中写入数据
		outStream.write(fileContent.getBytes());
		//关掉流
		outStream.close();
	}
	/**
	 * 读取文件内容
	 * @param fileName
	 * @return 文件内容
	 * @throws Exception
	 */
	public String read(String fileName)throws Exception{
		FileInputStream inStream=context.openFileInput(fileName);//openFileInput可以快速的得到文件的输入流对象
		 ByteArrayOutputStream outStream=new ByteArrayOutputStream();//把每次读到的对象都存到内存中
		byte[] buffer=new byte[1024];//自己定义数组的大小
		int len=0;
		while ((len=inStream.read(buffer))!=-1) {//每次读取的内容都会覆盖上次读取的内容，所以要把每次读取到的内容都存起来，所以这时候可以使用一个往内存输出的输出流对象ByteArrayOutputStream
			outStream.write(buffer,0,len);
		}
		byte[] data=outStream.toByteArray();//得到内存中的所有数据，此时的数据是前面保存时的getbytes（）方法的二进制数据
		
		return new String(data);//得到字符串类型的数据并将其返回;
	}

}
