package nebulous.loreEngine.core.game;

public interface IUpdatable {
	
	public void create(Game game, Scene scene);
	public void destroy(Game game, Scene scene);
	public void tick(Game game, Scene scene, int tick, int tock);
	public void update(Game game, Scene scene, double delta);
	
	public void onCreate(Game game, Scene scene);
	public void onDestroy(Game game, Scene scene);
	public void onTick(Game game, Scene scene, int tick, int tock);
	public void onUpdate(Game game, Scene scene, double delta);

}
