/**
 * Factory class responsible for creating instances of different renderer types.
 * Provides a static method to build and return a renderer based on the specified type.
 * Supported renderer types include "console" and "none."
 * Each renderer type corresponds to a specific implementation of the Renderer interface.
 * @author Yinon Kedem
 * @see Renderer
 * @see ConsoleRenderer
 * @see VoidRenderer
 */
class RendererFactory {
    /**
     * Builds and returns a renderer instance based on the specified renderer type.
     * @param type The type of renderer to build ("console" or "none").
     * @param size The size of the game board for rendering (used by some renderer implementations).
     * @return An instance of the Renderer interface corresponding to the specified type.
     * @throws IllegalArgumentException if an unsupported renderer type is provided.
     */
    public static Renderer buildRenderer(String type, int size){
        Renderer build = null;
        switch(type){
            case "console":
                build = new ConsoleRenderer(size);
                break;
            case "none":
                build = new VoidRenderer();
                break;
            default:
                return build;
        }
        return build;
    }

}