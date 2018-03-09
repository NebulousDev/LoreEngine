package nebulous.loreEngine.core.game;

import lore.math.Matrix4f;
import lore.math.Vector3f;

public class Transform3D {

	protected Vector3f 	position;
	protected Vector3f 	scale;
	protected Vector3f 	rotation;
	
	public Transform3D()
	{
		position = new Vector3f(0, 0, 0);
		scale	 = new Vector3f(1, 1, 1);
		rotation = new Vector3f(0, 0, 0);
	}
	
	public void move(float deltaX, float deltaY, float deltaZ)
	{
		position.x += deltaX;
		position.y += deltaY;
		position.z += deltaZ;
	}
	
	public void move(Vector3f delta)
	{
		position.x += delta.x;
		position.y += delta.y;
		position.z += delta.z;
	}
	
	public void rotate(float deltaX, float deltaY, float deltaZ)
	{
		rotation.x += deltaX;
		rotation.y += deltaY;
		rotation.z += deltaZ;
	}
	
	public void rotate(Vector3f delta)
	{
		rotation.x += delta.x;
		rotation.y += delta.y;
		rotation.z += delta.z;
	}
	
	public void scale(float deltaX, float deltaY, float deltaZ)
	{
		scale.x += deltaX;
		scale.y += deltaY;
		scale.z += deltaZ;
	}
	
	public void scale(Vector3f delta)
	{
		scale.x += delta.x;
		scale.y += delta.y;
		scale.z += delta.z;
	}
	
	public void setPos(float x, float y, float z)
	{
		position.x = x;
		position.y = y;
		position.z = z;
	}
	
	public void setPos(Vector3f pos)
	{
		position.x = pos.x;
		position.y = pos.y;
		position.z = pos.z;
	}
	
	public Vector3f getPos()
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
	
	public float getPosZ()
	{
		return position.z;
	}
	
	public void setRot(float x, float y, float z)
	{
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}
	
	public void setRot(Vector3f rot)
	{
		rotation.x = rot.x;
		rotation.y = rot.y;
		rotation.z = rot.z;
	}
	
	public Vector3f getRot()
	{
		return rotation;
	}
	
	public float getRotX()
	{
		return rotation.x;
	}
	
	public float getRotY()
	{
		return rotation.y;
	}
	
	public float getRotZ()
	{
		return rotation.z;
	}
	
	public void setScale(float x, float y, float z)
	{
		scale.x = x;
		scale.y = y;
		scale.z = z;
	}
	
	public void setScale(Vector3f scale)
	{
		scale.x = scale.x;
		scale.y = scale.y;
		scale.z = scale.z;
	}
	
	public Vector3f getScale()
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
	
	public float getScaleZ()
	{
		return scale.z;
	}
	
	public Matrix4f getTransform() {
		return  Matrix4f.Identity().mul(
				Matrix4f.Rotation(rotation).mul(
				Matrix4f.Translation(position).mul(
				Matrix4f.Scale(scale))));
	}
	
}
