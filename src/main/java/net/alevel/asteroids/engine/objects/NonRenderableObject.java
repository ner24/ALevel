package net.alevel.asteroids.engine.objects;

import org.joml.Vector3f;

import net.alevel.asteroids.game.objects.GameObjects;

/**Base class for all object types
 */
public abstract class NonRenderableObject {
	protected Vector3f position;
	protected Vector3f rotation;
	protected float scale;
	
	public NonRenderableObject() {
		this.position = new Vector3f();
		this.rotation = new Vector3f();
	}
	
	public NonRenderableObject(Vector3f pos, Vector3f rot) {
		this.position = pos;
		this.rotation = rot;
	}
	
	public void update(float time) {
		this.onUpdate(time);
		//some code that should run in all types of objects
	}
	
	public abstract void onUpdate(float time);
	
	/**This method exists so objects using opencl can clean up before opengl starts loading data. This is to make sure the GPU doesnt run out of memory
	 * @param time the interval (not accumulated time)
	 */
	public abstract void onUpdateFinish(float time);
	
	public abstract void onSpawn(GameObjects objectsManager);
	
	public abstract void onDespawn(GameObjects objectsManager);
	
	public abstract void cleanUp();
	
	public NonRenderableObject setScale(float scale) {
		this.scale = scale;
		return this;
	}
	
	public NonRenderableObject enlarge(float dx) {
		this.scale += dx;
		return this;
	}
	
	public NonRenderableObject shrink(float dx) {
		return this.enlarge(-dx);
	}
	
	/**Note: this copies the values in the vector.
	 * It does not reference the vector being passed (i.e. any changes made to the vector being passed will not affect the vector stored in this object)
	 */
	public NonRenderableObject setPosition(Vector3f position) {
		this.position.set(position);
		return this;
	}
	
	public NonRenderableObject translate(Vector3f delta) {
		this.position.add(delta);
		return this;
	}
	
	public NonRenderableObject translate(float dx, float dy, float dz) {
		this.position.add(dx, dy, dz);
		return this;
	}
	
	public NonRenderableObject setPosition(float x, float y, float z) {
		this.position.set(x, y, z);
		return this;
	}
	
	/**Note: this copies the values in the vector.
	 * It does not reference the vector being passed (i.e. any changes made to the vector being passed will not affect the vector stored in this object)
	 */
	public NonRenderableObject setRotation(Vector3f rotation) {
		this.rotation.set(rotation);
		return this;
	}
	
	public NonRenderableObject rotate(Vector3f delta) {
		this.rotation.add(delta);
		return this;
	}
	
	public NonRenderableObject rotate(float dx, float dy, float dz) {
		this.rotation.add(dx, dy, dz);
		return this;
	}
	
	public NonRenderableObject setRotation(float x, float y, float z) {
		this.rotation.set(x, y, z);
		return this;
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	public float getScale() {
		return this.scale;
	}
	
	public Vector3f getRotation() {
		return this.rotation;
	}
}