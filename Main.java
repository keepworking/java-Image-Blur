import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("start");
		BufferedImage target = null;
		
		try {
			target = ImageIO.read(new File("A.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int radius = 100;
		int size = radius * 2 + 1;
		
		float[] data = new float[size * size];
        
		float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;
        
        for (int i = -radius; i <= radius; i++) {
            float distance = i * i;
            int index = i + radius;
            data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
            total += data[index];
        }
        
        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }  
		Kernel kernel = new Kernel(1, size, data);
		
		ConvolveOp convolveOp = new ConvolveOp(kernel,ConvolveOp.EDGE_NO_OP,null);
		
		target = convolveOp.filter(target, null);
		
		kernel = new Kernel(size, 1, data);
		
		convolveOp = new ConvolveOp(kernel,ConvolveOp.EDGE_NO_OP,null);
		
		target = convolveOp.filter(target, null);
		
		try {
			ImageIO.write(target, "jpg", new File("B.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("end");
		
	}

}
