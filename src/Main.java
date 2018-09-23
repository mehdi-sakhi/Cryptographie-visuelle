import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Main {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) {

        String location = "resources/smile.png";
        Mat img = Imgcodecs.imread(location);
        Imgproc.cvtColor(img, img, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(img, img, 100, 255, Imgproc.THRESH_BINARY);
        Imgcodecs.imwrite("resources/smile_bin.png", img);
        CryptoAlgo cryptoAlgo=new CryptoAlgo(img);
        Imgcodecs.imwrite("resources/key1.png", cryptoAlgo.getKey1());
        Imgcodecs.imwrite("resources/key2.png", cryptoAlgo.getKey2());

        Mat res=new Mat(img.height(),img.width()*2,CvType.CV_8UC1);
        Core.bitwise_xor(cryptoAlgo.getKey1(),cryptoAlgo.getKey2(),res);
        Imgcodecs.imwrite("resources/xor.png", res);

    }
}
