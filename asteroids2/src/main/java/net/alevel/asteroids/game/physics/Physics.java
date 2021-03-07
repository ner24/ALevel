package net.alevel.asteroids.game.physics;

import java.io.IOException;
import java.util.List;

import net.alevel.asteroids.game.physics.SAT.SAT;
import net.alevel.asteroids.game.physics.pipeline.FunctionPipeline;
import net.alevel.asteroids.game.physics.pipeline.PipelineBuffer;
import net.alevel.asteroids.game.physics.worldCoords.WorldCoordinates;

public class Physics {
	private final PipelineBuffer physicsPipeline;
	private final WorldCoordinates worldCoordsCalc;
	//private final FunctionPipeline collisionDetector;
	
	public Physics() throws IOException {
		this.physicsPipeline = new PipelineBuffer();
		this.worldCoordsCalc = new WorldCoordinates();
		
		this.physicsPipeline.add(0, this.worldCoordsCalc);
		
		//this.collisionDetector = new SAT(this.physicsPipeline);
	}
	
	public void onUpdate(List<RigidObject> objects) {
		this.worldCoordsCalc.calc(objects, this.physicsPipeline);
		//this.collisionDetector.runPipeline(objects);
	}
}
