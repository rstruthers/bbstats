package online.bbstats.controllers;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

import online.bbstats.model.PlayerModel;

public class PitcherPlayerModelComparator implements Comparator<PlayerModel> {

    @Override
    public int compare(PlayerModel player1, PlayerModel player2) {
        if (player1 == null && player2 == null) {
            return 0;
        }
        if (player1 == null && player2 != null) {
            return -1;
        }
        if (player1 != null && player2 == null) {
            return 1;
        }
        
        String player1PrimaryPosition = StringUtils.trimToEmpty(player1.getPrimaryPosition());
        String player2PrimaryPosition = StringUtils.trimToEmpty(player2.getPrimaryPosition());
        
        if (player1PrimaryPosition.equals("P") && player2PrimaryPosition.equals("P")) {
            return 0;
        }
        
        if (!player1PrimaryPosition.equals("P") && player2PrimaryPosition.equals("P")) {
            return 1;
        }
        
        if (player1PrimaryPosition.equals("P") && !player2PrimaryPosition.equals("P")) {
            return -1;
        }
        
        if (player1PrimaryPosition.length() == 0 && player2PrimaryPosition.length() > 0) {
            return 1;
        }
        
        if (player1PrimaryPosition.length() > 0 && player2PrimaryPosition.length() == 0) {
            return -1;
        }
        
        return player1PrimaryPosition.compareTo(player2PrimaryPosition);
    }

}
