package net.alevel.asteroids.engine.cl;

import static org.jocl.CL.CL_MEM_COPY_HOST_PTR;
import static org.jocl.CL.CL_MEM_READ_ONLY;
import static org.jocl.CL.CL_MEM_READ_WRITE;
import static org.jocl.CL.CL_TRUE;
import static org.jocl.CL.clBuildProgram;
import static org.jocl.CL.clCreateKernel;
import static org.jocl.CL.clCreateProgramWithSource;
import static org.jocl.CL.clEnqueueNDRangeKernel;
import static org.jocl.CL.clEnqueueReadBuffer;
import static org.jocl.CL.clReleaseKernel;
import static org.jocl.CL.clReleaseMemObject;
import static org.jocl.CL.clReleaseProgram;
import static org.jocl.CL.clSetKernelArg;
import static org.jocl.CL.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.jocl.CL;
import org.jocl.Pointer;
import org.jocl.Sizeof;
import org.jocl.cl_kernel;
import org.jocl.cl_mem;
import org.jocl.cl_program;
import org.joml.Vector3f;

import net.alevel.asteroids.engine.graphics.Mesh;
import net.alevel.asteroids.game.objects.shapes.MeshGen;

public class SurfaceNormalsTest {
	public static void run() throws IOException {
		CL.setExceptionsEnabled(true);
		String programString = "";
		try(BufferedReader br = new BufferedReader(new InputStreamReader(MatrixMulTest.class.getResourceAsStream("/collisions/GetSurfaceNormals.cl")))) {
			String line;
			while((line = br.readLine()) != null)
				programString += line + "\n";
		}
		System.out.println(programString);
		
		cl_program program = clCreateProgramWithSource(CLManager.getContext(), 1, new String[] {programString}, null, null);
		clBuildProgram(program, 0, null, null, null, null);
		cl_kernel kernel = clCreateKernel(program, "getSurfaceNormals", null);
		
		//Mesh cubeA = new Mesh(new float[] {0, 0, 1, 0, 0, 0, 1, 0, 0}, new float[0], new float[0], new int[] {0, 1, 2});
		Mesh cubeA = MeshGen.cube(1, 1, 1);
		float[] surfaceNormals = new float[cubeA.getIndices().length];
		int[] err = new int[1];
		cl_mem vertexMem = clCreateBuffer(CLManager.getContext(), CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR, Sizeof.cl_float * cubeA.getVertices().length, Pointer.to(cubeA.getVertices()), err);
		//System.out.println(stringFor_errorCode(err[0]));
		cl_mem indexMem = clCreateBuffer(CLManager.getContext(), CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR, Sizeof.cl_int * cubeA.getIndices().length, Pointer.to(cubeA.getIndices()), null);
		cl_mem surfaceNormalsMem = clCreateBuffer(CLManager.getContext(), CL_MEM_READ_WRITE, Sizeof.cl_float * cubeA.getIndices().length, null, null); //length is getIndices / 3 (3 indices per triangle) * 3 (each normal is 3 floats)
		clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(vertexMem));
		clSetKernelArg(kernel, 1, Sizeof.cl_mem, Pointer.to(indexMem));
		clSetKernelArg(kernel, 2, Sizeof.cl_mem, Pointer.to(surfaceNormalsMem));
		long[] global_work_size = {cubeA.getIndices().length / 3};
		clEnqueueNDRangeKernel(
				CLManager.getCommandQueue(),
				kernel,
				global_work_size.length,
				null,
				global_work_size,
				null,
				0,
				null,
				null);
		clEnqueueReadBuffer(
				CLManager.getCommandQueue(),
				surfaceNormalsMem,
				CL_TRUE,
				0,
				Sizeof.cl_float * surfaceNormals.length,
				Pointer.to(surfaceNormals),
				0,
				null,
				null);
		for(int i = 0; i < surfaceNormals.length; i += 3) {
			for(int j = 0; j < 2; j++)
				System.out.print(surfaceNormals[i + j] + " ");
			System.out.println(surfaceNormals[i + 2]);
		}
		
		clReleaseMemObject(vertexMem);
		clReleaseMemObject(indexMem);
		clReleaseMemObject(surfaceNormalsMem);
		clReleaseKernel(kernel);
		clReleaseProgram(program);
	}
	
	public static void runJava() {
		Mesh cubeA = new Mesh(new float[] {0, 0, 1, 0, 0, 0, 1, 0, 0}, new float[0], new float[0], new int[] {0, 1, 2});
		//Mesh cubeA = MeshGen.cube(1, 1, 1);
		Vector3f a = new Vector3f(
				cubeA.getVertices()[3] - cubeA.getVertices()[0],
				cubeA.getVertices()[4] - cubeA.getVertices()[1],
				cubeA.getVertices()[5] - cubeA.getVertices()[2]
		);
		
		Vector3f b = new Vector3f(
				cubeA.getVertices()[6] - cubeA.getVertices()[0],
				cubeA.getVertices()[7] - cubeA.getVertices()[1],
				cubeA.getVertices()[8] - cubeA.getVertices()[2]
		);
		System.out.println(a.cross(b));
	}
}