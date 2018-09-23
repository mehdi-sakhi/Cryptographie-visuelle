import org.opencv.core.CvType;
import org.opencv.core.Mat;


import java.util.Random;

public class CryptoAlgo {
    private final Mat originalMatrix;
    private Mat key1;
    private Mat key2;
    private final int width,height;

    public CryptoAlgo(Mat matrix) {
        this.originalMatrix = matrix;
        width=matrix.width();
        height=matrix.height();
        initKeys();
        generateKeys();
    }

    private void initKeys() {
        key1 = new Mat(height, width*2, CvType.CV_8UC1);
        key2 = new Mat(height, width*2, CvType.CV_8UC1);
    }

    private void generateKeys() {
        Random random=new Random();
        for (int i=0;i<height;i++)
        {
            for (int j=0;j<width;j++)
            {
                int probabilityPixel=random.nextInt(11);
                probabilityPixel= (probabilityPixel<=5)?0:1;
                generateEquivalentPixels(i,j,probabilityPixel);
            }
        }
    }

    private void generateEquivalentPixels(int i, int j, int probabilityPixel) {
        int OriginalPixel=(int)originalMatrix.get(i,j)[0];
        if (OriginalPixel==0)
        {
            generateWhitePixelEquivalent(i,j*2,probabilityPixel);
        }
        else if (OriginalPixel==255)
        {
            generateBlackPixelEquivalent(i,j*2,probabilityPixel);
        }
        else
        {
            System.out.println("Erreur");
        }
    }

    private void generateBlackPixelEquivalent(int i, int j, int probabilityPixel) {


        key1.put(i,j,new double[]{probabilityPixel*255});
        key1.put(i,j+1,new double[]{Math.abs(probabilityPixel-1)*255});

        key2.put(i,j,new double[]{Math.abs(probabilityPixel-1)*255});
        key2.put(i,j+1,new double[]{probabilityPixel*255});
    }

    private void generateWhitePixelEquivalent(int i, int j, int probabilityPixel) {
        key1.put(i,j,new double[]{probabilityPixel*255});
        key1.put(i,j+1,new double[]{Math.abs(probabilityPixel-1)*255});
        key2.put(i,j,new double[]{probabilityPixel*255});
        key2.put(i,j+1,new double[]{Math.abs(probabilityPixel-1)*255});
    }

    public Mat getOriginalMatrix() {
        return originalMatrix;
    }

    public Mat getKey1() {
        return key1;
    }

    public Mat getKey2() {
        return key2;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
