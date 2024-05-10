/**
 * Factory class responsible for creating instances of different player types.
 * Provides a static method to build and return a player based on the specified type.
 * Supported player types include "human," "clever," "whatever," and "genius."
 * Each player type corresponds to a specific implementation of the Player interface.
 * @author Yinon Kedem
 * @see Player
 * @see HumanPlayer
 * @see CleverPlayer
 * @see WhateverPlayer
 * @see GeniusPlayer
 */
class PlayerFactory {
    /**
     * Builds and returns a player instance based on the specified player type.
     * @param type The type of player to build ("human," "clever," "whatever," or "genius").
     * @return An instance of the Player interface corresponding to the specified type.
     */
    public static Player buildPlayer(String type){
        Player build = null;
        switch(type){
            case "human":
                build = new HumanPlayer();
                break;
            case "clever":
                build = new CleverPlayer();
                break;
            case "whatever":
                build = new WhateverPlayer();
                break;
            case "genius":
            build = new GeniusPlayer();
                break;
            default:
                build = null;
                break;
        }
        return build;
    }

}