package nebulous.loreEngine.core.graphics;

import lore.math.Matrix4f;
import lore.math.Vector2f;
import lore.math.Vector3f;
import nebulous.loreEngine.core.game.Window;

public class Camera {
	
	protected Vector2f 	position;
	protected float		distance;
	protected float		rotation;
	protected Matrix4f	perspective;
	protected Matrix4f	view;
	protected float		fov;
	
	public Camera()
	{
		this.position = new Vector2f(0, 0);
		this.distance = -1.0f;
		this.rotation = 0.0f;
		this.fov = 90.0f;
		
		this.perspective = Matrix4f.Identity();
	}
	
	public void move(float deltaX, float deltaY)
	{
		position.x += deltaX;
		position.y += deltaY;
	}
	
	public void rotate(float deltaDeg)
	{
		rotation += deltaDeg;
	}
	
	public void zoom(float delta)
	{
		distance += delta;
	}
	
	public void setPos(float x, float y)
	{
		this.position.x = x;
		this.position.y = y;
	}
	
	public Vector2f getPos() {
		return position;
	}
	
	public float getRot()
	{
		return rotation;
	}
	
	public void setDistance(float dist)
	{
		this.distance = dist;
	}
	
	public float getDistance() {
		return distance;
	}
	
	public Matrix4f getPerspective(Window window) {
		return Matrix4f.Perspective(fov, (float)window.getWidth() / (float)window.getHeight(), 0.0001f, 1000.0f);
	}
	
	public Matrix4f getView() {
		return Matrix4f.Identity().mul(
				Matrix4f.Rotation(new Vector3f(0.0f, 0.0f, rotation)).mul( 
				Matrix4f.Translation(new Vector3f(position.x, position.y, distance))));
	}

	public float getFov() {
		return fov;
	}

	public void setFov(float fov) {
		this.fov = fov;
	}

}
