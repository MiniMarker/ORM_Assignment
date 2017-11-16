import com.j256.ormlite.logger.LocalLog;

import java.util.Scanner;

/**
 * @author Christian on 23.10.2017.
 */
public class Main {

	public static void main(String[] args) throws Exception {

		System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");

		new UserMenu().startMenu();

		//userMenu.startMenu();

	}
}

