package tntrun;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;

import org.bukkit.Bukkit;

public class VersionChecker {
	
	private static VersionChecker instance;

	public VersionChecker(){
		instance = this;
	}
	
	public static VersionChecker get(){
		return instance;
	}
	
	public String getVersion(){
		try {
			byte[] ver = get(new URL("http://the-tadesk.tk/updater/tntrun/"));
			if (ver == null) {
				return "error";
			}

			String data = new String(ver);
			if(data == null || data.isEmpty()){
				return "error";
			}
			return data;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Bukkit.getLogger().log(Level.WARNING, "[TNTRun] An error was occurred while checking version! Please report this here: https://www.spigotmc.org/threads/tntrun.67418/");
			return "error";
		}
	}
	
	public static byte[] get(URL url){
		try{
			HttpURLConnection c = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);

	        c.setRequestMethod("GET");
	        c.setRequestProperty("Host", url.getHost());

			BufferedInputStream in = new BufferedInputStream(c.getInputStream());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Streams.pipeStreams(in, out);
			return out.toByteArray();
	        }
	        catch (UnknownHostException e) {
				Bukkit.getLogger().log(Level.WARNING, "[TNTRun] Failed to connect to [" + url.getHost() + "] to check version.  Please report this here: https://www.spigotmc.org/threads/tntrun.67418/");
			}
	        catch (IOException e) {
	        	e.printStackTrace();
	        	Bukkit.getLogger().log(Level.WARNING, "[TNTRun] An error was occured while checking version! Please report this here: https://www.spigotmc.org/threads/tntrun.67418/");
	    }
		return null;
	}
}
