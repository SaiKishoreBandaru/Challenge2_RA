package com.example.challenge2;


import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			/*getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();*/
		}
		
		Button b2 = (Button) findViewById(R.id.button1);
		b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// c.startScan();

				// startService(new
				// Intent(MainActivity.this,ConnectionService.class));
				
				new SendFile().execute("");
				
				String s="";
				File sdCard = Environment.getExternalStorageDirectory();
		        File directory = new File (sdCard.getAbsolutePath() + "/Data");
		        if(!directory.exists())
		        directory.mkdirs();
		        String fname = "result1.txt";
		        
		        File file = new File (directory, fname);
		        
		        try {
		            if(file.exists())
		            {
		            	FileInputStream in=new FileInputStream(file);
		            	  DataInputStream dataIO = new DataInputStream(in);
		            	    String strLine = null;

		            	    while ((strLine = dataIO.readLine()) != null) {
		            	        s=s+strLine+"\n";
		            	        
		            	    }
		            }

		        } catch (Exception e) {
		        	
		        	Log.i("error", "it came here");
		               e.printStackTrace();
		        }
		        
		      Log.i("data", s);
		        
		        TextView t = (TextView) findViewById(R.id.tv1);
		        t.setText(s);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}


class SendFile extends AsyncTask<String, Void, String> {
	

    private Exception exception;

    protected String doInBackground(String... urls) {
        try {
           
        	
        	
        	JSch ssh = new JSch();
		        JSch.setConfig("StrictHostKeyChecking", "no");
		        Session session;
				try {
					session = ssh.getSession("group1", "134.193.136.114", 22);
				
		        session.setPassword("group1");
		        session.connect();
		        Channel channel = session.openChannel("sftp");
		        channel.connect();
		        ChannelSftp sftp = (ChannelSftp) channel;
		        
		        File sdCard = Environment.getExternalStorageDirectory(); 
				File directory = new File (sdCard.getAbsolutePath() + "/Data");
			    
				sftp.get("/home/group1/result.txt",directory+"/result1.txt");
		        
//		     /   sftp.put(directory+"/GPS.txt", "/home/cloudera/");
		    	
				} catch (JSchException e) {
					// TODO Auto-generated catch block
					Log.i(null,e.toString());
					e.printStackTrace();
					
				} catch (SftpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}

        	
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
		return null;
    }

    protected void onPostExecute() {
        // TODO: check this.exception 
        // TODO: do something with the feed
    }

}



