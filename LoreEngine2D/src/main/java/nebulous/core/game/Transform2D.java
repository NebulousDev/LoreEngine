package nebulous.core.game;

import lore.math.Matrix4f;
import lore.math.Vector2f;
import lore.math.Vector3f;

public class Transform2D {
	
	protected Vector2f 	position;
	protected Vector2f 	scale;
	protected float 	rotation;
	
	public Transform2D()
	{
		position = new Vector2f(0, 0);
		scale	 = new Vector2f(1, 1);
		rotation = 0;
	}
	
	public void move(float deltaX, float deltaY)
	{
		position.x += deltaX;
		position.y += deltaY;
	}
	
	public void move(Vector2f delta)
	{
		position.x += delta.x;
		position.y += delta.y;
	}
	
	public void rotate(float deltaDeg)
	{
		rotation += deltaDeg;
	}
	
	public void scale(float deltaX, float deltaY)
	{
		scale.x += deltaX;
		scale.y += deltaY;
	}
	
	public void scale(Vector2f delta)
	{
		scale.x += delta.x;
		scale.y += delta.y;
	}
	
	public void setPos(float x, float y)
	{
		position.x = x;
		position.y = y;
	}
	
	public void setPos(Vector2f pos)
	{
		position.x = pos.x;
		position.y = pos.y;
	}
	
	public Vector2f getPos()
	{
		return position;
	}
	
	public float getPosX()
	{
		return position.x;
	}
	
	public float getPosY()
	{
		return position.y;
	}
	
	public void setRot(float degree)
	{
		rotation = degree;
	}
	
	public float getRot()
	{
		return rotation;
	}
	
	public void setScale(float x, float y)
	{
		scale.x = x;
		scale.y = y;
	}
	
	public void setScale(Vector2f scale)
	{
		scale.x = scale.x;
		scale.y = scale.y;
	}
	
	public Vector2f getScale()
	{
		return scale;
	}
	
	public float getScaleX()
	{
		return scale.x;
	}
	
	public float getScaleY()
	{
		return scale.y;
	}
	
	public Matrix4f getTransform() {
		return  Matrix4f.Identity().mul(
				Matrix4f.Rotation(new Vector3f(0.0f, rotation, 0.0f)).mul(
				Matrix4f.Translation(new Vector3f(position.x, position.y, 0.0f)).mul(
				Matrix4f.Scale(new Vector3f(scale.x, scale.y, 0.0f)))));
	}
	
}
