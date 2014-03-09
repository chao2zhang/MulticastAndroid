package com.example.testandroid;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener, android.content.DialogInterface.OnClickListener{
	
	private Button sendButton;
	private EditText photoname;
	private EditText ipAddress;
	private EditText portNumber;
	private AlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().penaltyLog().build());
		setContentView(R.layout.activity_main);
		sendButton = (Button)findViewById(R.id.sendButton);
		sendButton.setOnClickListener(this);
		photoname = (EditText)findViewById(R.id.photoName);
		ipAddress = (EditText)findViewById(R.id.ipAddress);
		portNumber = (EditText)findViewById(R.id.portNumber);		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.sendButton) {
			try {
				String msg = photoname.getText().toString();
				String ipAddr = ipAddress.getText().toString();
				InetAddress inetAddr = InetAddress.getByName(ipAddr);
				int portNum = Integer.parseInt(portNumber.getText().toString());
				
				MulticastSocket ms = new MulticastSocket();
				DatagramPacket dp = new DatagramPacket(msg.getBytes(), msg.length(), inetAddr, portNum);
				ms.joinGroup(inetAddr);
				ms.setTimeToLive(2);
				ms.send(dp);
				AlertDialog.Builder adb = new AlertDialog.Builder(this);
				adb.setTitle("Done");
				adb.setPositiveButton("OK", this);
				dialog = adb.show();
			} catch (Exception e) {
				StringWriter writer = new StringWriter();
				PrintWriter out = new PrintWriter(writer);
				e.printStackTrace(out);
				
				AlertDialog.Builder adb = new AlertDialog.Builder(this);
				adb.setTitle("Exception");
				adb.setMessage(writer.toString());
				adb.setPositiveButton("OK", this);
				dialog = adb.show();
			}
		}
	}


	@Override
	public void onClick(DialogInterface dialog, int which) {
		dialog.cancel();
	}

}
