package players.bots.botb;

import common.Data;
import players.common.DataPlayer;
import players.bots.common.DataBot;

public class Launch {

	public static void main(String[] args) {
		if (args.length == 1)
			System.out.println("Je suis " + args[0] +" le bot B, j'utilise aussi "+ Data.message + ", " + DataPlayer.message + " et " + DataBot.message );
		else
			System.out.println("Je suis le bot B, j'utilise aussi "+ Data.message + ", " + DataPlayer.message + " et " + DataBot.message );
	}

}