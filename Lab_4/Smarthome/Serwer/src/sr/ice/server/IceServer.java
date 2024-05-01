package sr.ice.server;

import Smarthome.DeviceInfo;
import Smarthome.DeviceType;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import sr.ice.server.device.coffeemaker.CoffeeMaker;
import sr.ice.server.device.speaker.MP3Player;
import sr.ice.server.device.speaker.Television;
import sr.ice.server.deviceManager.DeviceManager;
import sr.ice.server.sensor.CO;
import sr.ice.server.sensor.Thermometer;

public class IceServer {

	private static int type = 1;
	private ObjectAdapter initializeAdapter1(Communicator communicator) {
		ObjectAdapter adapter = communicator.createObjectAdapter("Adapter1");

		Television television1Servant = new Television("telewizja1");
		Television television2Servant = new Television("telewizja2");
		MP3Player mp3PlayerServant = new MP3Player("mp3Player");
		CoffeeMaker coffeeMakerServant = new CoffeeMaker("ekspres");

		adapter.add(television1Servant, new Identity("Telewizja1", "TELEVISION"));
		adapter.add(television2Servant, new Identity("Telewizja2", "TELEVISION"));
		adapter.add(mp3PlayerServant, new Identity("Mp3", "MP3PLAYER"));
		adapter.add(coffeeMakerServant, new Identity("Ekspres", "COFFEEMAKER"));

		System.out.println("[IceServer] initializeAdapter1");
		return adapter;
	}

	private ObjectAdapter initializeAdapter2(Communicator communicator) {
		ObjectAdapter adapter = communicator.createObjectAdapter("Adapter2");

		Thermometer thermometerServant = new Thermometer("termometr");
		CO coSensorServant = new CO("czujnik");

		adapter.add(thermometerServant, new Identity("Termometr", "THERMOMETER"));
		adapter.add(coSensorServant, new Identity("Czujnik", "CO"));

		System.out.println("[IceServer] initializeAdapter2");
		return adapter;
	}

	public void t1(String[] args) {
		int status = 0;
		Communicator communicator = null;

		try {
			// 1. Initialize ICE
			communicator = Util.initialize(args);

			// 2.Config adapter
			ObjectAdapter adapter;

			if (type == 1) {
				adapter = initializeAdapter1(communicator);
			} else {
				adapter = initializeAdapter2(communicator);
			}

			// 3. Add device manager
			DeviceManager deviceManagerServant = new DeviceManager();
			adapter.add(deviceManagerServant, new Identity("Home", "deviceManager"));

			// 4. Activate adapter
			adapter.activate();

			System.out.println("Entering event processing loop...");

			communicator.waitForShutdown();

		} catch (Exception e) {
			e.printStackTrace(System.err);
			status = 1;
		}
		if (communicator != null) {
			try {
				communicator.destroy();
			} catch (Exception e) {
				e.printStackTrace(System.err);
				status = 1;
			}
		}
		System.exit(status);
	}


	public static void main(String[] args) {
		IceServer app = new IceServer();
		String loadConfig = "--Ice.Config=config/config.server1";
		if (args.length > 0 && Integer.parseInt(args[0]) == 2) {
			loadConfig = "--Ice.Config=config/config.server2";
			type = 2;
		}
		String[] arg = {loadConfig};
		app.t1(arg);
	}
}