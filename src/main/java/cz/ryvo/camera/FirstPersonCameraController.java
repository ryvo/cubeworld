package cz.ryvo.camera;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

/**
 * The first person camera controller.
 */
public class FirstPersonCameraController {

    //3d vector to store the camera's position in
    private Vector3f position    = null;
    //the rotation around the Y axis of the camera
    private float    yaw         = 0.0f;
    //the rotation around the X axis of the camera
    private float    pitch       = 0.0f;

    //Constructor that takes the starting x, y, z location of the camera
    public FirstPersonCameraController(float x, float y, float z)
    {
        position = new Vector3f(x, y, z);
    }

    // Increment the camera's current yaw rotation
    public void yaw(float amount)
    {
        yaw += amount;
    }

    // Increment the camera's current yaw rotation
    public void pitch(float amount)
    {
        pitch += amount;
    }

    // Moves the camera forward relative to its current rotation (yaw)
    public void walkForward(float distance)
    {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw));
    }

    // Moves the camera backward relative to its current rotation (yaw)
    public void walkBackwards(float distance)
    {
        position.x += distance * (float) Math.sin(Math.toRadians(yaw));
        position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
    }

    // Strafes the camera left relative to its current rotation (yaw)
    public void strafeLeft(float distance)
    {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
    }

    // Strafes the camera right relitive to its current rotation (yaw)
    public void strafeRight(float distance)
    {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
    }

    // Translates and rotates the matrix so that it looks through the camera
    public void lookThrough()
    {
        // Roatate the pitch around the X axis
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        // Rotate the yaw around the Y axis
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        // Translate to the position vector's location
        GL11.glTranslatef(position.x, position.y, position.z);
    }
}
