package net.alevel.asteroids.engine;

import org.joml.Vector3f;

import net.alevel.asteroids.engine.graphics.Mesh;

/**A generic in game object.
 */
public abstract class GameObject {
	private final Mesh mesh;
	protected final Vector3f position;
	protected float scale;
	protected final Vector3f rotation;
	
	public GameObject(Mesh mesh) {
		this.mesh = mesh;
		this.position = new Vector3f();
		this.scale = 1;
		this.rotation = new Vector3f();
	}
	
	public abstract void update(float time);

	public Vector3f getPosition() {
		return this.position;
	}
	
	public float getScale() {
		return this.scale;
	}
	
	public Vector3f getRotation() {
		return this.rotation;
	}
	
	public GameObject setScale(float scale) {
		this.scale = scale;
		return this;
	}
	
	/**Note: this copies the values in the vector.
	 * It does not reference the vector being passed (i.e. any changes made to the vector being passed will not affect the vector stored in this object)
	 */
	public GameObject setPosition(Vector3f position) {
		this.position.set(position);
		return this;
	}
	
	public GameObject setPosition(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
		return this;
	}
	
	/**Note: this copies the values in the vector.
	 * It does not reference the vector being passed (i.e. any changes made to the vector being passed will not affect the vector stored in this object)
	 */
	public GameObject setRotation(Vector3f rotation) {
		this.rotation.set(rotation);
		return this;
	}
	
	public GameObject setRotation(float x, float y, float z) {
		this.rotation.x = x;
		this.rotation.y = y;
		this.rotation.z = z;
		return this;
	}
	
	public Mesh getMesh() {
		return this.mesh;
	}
}
