package net.alevel.asteroids.game.physics;

import org.joml.AABBf;
import org.joml.Matrix3f;
import org.joml.Vector3f;

import net.alevel.asteroids.engine.graphics.Mesh;
import net.alevel.asteroids.engine.objects.GameObject;
import net.alevel.asteroids.game.objects.GameObjects;

/**@deprecated no longer using axis aligned bounding boxes
 */
@Deprecated
public class PhysicalObject extends GameObject {
	private final AABBf modelBoundingBox;
	private final AABBf boundingBox;
	
	public PhysicalObject(Mesh mesh) {
		super(mesh);
		this.modelBoundingBox = new AABBf();
		this.boundingBox = new AABBf();
		this.createModelBoundingBox(mesh.getVertices());
		Collision.getInstance().addObjectToCheck(this);
	}
	
	@Override
	public final void onUpdate(float time) {
		this.simulatePhysics(time);
		
		Matrix3f rotateAndScale = new Matrix3f();
		rotateAndScale.rotateX((float) Math.toRadians(-super.rotation.x));
		rotateAndScale.rotateY((float) Math.toRadians(-super.rotation.y));
		rotateAndScale.rotateZ((float) Math.toRadians(-super.rotation.z));
		rotateAndScale.scale(super.scale);
		
		//System.out.println("PhysUpdate: " + new Vector3f(this.modelBoundingBox.maxX, this.modelBoundingBox.maxY, this.modelBoundingBox.maxZ).mul(rotateAndScale).add(super.position));
		//System.out.println("PhysUpdate: " + new Vector3f(this.modelBoundingBox.minX, this.modelBoundingBox.minY, this.modelBoundingBox.minZ).mul(rotateAndScale).add(super.position));
		//System.out.println();
		
		this.boundingBox.setMax(new Vector3f(this.modelBoundingBox.maxX, this.modelBoundingBox.maxY, this.modelBoundingBox.maxZ).mul(rotateAndScale).add(super.position))
						.setMin(new Vector3f(this.modelBoundingBox.minX, this.modelBoundingBox.minY, this.modelBoundingBox.minZ).mul(rotateAndScale).add(super.position));
		//System.out.println("(" + this.boundingBox.minX + ", " + this.boundingBox.minY + ", " + this.boundingBox.minZ + ") (" + this.boundingBox.maxX + ", " + this.boundingBox.maxY + ", " + this.boundingBox.maxZ + ")");
		//System.out.println();
		//System.out.println(this.modelBoundingBox);
		//System.out.println(this.boundingBox);
	}
	
	protected void simulatePhysics(float time) {
		//nothing. So i can create an object that does nothing on update but still is detected in collisions
	}
	
	private final void createModelBoundingBox(float[] positions) {
	//float minX = Float.MIN_VALUE, maxX = Float.MAX_VALUE, minY = Float.MIN_VALUE, maxY = Float.MAX_VALUE, minZ = Float.MIN_VALUE, maxZ = Float.MAX_VALUE;
	float minX = 0, maxX = 0, minY = 0, maxY = 0, minZ = 0, maxZ = 0;
		for(int i = 0; i < positions.length; i += 3) {
			Vector3f temp = new Vector3f(positions[i],
									 	positions[i + 1],
									 	positions[i + 2]);
			if(temp.x > maxX)
				maxX = temp.x;
			if(temp.x < minX)
				minX = temp.x;
			if(temp.y > maxY)
				maxY = temp.y;
			if(temp.y < minY)
				minY = temp.y;
			if(temp.z > maxZ)
				maxZ = temp.z;
			if(temp.z < minZ)
				minZ = temp.z;
			//System.out.println(temp);
		}
		//System.out.println(new AABBf().setMax(maxX, maxY, maxZ).setMin(minX, minY, minZ));
		this.modelBoundingBox.setMax(maxX, maxY, maxZ)
					   		 .setMin(minX, minY, minZ);
	}
	
	public final AABBf getBoundingBox() {
		return this.boundingBox;
	}
	
	public final AABBf getModelBoundingBox() {
		return this.modelBoundingBox;
	}

	@Override
	public void onUpdateFinish(float time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSpawn(GameObjects objectsManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDespawn(GameObjects objectsManager) {
		// TODO Auto-generated method stub
		
	}
}